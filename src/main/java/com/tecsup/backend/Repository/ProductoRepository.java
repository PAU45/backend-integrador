package com.tecsup.backend.Repository;
import com.tecsup.backend.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}