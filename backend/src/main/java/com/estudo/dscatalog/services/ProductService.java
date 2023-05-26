package com.estudo.dscatalog.services;

import com.estudo.dscatalog.dto.ProductDTO;
import com.estudo.dscatalog.entities.Product;
import com.estudo.dscatalog.repositories.ProductRepository;
import com.estudo.dscatalog.services.exceptions.DatabaseException;
import com.estudo.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

// Registra como um componente que participa do sistema de injeção de dependencia automatizado do spring.
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //Garante a transação do banco
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> products = productRepository.findAll(pageRequest);
        return products.map(prod -> new ProductDTO(prod));
    };
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        //Optional: para nunca ter valor nulo. Mas dentro dele, pode ou não ter categorias
        Optional<Product> obj = productRepository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());

    };

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product product = new Product();
//        product.setName(productDTO.getName());
        product = productRepository.save(product);
        return new ProductDTO(product);
    };

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            //Não toca no banco de dados. Instancia um objeto provisório com os dados. Após mandar salvar, que ele bate no banco e salva os dados
            Product product = productRepository.getReferenceById(id);
//            product.setName(productDTO.getName());
            product = productRepository.save(product);
            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    };

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation!");
        }
    };
}
