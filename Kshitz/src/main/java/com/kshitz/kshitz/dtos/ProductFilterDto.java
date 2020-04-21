package com.kshitz.kshitz.dtos;

import com.kshitz.kshitz.entities.products.Product;

import java.util.List;

public class ProductFilterDto {
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
