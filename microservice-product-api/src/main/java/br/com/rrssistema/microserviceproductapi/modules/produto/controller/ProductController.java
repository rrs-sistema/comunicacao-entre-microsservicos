package br.com.rrssistema.microserviceproductapi.modules.produto.controller;

import br.com.rrssistema.microserviceproductapi.config.exception.SuccessResponse;
import br.com.rrssistema.microserviceproductapi.modules.produto.dto.ProductRequest;
import br.com.rrssistema.microserviceproductapi.modules.produto.dto.ProductResponse;
import br.com.rrssistema.microserviceproductapi.modules.produto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    private ProductResponse save(@RequestBody ProductRequest request) {
        return productService.save(request);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @GetMapping("/all")
    public List<ProductResponse> findAll() {
        return productService.findByAll();
    }

    @GetMapping("/name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("/id/{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        return productService.findByIdResponse(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId) {
        return productService.findBySupplierId(supplierId);
    }

}
