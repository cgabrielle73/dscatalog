package com.estudo.dscatalog.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//Serializable serve para transformar o objeto em bytes e conseguir passá-lo na rede.
@Entity
@Table(name="category")
public class Category implements Serializable {
    @Id
    //Id incrementável
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category(){}
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
