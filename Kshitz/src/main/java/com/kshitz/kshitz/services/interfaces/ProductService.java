package com.kshitz.kshitz.services.interfaces;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductService {
    String addProduct(ProductDto productDto, String username);

    String addProductVariation(ProductVariationDto productVariationDto);

    Product viewProduct(Integer id);

    String activateProduct(Integer id);

    String deactivateProduct(Integer id);

    Product viewSellerProduct(Integer id, String username);

    ProductVariation viewSellerProductVariation(Integer id, String username);

    List<Product> viewAllSellerProducts(String username);

    List<ProductVariation> viewAllSellerProductVariations(Integer id, String username);

    @Transactional
    String deleteProduct(Integer id, String username);

    String updateProduct(Integer id, UpdateProductDto updateProductDto, String username);

    String updateProductVariation(Integer id, UpdateProductVariationDto updateProductVariationDto, String username);

    List<ProductVariation> viewCustomerProduct(Integer id);

    List<ProductVariation> viewAllProducts();

    ProductFilterDto viewSimilarProducts(Integer id);
}
