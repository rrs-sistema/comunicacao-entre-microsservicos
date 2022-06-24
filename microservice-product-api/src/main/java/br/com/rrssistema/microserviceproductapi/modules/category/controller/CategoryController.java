package br.com.rrssistema.microserviceproductapi.modules.category.controller;

import br.com.rrssistema.microserviceproductapi.config.exception.SuccessResponse;
import br.com.rrssistema.microserviceproductapi.modules.category.dto.CategoryRequest;
import br.com.rrssistema.microserviceproductapi.modules.category.dto.CategoryResponse;
import br.com.rrssistema.microserviceproductapi.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    private CategoryResponse save(@RequestBody CategoryRequest request) {
        return categoryService.save(request);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

    @GetMapping("/all")
    public List<CategoryResponse> findAll() {
        return categoryService.findByAll();
    }

    @GetMapping("/id/{id}")
    public CategoryResponse findById(@PathVariable Integer id) {
        return categoryService.findByIdResponse(id);
    }

    @GetMapping("/description/{description}")
    public List<CategoryResponse> findByDescription(@PathVariable String description) {
        return categoryService.findByDescription(description);
    }

}
