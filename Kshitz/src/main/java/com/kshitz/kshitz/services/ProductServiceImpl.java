package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.*;
import com.kshitz.kshitz.entities.users.Seller;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.NotActiveException;
import com.kshitz.kshitz.exceptions.NotUniqueException;
import com.kshitz.kshitz.exceptions.ValidationException;
import com.kshitz.kshitz.repositories.*;
import com.kshitz.kshitz.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kshitz.kshitz.utilities.StringConstants.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CategoryMetadataFieldValueRepository categoryMetadataFieldValueRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;
    @Autowired
    CategoryMetadataFieldRespository categoryMetadataFieldRespository;


    @Override
    public String addProduct(ProductDto productDto, String username) {
        Seller seller = sellerRepository.findByUsername(username);
        Product product = new Product();
        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new EntityNotFoundException(CATEGORY_ERROR);
        }
        Category category = categoryOptional.get();
        Product product1 = productRepository.findUniqueName(seller.getId(), productDto.getBrand(), category.getId(), productDto.getName());
        if (product1 != null) {
            throw new NotUniqueException("Name is not unique");
        }

        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setSeller(seller);
        productRepository.save(product);
        return "Product added successfully";

    }

    @Override
    public String addProductVariation(ProductVariationDto productVariationDto) {
        Optional<Product> productOptional = productRepository.findById(productVariationDto.getProductId());
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        ProductVariation productVariation = new ProductVariation();
        productVariation.setProduct(product);
        productVariation.setActive(true);
        Category category = product.getCategory();
        Map<String, String> metadata = productVariationDto.getMetadata();
        for (Map.Entry<String, String> fieldvalues : metadata.entrySet()) {
            String field = fieldvalues.getKey();
            String value = fieldvalues.getValue();
            CategoryMetadataField categoryMetadataField = categoryMetadataFieldRespository.findByName(field);
            CategoryMetadataFieldValues categoryMetadataFieldValues = categoryMetadataFieldValueRepository.findByAllId(categoryMetadataField.getId(), category.getId());
            List<String> list = Arrays.asList(categoryMetadataFieldValues.getValue().split(","));

            if (!list.contains(value)) {
                throw new EntityNotFoundException("Value not found");
            }
        }
List<ProductVariation> productVariationList = productVariationRepository.findAllSameDetails(product.getId(),productVariationDto.getQuantity());
        for(ProductVariation productVariation1:productVariationList){
            Map<String,String> metadata1 = productVariation1.getMetadata();
           if(metadata.equals(metadata1)){
               throw new NotUniqueException("These details are already mentioned");
           }
        }
        System.out.println(metadata);
        productVariation.setMetadata(metadata);

        System.out.println(productVariation.getMetadata());
        if (productVariationDto.getPrice() < 0) {
            throw new ValidationException("Price must be 0 or more");
        }
        productVariation.setPrice(productVariationDto.getPrice());
        if (productVariationDto.getQuantity() < 0) {
            throw new ValidationException("Quantity must be ) or more");
        }
        productVariation.setQuantityAvailable(productVariationDto.getQuantity());
        if (!productVariationDto.getImagePath().matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)")) {
            throw new ValidationException("format of image is not correct(either use jpg ,png,jpeg or bmp extension)");
        }
        productVariation.setPrimaryImageName(productVariationDto.getImagePath());
        productVariationRepository.save(productVariation);
        return "Product variation added successfully!";

    }

    @Override
    public Product viewProduct(String  id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        if (!product.isActive() || product.isDeleted())
            throw new NotActiveException(PRODUCT_NOT_ACTIVE);
        return product;
    }

    @Override
    public String activateProduct(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        product.setActive(true);
        productRepository.save(product);
        return "Product activated successfully";
    }

    @Override
    public String deactivateProduct(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        product.setActive(false);
        productRepository.save(product);
        return "Product activated successfully";
    }

    @Override
    public Product viewSellerProduct(String  id, String username) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        if (product.getSeller() != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException(PRODUCT_ERROR);

        }
        if (!product.isActive() || product.isDeleted()) {
            throw new NotActiveException(PRODUCT_NOT_ACTIVE);
        }
        return product;
    }

    @Override
    public ProductVariation viewSellerProductVariation(String id, String username) {
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(id);
        if (!productVariationOptional.isPresent())
            throw new EntityNotFoundException("product variation is not found");
        ProductVariation productVariation = productVariationOptional.get();
        if (productVariation.getProduct().getSeller() != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException(PRODUCT_ERROR);

        }
        if (!productVariation.getProduct().isActive() || productVariation.getProduct().isDeleted()) {
            throw new NotActiveException(PRODUCT_NOT_ACTIVE);
        }
        return productVariation;
    }

    @Override
    public List<Product> viewAllSellerProducts(String username) {
        Seller seller = sellerRepository.findByUsername(username);
        List<Product> products = productRepository.findBySeller(PageRequest.of(0, 10, Sort.Direction.ASC, "id"), seller);
        return products.stream().filter(Product::isActive).collect(Collectors.toList());

    }

    @Override
    public List<ProductVariation> viewAllSellerProductVariations(String id, String username) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        if (product.getSeller() != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException(PRODUCT_ERROR);
        }
        if (!product.isActive() || product.isDeleted()) {
            throw new NotActiveException(PRODUCT_NOT_ACTIVE);
        }
        return productVariationRepository.findByProduct(PageRequest.of(0, 10, Sort.Direction.ASC, "id"), product);

    }

    @Override
    public String deleteProduct(String  id, String username) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        if (product.getSeller() != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException(PRODUCT_ERROR);

        }
        product.setDeleted(true);
        product.setActive(false);

        return "Product deleted successfully";
    }


    @Override
    public String updateProduct(String id, UpdateProductDto updateProductDto, String username) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            throw new EntityNotFoundException(PRODUCT_ERROR);
        Product product = productOptional.get();
        Seller seller = product.getSeller();
        if (seller != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException(PRODUCT_ERROR);

        }
        if (updateProductDto.getName() != null) {
            Product product1 = productRepository.findUniqueName(seller.getId(), product.getBrand(), product.getCategory().getId(), updateProductDto.getName());
            if (product1 != null) {
                throw new NotUniqueException("Name is not unique");
            }
            product.setName(updateProductDto.getName());
        }
        if (updateProductDto.getDescription() != null) {
            product.setDescription(updateProductDto.getDescription());
        }
        if (updateProductDto.isCancellable() != null) {
            product.setCancellable(updateProductDto.isCancellable());
        }
        if (updateProductDto.isReturnable() != null) {
            product.setReturnable(updateProductDto.isReturnable());
        }
        productRepository.save(product);
        return "product updated successfully";
    }

    @Override
    public String updateProductVariation(String id, UpdateProductVariationDto updateProductVariationDto, String username) {
        Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(id);
        if (!productVariationOptional.isPresent())
            throw new EntityNotFoundException("Product variation not found");
        ProductVariation productVariation = productVariationOptional.get();
        if (productVariation.getProduct().getSeller() != sellerRepository.findByUsername(username)) {
            throw new EntityNotFoundException("Product not related to this seller");
        }
        if (updateProductVariationDto.getQuantity() != null) {
            productVariation.setQuantityAvailable(updateProductVariationDto.getQuantity());
        }
        if (updateProductVariationDto.getPrice() != null) {
            productVariation.setPrice(updateProductVariationDto.getPrice());
        }
        if (updateProductVariationDto.getPrimaryImage() != null) {
            productVariation.setPrimaryImageName(updateProductVariationDto.getPrimaryImage());
        }
        if (updateProductVariationDto.getActive() != null) {
            productVariation.setActive(updateProductVariationDto.getActive());
        }
        if (updateProductVariationDto.getMetadata() != null) {
            Map<String, String> metadata = updateProductVariationDto.getMetadata();
            for (Map.Entry<String, String> fieldvalues : metadata.entrySet()) {
                String field = fieldvalues.getKey();
                String value = fieldvalues.getValue();
                CategoryMetadataField categoryMetadataField = categoryMetadataFieldRespository.findByName(field);
                CategoryMetadataFieldValues categoryMetadataFieldValues = categoryMetadataFieldValueRepository.findByAllId(categoryMetadataField.getId(), productVariation.getProduct().getCategory().getId());
                List<String> list = Arrays.asList(categoryMetadataFieldValues.getValue().split(","));

                if (!list.contains(value)) {
                    throw new EntityNotFoundException("Value not found");
                }
            }
            productVariation.setMetadata(metadata);
        }
        productVariationRepository.save(productVariation);
        return "product variation updated successfully";

    }

    @Override
    public List<ProductVariation> viewCustomerProduct(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException(PRODUCT_ERROR);
        }
        Product product = productOptional.get();
        if (!product.isActive() || product.isDeleted()) {
            throw new NotActiveException(PRODUCT_NOT_ACTIVE);
        }
        return productVariationRepository.findByProduct(PageRequest.of(0, 10, Sort.Direction.ASC, "id"), product);
    }

    @Override
    public List<ProductVariation> viewAllProducts() {
        List<ProductVariation> productVariations = productVariationRepository.findAllProductVariation(PageRequest.of(0, 10, Sort.Direction.ASC, "id"));
        return productVariations.stream().filter(e -> e.getProduct().isActive() || !e.getProduct().isDeleted()).collect(Collectors.toList());
    }


    @Override
    public ProductFilterDto viewSimilarProducts(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new EntityNotFoundException("product not found");
        }
        Product product = productOptional.get();

        ProductFilterDto productFilterDto = new ProductFilterDto();
        Category category = product.getCategory();
        Category parentCategory = category.getCategory();
        List<Product> products;
        products = productRepository.findOtherProducts(id, category.getId());
        List<Category> categories = categoryRepository.findLeafNode();
        for (Category category1 : categories) {
            if (category1.getCategory() == parentCategory) {
                List<Product> products1 = productRepository.findOtherProducts(id, category1.getId());
                products.addAll(products1);
            }

        }

        productFilterDto.setProductList(products.stream().filter(Product::isActive).collect(Collectors.toList()));
        return productFilterDto;
    }
}
