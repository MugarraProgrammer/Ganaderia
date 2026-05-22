package com.duocuc.ganaderia.repository;
import  com.duocuc.ganaderia.model.Prducto;
import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

    @Query("SELECT p FROM PRODUCTO p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('&', :NOMBRE, '&'))")
    List<Producto> buscarPorNombreContieneIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONTAT(:prefijo, '&'))")
    List<Producto> buscarPorNombreEmpiezaConIgnoreCase(String prefijo);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('&', :sufijo))")
    List<Producto> buscarPorNombreTerminaConIgnoreCase(String sufijo);
    
    
}
