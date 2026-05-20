package com.duocuc.ganaderia.repository;
import java.util.Optional;
import com.duocuc.ganaderia.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long>{

    @Query("SELECT C FROM CategoriaProducto C WHERE UPPER(c.nombre) = UPPER(:nombre)")
    Optional<CategoriaProducto> buscarPorNombreIgualIgnoreCase(String nombre);
}
