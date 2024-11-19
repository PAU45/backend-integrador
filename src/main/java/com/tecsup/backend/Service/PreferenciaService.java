package com.tecsup.backend.Service;

import com.tecsup.backend.Model.Categoria;
import com.tecsup.backend.Model.Preferencia;
import com.tecsup.backend.Model.Usuario;
import com.tecsup.backend.Repository.CategoriaRepository;
import com.tecsup.backend.Repository.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository; // Agregado para verificar la existencia de categorías

    // Método para obtener las preferencias de un usuario
    public List<Categoria> obtenerPreferenciasDeUsuario(Usuario usuario) {
        if (usuario == null) {
            return Collections.emptyList(); // Retorna una lista vacía si el usuario es nulo
        }

        // Recuperamos las preferencias asociadas al usuario
        List<Preferencia> preferencias = preferenciaRepository.findByUsuario(usuario);

        // Convertimos las preferencias en una lista de categorías
        return preferencias.stream()
                .map(Preferencia::getCategoria)
                .collect(Collectors.toList());
    }

    // Método para guardar las preferencias de un usuario
    @Transactional // Asegura que la operación se haga dentro de una transacción
    public void guardarPreferencias(Usuario usuario, List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new IllegalArgumentException("No se han seleccionado categorías.");
        }

        // Eliminar las preferencias actuales del usuario antes de guardar las nuevas
        preferenciaRepository.deleteByUsuario(usuario);

        // Crear y guardar las nuevas preferencias
        for (Long categoryId : categoryIds) {
            // Verificar que la categoría exista
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoryId);
            if (!categoriaOpt.isPresent()) {
                throw new IllegalArgumentException("La categoría con ID " + categoryId + " no existe.");
            }

            Preferencia preferencia = new Preferencia();
            preferencia.setUsuario(usuario);
            preferencia.setCategoria(categoriaOpt.get()); // Asignar la categoría existente

            preferenciaRepository.save(preferencia); // Guardar la nueva preferencia
        }
    }
}
