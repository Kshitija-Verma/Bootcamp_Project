package com.kshitz.kshitz.services;

import com.kshitz.kshitz.dtos.*;
import com.kshitz.kshitz.entities.products.*;
import com.kshitz.kshitz.exceptions.EntityNotFoundException;
import com.kshitz.kshitz.exceptions.NotUniqueException;
import com.kshitz.kshitz.repositories.*;
import com.kshitz.kshitz.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.kshitz.kshitz.utilities.StringConstants.CATEGORY_ERROR;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMetadataFieldRespository categoryMetadataFieldRespository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMetadataFieldValueRepository categoryMetadataFieldValueRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;


    @Override
    public String addCategoryMetadata(CategoryMetadataDto categoryMetadataDto) {
        CategoryMetadataField categoryMetadataField = new CategoryMetadataField();
        if (categoryMetadataFieldRespository.findByName(categoryMetadataDto.getName()) != null)
            throw new NotUniqueException("name is not unique");
        categoryMetadataField.setName(categoryMetadataDto.getName());
        categoryMetadataFieldRespository.save(categoryMetadataField);
        return categoryMetadataField.getId();
    }

    @Override
    public Iterable<CategoryMetadataField> listData() {
        return categoryMetadataFieldRespository.findAll();
    }

    @Override
    public String addNewCategory(CategoryDto categoryDto) {
        Category category = new Category();
        if (categoryRepository.findByName(categoryDto.getName()) != null)
            throw new NotUniqueException("Name must be unique");
        category.setName(categoryDto.getName());
        if (categoryDto.getId() != null) {
            Optional<Category> category1 = categoryRepository.findById(categoryDto.getId());
            if (!category1.isPresent()) {
                throw new EntityNotFoundException(CATEGORY_ERROR);
            }

            category.setCategory(category1.get());
        } else {
            categoryRepository.save(category);
            category.setCategory(null);
        }
        categoryRepository.save(category);
        return category.getId();

    }

    @Override
    public List<CategoryDisplayDto> viewCategory(String id) {
        List<CategoryDisplayDto> categories = new ArrayList<>();
        CategoryDisplayDto categoryDisplayDto = new CategoryDisplayDto();
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            throw new EntityNotFoundException(CATEGORY_ERROR);
        Category category1 = category.get();
        if(category1.getCategory()==null){
            categoryDisplayDto.setId(category1.getId());
            categoryDisplayDto.setName(category1.getName());
            categoryDisplayDto.setParentId(null);

            categories.add(categoryDisplayDto);
            return categories;
        }
        if(category1.getCategory()!=null) {
            categoryDisplayDto.setId(category1.getId());
            categoryDisplayDto.setName(category1.getName());
            categoryDisplayDto.setParentId(category1.getCategory().getId());

            categories.add(categoryDisplayDto);
        }
        if (!category1.getId().equals(category1.getCategory().getId())) {
            category1 = category1.getCategory();
            if (category1.getCategory() != null) {
                String parentid = category1.getCategory().getId();
                CategoryDisplayDto categoryDisplayDto1 = new CategoryDisplayDto();
                categoryDisplayDto1.setId(category1.getId());
                categoryDisplayDto1.setName(category1.getName());
                categoryDisplayDto1.setParentId(parentid);
                categories.add(categoryDisplayDto1);
            }
        }
        return categories;
    }

    @Override
    public List<Category> viewAllCategory() {
        List<Category> categories = categoryRepository.findLeafNode();
        List<Category> categories1 = categoryRepository.findInnerNode();
        List<Category> categories2 = categoryRepository.findRootNode();
        List<Category> categoryList = new ArrayList<>();
        categoryList.addAll(categories);
        categoryList.addAll(categories1);
        categoryList.addAll(categories2);
        return categoryList;
    }

    @Override
    public String updateCategory(String id, CategoryDto categoryDto) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent())
            throw new EntityNotFoundException(CATEGORY_ERROR);
        Category category1 = category.get();
        if (categoryDto.getId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());

            if (!categoryOptional.isPresent()) {
                throw new EntityNotFoundException(CATEGORY_ERROR);
            }
            Category category2 = categoryOptional.get();
            category1.setCategory(category2.getCategory());
        }
        if (categoryDto.getName() != null)
            category1.setName(categoryDto.getName());
        categoryRepository.save(category1);
        return "Category Updated Successfully";

    }

    @Override
    public String addFieldValueCategory(CategoryfieldDto categoryfieldDto) {
        if(categoryfieldDto.getCategoryId()==null||categoryfieldDto.getMetadataId()==null){
            throw new EntityNotFoundException("Metadata id or category id is not given");
        }
        Optional<CategoryMetadataField> categoryMetadataFieldOptional = categoryMetadataFieldRespository.findById(categoryfieldDto.getMetadataId());
        if (!categoryMetadataFieldOptional.isPresent()) {
            throw new EntityNotFoundException("Category metadata field not found with this id");
        }
        CategoryMetadataField categoryMetadataField = categoryMetadataFieldOptional.get();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryfieldDto.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new EntityNotFoundException(CATEGORY_ERROR);
        }
        Category category = categoryOptional.get();
        CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();
        CategoryMetadataIdentity categoryMetadataIdentity = new CategoryMetadataIdentity();
        categoryMetadataIdentity.setCategory(category);
        categoryMetadataIdentity.setCategoryMetadataField(categoryMetadataField);
        categoryMetadataFieldValues.setCategoryMetadataIdentity(categoryMetadataIdentity);
        categoryMetadataFieldValues.setValue(categoryfieldDto.getValue());

        categoryMetadataFieldValueRepository.save(categoryMetadataFieldValues);
        return "Field Added to the category";
    }

    @Override
    public List<CategoryMetadataFieldValues> viewSellerCategories() {

        return categoryMetadataFieldValueRepository.findAllLeafCategory();
    }

    @Override
    public List<Category> viewCustomerCategory() {

        return categoryRepository.findRootNode();
    }


    @Override
    public CategoryFilterDto viewFilterCategory(String id) {
        int flag = 0;
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new EntityNotFoundException(CATEGORY_ERROR);
        }
        CategoryFilterDto categoryFilterDto;
        Category category = categoryOptional.get();
        List<Category> categories = categoryRepository.findInnerNode();
        List<Category> categories1 = categoryRepository.findLeafNode();
        List<Category> categories2 = categoryRepository.findRootNode();
        for (Category category1 : categories) {
            if (category1 == category) {
                flag = 1;
                break;
            }
        }
        for (Category category1 : categories2) {
            if (category1 == category)
                throw new EntityNotFoundException("Too many categories found (please select any sub-category)");
        }

        if (flag == 1) {
            categoryFilterDto = findResultIfInnerNode(category);
            return categoryFilterDto;
        }

        categoryFilterDto = findResultListIfLeafNode(categories1, category);
        return categoryFilterDto;

    }


    public CategoryFilterDto findResultListIfLeafNode(List<Category> categories, Category category) {
        Map<String, String> brandlist = new HashMap<>();
        Map<String, Double> productVariationsMap = new HashMap<>();
        Map<String, String> categoryMetadataFieldValuesMap = new HashMap<>();
        CategoryFilterDto categoryFilterDto = new CategoryFilterDto();
        for (Category category1 : categories) {
            if (category1 == category) {
                List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList = categoryMetadataFieldValueRepository.findByCategory(category.getId());
                List<Product> products = productRepository.findByCategoryId(category.getId());
                for (Product product : products)
                    if (product != null) {
                        Map<String, Double> productVariationFirst = new HashMap<>();
                        brandlist.put(product.getBrand(), category.getId());
                        List<ProductVariation> productVariations1 = productVariationRepository.findByProduct(PageRequest.of(0, 10, Sort.Direction.ASC, "id"), product);
                        Double max = 0.0;
                        Double min = 10000000.0;
                        for (ProductVariation productVariation : productVariations1) {
                            if (productVariation.getPrice() > max) {
                                max = productVariation.getPrice();
                            }
                            if (productVariation.getPrice() < min) {
                                min = productVariation.getPrice();
                            }
                            productVariationFirst.put("Name: " + productVariation.getProduct().getName() + " , Brand: " + productVariation.getProduct().getBrand() + " , CategoryId: " + productVariation.getProduct().getCategory().getId(), productVariation.getPrice());
                        }
                        for (Map.Entry<String, Double> productvariationMapSecond : productVariationFirst.entrySet()) {
                            Double value = productvariationMapSecond.getValue();
                            if (value.equals(max) || value.equals(min))
                                productVariationsMap.put(productvariationMapSecond.getKey(), value);
                        }
                    }


                for (CategoryMetadataFieldValues categoryMetadataFieldValues : categoryMetadataFieldValuesList) {
                    categoryMetadataFieldValuesMap.put("field: " + categoryMetadataFieldValues.getCategoryMetadataIdentity().getCategoryMetadataField().getName() + " , Value : " + categoryMetadataFieldValues.getValue(), " , CategoryId: " + categoryMetadataFieldValues.getCategoryMetadataIdentity().getCategory().getId());
                }
            }
        }
        categoryFilterDto.setCategoryMetadataFieldValues(categoryMetadataFieldValuesMap);
        categoryFilterDto.setBrands(brandlist);
        categoryFilterDto.setProductVariations(productVariationsMap);
        return categoryFilterDto;

    }

    public CategoryFilterDto findResultIfInnerNode(Category category) {
        Map<String, String> brandlist = new HashMap<>();
        Map<String, Double> productVariationsMap = new HashMap<>();
        Map<String, String> categoryMetadataFieldValuesMap = new HashMap<>();
        CategoryFilterDto categoryFilterDto = new CategoryFilterDto();
        List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList1 = categoryMetadataFieldValueRepository.findAllLeafCategory();
        for (CategoryMetadataFieldValues categoryMetadataFieldValues : categoryMetadataFieldValuesList1) {
            if (categoryMetadataFieldValues.getCategoryMetadataIdentity().getCategory().getCategory().getId().equals(category.getId()))
                categoryMetadataFieldValuesMap.put("field: " + categoryMetadataFieldValues.getCategoryMetadataIdentity().getCategoryMetadataField().getName() + " , Value : " + categoryMetadataFieldValues.getValue(), " , CategoryId: " + categoryMetadataFieldValues.getCategoryMetadataIdentity().getCategory().getId());
        }
        Set<Category> categorieset = category.getCategories();
        for (Category category1 : categorieset) {
            List<Product> products = productRepository.findByCategoryId(category1.getId());
            for (Product product : products)
                if (product != null) {
                    Map<String, Double> productVariationFirst = new HashMap<>();
                    brandlist.put(product.getBrand(), category.getId());
                    List<ProductVariation> productVariations1 = productVariationRepository.findByProduct(PageRequest.of(0, 10, Sort.Direction.ASC, "id"), product);
                    Double max = 0.0;
                    Double min = 1000000.0;
                    for (ProductVariation productVariation : productVariations1) {
                        if (productVariation.getPrice() > max) {
                            max = productVariation.getPrice();
                        }
                        if (productVariation.getPrice() < min) {
                            min = productVariation.getPrice();
                        }
                        productVariationFirst.put("Name: " + productVariation.getProduct().getName() + " , Brand: " + productVariation.getProduct().getBrand() + " , CategoryId: " + productVariation.getProduct().getCategory().getId(), productVariation.getPrice());
                    }
                    for (Map.Entry<String, Double> productvariationMapSecond : productVariationFirst.entrySet()) {
                        Double value = productvariationMapSecond.getValue();
                        if (value.equals(max) || value.equals(min))
                            productVariationsMap.put(productvariationMapSecond.getKey(), value);
                    }
                }
        }
        categoryFilterDto.setCategoryMetadataFieldValues(categoryMetadataFieldValuesMap);
        categoryFilterDto.setBrands(brandlist);
        categoryFilterDto.setProductVariations(productVariationsMap);
        return categoryFilterDto;

    }
}
