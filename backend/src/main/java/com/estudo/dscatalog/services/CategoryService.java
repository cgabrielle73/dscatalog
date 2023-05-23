package com.estudo.dscatalog.services;

import com.estudo.dscatalog.dto.CategoryDTO;
import com.estudo.dscatalog.entities.Category;
import com.estudo.dscatalog.repositories.CategoryRepository;
import com.estudo.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        //Optional: para nunca ter valor nulo. Mas dentro dele, pode ou não ter categorias
        Optional<Category> obj = categoryRepository.findById(id);
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);

    };
}
