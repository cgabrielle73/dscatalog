package com.estudo.dscatalog.resources;

import com.estudo.dscatalog.dto.ProductDTO;
import com.estudo.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResources {
    @Autowired
    private ProductService productService;
    //ResponseEntity: encapsula a resposta http
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            //RequestParam serve para quando vc não quer que um argumento seja obrigatório na url
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ProductDTO> products = productService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(products);
    }
    @GetMapping(value="/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok().body(productDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO) {
        productDTO = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO = productService.update(id, productDTO);
        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
