package com.kshitz.kshitz.controller;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.Product;
import com.kshitz.kshitz.entities.products.ProductVariation;
import com.kshitz.kshitz.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductServiceImpl productService;

    @PostMapping("/seller/add-product")
    public String addProduct(@RequestBody ProductDto productDto, Authentication authentication) {
        String username = authentication.getName();
        return productService.addProduct(productDto, username);
    }

    @GetMapping("/seller/view-product/{id}")
    public Product viewSellerProduct(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        return productService.viewSellerProduct(id, username);
    }

    @GetMapping("/seller/view-product-variation/{id}")
    public ProductVariation viewSellerProductVariation(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        return productService.viewSellerProductVariation(id, username);
    }

    @PostMapping("/seller/add-product-variation")
    public String addProductVariation(@RequestBody ProductVariationDto productVariationDto) {
        return productService.addProductVariation(productVariationDto);
    }

    @GetMapping("/seller/view-all-products")
    public List<Product> viewAllProducts(Authentication authentication) {
        String username = authentication.getName();
        return productService.viewAllSellerProducts(username);
    }

    @GetMapping("/seller/view-all-product-variations/{id}")
    public List<ProductVariation> viewAllProductVariations(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        return productService.viewAllSellerProductVariations(id, username);
    }

    @DeleteMapping("/seller/delete-product/{id}")
    public String deleteProduct(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        return productService.deleteProduct(id, username);
    }

    @PutMapping("/seller/update-product/{id}")
    public String updateProduct(@PathVariable String id, @RequestBody UpdateProductDto updateProductDto, Authentication authentication) {
        String username = authentication.getName();
        return productService.updateProduct(id, updateProductDto, username);
    }

    @PutMapping("/seller/update-product-variation/{id}")
    public String updateProductVariation(@PathVariable String id, @RequestBody UpdateProductVariationDto updateProductVariationDto, Authentication authentication) {
        String username = authentication.getName();
        return productService.updateProductVariation(id, updateProductVariationDto, username);
    }

    @GetMapping("/customer/view-product/{id}")
    public List<ProductVariation> viewCustomerProduct(@PathVariable String id) {
        return productService.viewCustomerProduct(id);
    }

    @GetMapping("/customer/view-all-products")
    public List<ProductVariation> viewAllCustomerProducts() {
        return productService.viewAllProducts();
    }

    @GetMapping("/customer/view-similar-products/{productId}")
    public ProductFilterDto viewSimilarProducts(@PathVariable String productId) {
        return productService.viewSimilarProducts(productId);
    }

    @GetMapping("/admin/view-product/{id}")
    public Product viewProduct(@PathVariable String id) {
        return productService.viewProduct(id);
    }

    @GetMapping("/admin/view-all-products")
    public List<ProductVariation> viewAllAdminProducts() {
        return productService.viewAllProducts();
    }

    @PutMapping("/admin/activate-product/{id}")
    public String activateProduct(@PathVariable String id) {
        return productService.activateProduct(id);
    }

    @PutMapping("/admin/deactivate-product/{id}")
    public String deactivateProduct(@PathVariable String id) {
        return productService.deactivateProduct(id);
    }

    //    @GetMapping({"/view-all-products/{count}"})
//    public List<ProductVariation> viewAllProducts(@PathVariable Integer count) {
//        return this.productService.viewGroupProducts(count);
//    }
    @GetMapping({"/admin/view-product"})
    public Iterable<Product> viewProduct() {
        return this.productService.viewProducts();
    }

    @GetMapping({"/view-all-products/{count}"})
    public List<ProductVariation> viewAllProducts(@PathVariable Integer count) {
        return this.productService.viewGroupProducts(count);
    }
    @GetMapping({"/view-all-product-variation/{id}"})
    public List<ProductVariation> viewAllCustomerProducts(@PathVariable String id) {
        return this.productService.viewAllProductVariations(id);
    }
}