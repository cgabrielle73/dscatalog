package com.estudo.dscatalog.resources;

import com.estudo.dscatalog.dto.CategoryDTO;
import com.estudo.dscatalog.entities.Category;
import com.estudo.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {
    @Autowired
    private CategoryService categoryService;
    //ResponseEntity: encapsula a resposta http
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }
}
