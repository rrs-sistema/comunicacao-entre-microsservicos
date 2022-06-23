package br.com.rrssistema.microserviceproductapi.modules.supplier.service;

import br.com.rrssistema.microserviceproductapi.config.exception.ValidationException;
import br.com.rrssistema.microserviceproductapi.modules.supplier.dto.SupplierRequest;
import br.com.rrssistema.microserviceproductapi.modules.supplier.dto.SupplierResponse;
import br.com.rrssistema.microserviceproductapi.modules.supplier.model.Supplier;
import br.com.rrssistema.microserviceproductapi.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findByAll() {
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name) {
        if(!hasText(name)) {
            throw new ValidationException("The supplier name must be informed.");
        }
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                //Ou//.map(category -> CategoryResponse.of(category))
                .collect(Collectors.toList());
    }

    public Supplier findById(Integer id) {
        if(id == null) {
            throw new ValidationException("The supplier ID must be informed.");
        }
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for given ID."));
    }

    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier  = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void validateSupplierNameInformed(SupplierRequest request) {
        if(!hasText(request.getName())) {
            throw new ValidationException("The supplier's name was not informed.");
        }
    }
}
