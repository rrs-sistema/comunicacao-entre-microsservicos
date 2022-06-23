package br.com.rrssistema.microserviceproductapi.modules.supplier.dto;

import br.com.rrssistema.microserviceproductapi.modules.supplier.model.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierRequest {
    private  String name;

    public static SupplierRequest of(Supplier supplier) {
        var request = new SupplierRequest();
        BeanUtils.copyProperties(supplier, request);
        return request;
    }
}
