package br.com.rrssistema.microserviceproductapi.modules.category.service;

import br.com.rrssistema.microserviceproductapi.config.exception.SuccessResponse;
import br.com.rrssistema.microserviceproductapi.config.exception.ValidationException;
import br.com.rrssistema.microserviceproductapi.modules.category.dto.CategoryRequest;
import br.com.rrssistema.microserviceproductapi.modules.category.dto.CategoryResponse;
import br.com.rrssistema.microserviceproductapi.modules.category.model.Category;
import br.com.rrssistema.microserviceproductapi.modules.category.repository.CategoryRepository;
import br.com.rrssistema.microserviceproductapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CategoryService {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse findByIdResponse(Integer id) {
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findByAll() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description) {
        if(isEmpty(description)) {
            throw new ValidationException("The category description must be informed.");
        }
        return categoryRepository
                .findByDescriptionIgnoreCaseContaining(description)
                .stream()
                .map(CategoryResponse::of)
                //Ou//.map(category -> CategoryResponse.of(category))
                .collect(Collectors.toList());
    }

    public Category findById(Integer id){
        validateInformedId(id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no category for given ID."));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category  = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id) {
        validateCategoryNameInformed(request);
        validateInformedId(id);
        var category  = Category.of(request);
        category.setId(id);
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    public SuccessResponse delete(Integer id) {
        validateInformedId(id);
        if(productService.existsByCategoryId(id)){
            throw new ValidationException("You cannot delete this category because it's already defined by a product.");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The supplier ID must be informed");
    }

    private void validateInformedId(Integer id) {
        if(isEmpty(id)) {
            throw new ValidationException("The category ID must be informed.");
        }
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if(isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }
}
