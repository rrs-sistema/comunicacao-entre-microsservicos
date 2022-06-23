package br.com.rrssistema.microserviceproductapi.modules.category.dto;

import br.com.rrssistema.microserviceproductapi.modules.category.model.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryRequest {
    private  String description;

    public static CategoryRequest of(Category category) {
        var request = new CategoryRequest();
        BeanUtils.copyProperties(category, request);
        return request;
    }
}
