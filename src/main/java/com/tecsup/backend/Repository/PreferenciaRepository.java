package com.tecsup.backend.Repository;

import com.tecsup.backend.Model.Preferencia;
import com.tecsup.backend.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Long> {
    List<Preferencia> findByUsuario(Usuario usuario);

    // Método para eliminar preferencias por usuario
    void deleteByUsuario(Usuario usuario);
}
