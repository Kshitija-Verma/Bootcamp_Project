package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;


import java.util.List;

public interface ProductService {
    String addProduct(ProductDto productDto, String username);

    String addProductVariation(ProductVariationDto productVariationDto);

    Product viewProduct(String  id);

    String activateProduct(String id);

    String deactivateProduct(String id);

    Product viewSellerProduct(String id, String username);

    ProductVariation viewSellerProductVariation(String id, String username);

    List<Product> viewAllSellerProducts(String username);

    List<ProductVariation> viewAllSellerProductVariations(String  id, String username);


    String deleteProduct(String id, String username);

    String updateProduct(String id, UpdateProductDto updateProductDto, String username);

    String updateProductVariation(String id, UpdateProductVariationDto updateProductVariationDto, String username);

    List<ProductVariation> viewCustomerProduct(String id);

    List<ProductVariation> viewAllProducts();

    ProductFilterDto viewSimilarProducts(String id);
}
