package com.kshitz.kshitz.entities.products;

import com.kshitz.kshitz.audits.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductVariation extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantityAvailable;
    private Double price;
    private Boolean isActive;
    @Convert(converter = HashMapConverter.class)

    @Column(columnDefinition = "json")
    private Map<String, String> metadata;

    private String primaryImageName;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Product product;

    @PrePersist
    public void onPrePersist(){
        create();
    }
    @PreUpdate
    public void onPreUpdate() {
        audit();
    }
    @PreRemove
    public void onPreDelete(){
        audit();
    }
    private void audit() {
        setLastModifiedBy(product.seller.getUsername());
        setLastModifiedDate(new Date());
    }
    private void create(){
        setCreatedBy(product.seller.getUsername());
        setLastModifiedBy(product.seller.getUsername());
        setLastModifiedDate(new Date());
        setCreatedDate(new Date());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPrimaryImageName() {
        return primaryImageName;
    }

    public void setPrimaryImageName(String primaryImageName) {
        this.primaryImageName = primaryImageName;
    }

}


