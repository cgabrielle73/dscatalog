package com.estudo.dscatalog.services;

import com.estudo.dscatalog.dto.CategoryDTO;
import com.estudo.dscatalog.entities.Category;
import com.estudo.dscatalog.repositories.CategoryRepository;
import com.estudo.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity);

    };

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        return new CategoryDTO(category);
    };

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try {
            //Não toca no banco de dados. Instancia um objeto provisório com os dados. Após mandar salvar, que ele bate no banco e salva os dados
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryDTO.getName());
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

    };
}
