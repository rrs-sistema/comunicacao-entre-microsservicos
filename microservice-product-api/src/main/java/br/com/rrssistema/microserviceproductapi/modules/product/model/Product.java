package br.com.rrssistema.microserviceproductapi.modules.product.model;

import br.com.rrssistema.microserviceproductapi.modules.category.model.Category;
import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductRequest;
import br.com.rrssistema.microserviceproductapi.modules.supplier.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fK_supplier", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "fK_category", nullable = false)
    private Category category;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public static Product of(ProductRequest request, Supplier supplier, Category category) {
        return Product
                .builder()
                .name(request.getName())
                .quantityAvailable(request.getQuantityAvailable())
                .supplier(supplier)
                .category(category)
                .build();
    }

    public void updateStock(Integer quantity) {
        quantityAvailable = quantityAvailable - quantity;
    }

}
