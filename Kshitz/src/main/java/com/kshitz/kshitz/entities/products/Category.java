package com.kshitz.kshitz.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.Set;

@Document
public class Category {

    private String  id;

    private String name;

    @JsonIgnore
    private Category parent;

    @JsonIgnore
    Set<Category> categories = new HashSet<>();


    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Category getCategory() {
        return parent;
    }

    public void setCategory(Category category) {
        this.parent = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
