package com.kshitz.kshitz.entities.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kshitz.kshitz.audits.Auditable;
import com.kshitz.kshitz.entities.users.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    @JsonIgnore
    private Boolean isCancellable=false;
    @JsonIgnore
    private Boolean isReturnable=false;
    @JsonIgnore
    private Boolean isActive=false;
    @JsonIgnore
    private Boolean isDeleted=false;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    Seller seller;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewid")
    List<ProductReview> productReviews;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
     Category category;

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
        setLastModifiedBy(seller.getUsername());
        setLastModifiedDate(new Date());
    }
    private void create(){
        setCreatedBy(seller.getUsername());
        setLastModifiedBy(seller.getUsername());
        setLastModifiedDate(new Date());
        setCreatedDate(new Date());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(Boolean cancellable) {
        isCancellable = cancellable;
    }

    public Boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(Boolean returnable) {
        isReturnable = returnable;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", isCancellable=" + isCancellable +
                ", isReturnable=" + isReturnable +
                ", isActive=" + isActive +
                ", seller=" + seller +
                '}';
    }
}
