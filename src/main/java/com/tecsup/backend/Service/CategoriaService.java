package com.tecsup.backend.Service;

import com.tecsup.backend.Model.Categoria;
import com.tecsup.backend.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository; // El repositorio de categoría

    public List<Categoria> findAll() {
        return categoriaRepository.findAll(); // Obtener todas las categorías desde la base de datos
    }
}
