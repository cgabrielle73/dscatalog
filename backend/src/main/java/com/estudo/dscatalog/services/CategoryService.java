package com.estudo.dscatalog.services;

import com.estudo.dscatalog.dto.CategoryDTO;
import com.estudo.dscatalog.entities.Category;
import com.estudo.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Registra como um componente que participa do sistema de injeção de dependencia automatizado do spring.
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    //Garante a transação do banco
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(cat -> new CategoryDTO(cat)).collect(Collectors.toList());
    };
}
