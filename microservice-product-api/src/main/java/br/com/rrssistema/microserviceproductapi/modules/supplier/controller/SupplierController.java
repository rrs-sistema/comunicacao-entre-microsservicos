package br.com.rrssistema.microserviceproductapi.modules.supplier.controller;

import br.com.rrssistema.microserviceproductapi.modules.supplier.dto.SupplierRequest;
import br.com.rrssistema.microserviceproductapi.modules.supplier.dto.SupplierResponse;
import br.com.rrssistema.microserviceproductapi.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/save")
    private SupplierResponse save(@RequestBody SupplierRequest request) {
        return supplierService.save(request);
    }

    @GetMapping("/all")
    public List<SupplierResponse> findAll() {
        return supplierService.findByAll();
    }

    @GetMapping("/id/{id}")
    public SupplierResponse findById(@PathVariable Integer id) {
        return supplierService.findByIdResponse(id);
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

}
