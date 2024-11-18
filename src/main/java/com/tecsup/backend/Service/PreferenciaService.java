package com.tecsup.backend.Service;

import com.tecsup.backend.Model.Categoria;
import com.tecsup.backend.Model.Preferencia;
import com.tecsup.backend.Model.Usuario;
import com.tecsup.backend.Repository.PreferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

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
    public void guardarPreferencias(Usuario usuario, List<Long> categoryIds) {
        // Eliminar las preferencias actuales del usuario
        preferenciaRepository.deleteByUsuario(usuario);

        // Crear y guardar las nuevas preferencias
        for (Long categoryId : categoryIds) {
            Preferencia preferencia = new Preferencia();
            preferencia.setUsuario(usuario);
            Categoria categoria = new Categoria();
            categoria.setId(categoryId); // Asumimos que el id de la categoría ya existe
            preferencia.setCategoria(categoria);
            preferenciaRepository.save(preferencia);
        }
    }
}
