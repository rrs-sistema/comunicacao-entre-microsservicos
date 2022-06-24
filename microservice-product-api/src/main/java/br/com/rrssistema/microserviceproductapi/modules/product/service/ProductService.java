package br.com.rrssistema.microserviceproductapi.modules.product.service;

import br.com.rrssistema.microserviceproductapi.config.exception.SuccessResponse;
import br.com.rrssistema.microserviceproductapi.config.exception.ValidationException;
import br.com.rrssistema.microserviceproductapi.modules.category.service.CategoryService;
import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductQuantityDTO;
import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductRequest;
import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductResponse;
import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductStockDTO;
import br.com.rrssistema.microserviceproductapi.modules.product.model.Product;
import br.com.rrssistema.microserviceproductapi.modules.product.repository.ProductRepository;
import br.com.rrssistema.microserviceproductapi.modules.sales.dto.SalesConfirmationDTO;
import br.com.rrssistema.microserviceproductapi.modules.sales.enums.SalesStatus;
import br.com.rrssistema.microserviceproductapi.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.rrssistema.microserviceproductapi.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    private static final  Integer ZERO = 0;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }

    public List<ProductResponse> findByAll() {
        return productRepository
                .findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name) {
        if(isEmpty(name)) {
            throw new ValidationException("The product name must be informed.");
        }
        return productRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                //Ou//.map(category -> CategoryResponse.of(category))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if(supplierId == null) {
            throw new ValidationException("The product supplier ID must be informed.");
        }
        return productRepository
                .findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer category) {
        if(category == null) {
            throw new ValidationException("The product category ID must be informed.");
        }
        return productRepository
                .findByCategoryId(category)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public Product findById(Integer id){
        validateInformedId(id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no product for given ID."));
    }

    public ProductResponse save(ProductRequest request) {
        validateProductDataInformed(request);
        validateProductCategoryAndSupplierIdInformed(request);

        var supplier = supplierService.findById(request.getSupplierId());
        var category = categoryService.findById(request.getCategoryId());

        var productRequest = Product.of(request, supplier, category);
        var product  = productRepository.save(productRequest);
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validateProductDataInformed(request);
        validateProductCategoryAndSupplierIdInformed(request);
        validateInformedId(id);

        var supplier = supplierService.findById(request.getSupplierId());
        var category = categoryService.findById(request.getCategoryId());

        var productRequest = Product.of(request, supplier, category);
        productRequest.setId(id);
        var product  = productRepository.save(productRequest);
        return ProductResponse.of(product);
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The product ID must be informed");
    }

    public Boolean existsByCategoryId(Integer categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer supplierId) {
        return productRepository.existsBySupplierId(supplierId);
    }

    private void validateProductDataInformed(ProductRequest request) {
        if(isEmpty(request.getName())) {
            throw new ValidationException("The product's name was not informed.");
        }
        if(request.getQuantityAvailable() == null) {
            throw new ValidationException("The product's quantity was not informed.");
        }
        if(request.getQuantityAvailable() <= ZERO) {
            throw new ValidationException("The quantity should not be less or equal to zero.");
        }
    }

    private void validateProductCategoryAndSupplierIdInformed(ProductRequest request) {
        if(isEmpty(request.getCategoryId())) {
            throw new ValidationException("The category ID was not informed.");
        }
        if(request.getSupplierId() == null) {
            throw new ValidationException("The supplier ID was not informed.");
        }
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("The product ID must be informed.");
        }
    }

    public void updateProductStock(ProductStockDTO product) {
        try {
            validateStockUpdateData(product);
            updateStock(product);
        } catch (Exception ex) {
            log.error("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            var rejectedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.REJECTED);
            salesConfirmationSender.sendSalesConfirmationMessage(rejectedMessage);
        }
    }

    private void validateStockUpdateData(ProductStockDTO product) {
        if(isEmpty(product) || isEmpty(product.getSalesId())){
            throw new ValidationException("The product data and the sales ID must be informed.");
        }
        if(isEmpty(product.getProducts())){
            throw new ValidationException("The sales' products must be informed.");
        }
        product
                .getProducts()
                .forEach(salesProduct -> {
                    if(isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId())){
                        throw new ValidationException("The productID and the quantity must be informed.");
                    }
                });
    }

    @Transactional
    private void updateStock(ProductStockDTO product) {
        var productsForUpdate = new ArrayList<Product>();
        product.getProducts()
                .forEach(salesProduct -> {
                    var existingProduct = findById(salesProduct.getProductId());
                    validateQuantityInStock(salesProduct, existingProduct);
                    existingProduct.updateStock(salesProduct.getQuantity());
                    productsForUpdate.add(existingProduct);
                });
        if(!isEmpty(productRepository)) {
            productRepository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        }
    }

    private void validateQuantityInStock(ProductQuantityDTO salesProduct, Product existingProduct) {
        if(salesProduct.getQuantity() > existingProduct.getQuantityAvailable()) {
            throw new ValidationException(
                    String.format("The product %s stock cannot be updated.", existingProduct.getId()));
        }
    }
}