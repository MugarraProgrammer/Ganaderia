package com.duocuc.ganaderia.repository;

import com.duocuc.ganaderia.model.Producto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))")
    List<Producto> buscarPorNombreContieneIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT(:prefijo, '%'))")
    List<Producto> buscarPorNombreEmpiezaConIgnoreCase(@Param("prefijo") String prefijo);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :sufijo))")
    List<Producto> buscarPorNombreTerminaConIgnoreCase(@Param("sufijo") String sufijo);

    @Query("SELECT p FROM Producto p WHERE p.precio < :precio")
    List<Producto> buscarPorPrecioMenorQue(@Param("precio") BigDecimal precio);

    @Query("SELECT p FROM Producto p WHERE p.precio >= :precio")
    List<Producto> buscarPorPrecioMayorOIgual(@Param("precio") BigDecimal precio);

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max")
    List<Producto> buscarPorPrecioEntre(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId")
    List<Producto> buscarPorCategoriaId(@Param("categoriaId") Long categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.precio <= :precioMax ORDER BY p.precio DESC")
    List<Producto> buscarProductosBajoPresupuesto(@Param("precioMax") BigDecimal precioMax);

    @Query(value = "SELECT * FROM producto WHERE nombre LIKE CONCAT('%', :texto, '%')", nativeQuery = true)
    List<Producto> buscarPorNombreNativo(@Param("texto") String texto);
}
