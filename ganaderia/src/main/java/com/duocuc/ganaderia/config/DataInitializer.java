package com.duocuc.ganaderia.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.duocuc.ganaderia.model.*;
import com.duocuc.ganaderia.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
@Slf4j @Component @RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaProductoRepository categoriaRepository;
    private final ProductoRepository productoRepository;
 
    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0 && productoRepository.count() > 0) return;
        
        CategoriaProducto vacuno = obtenerOCrear("Vacuno");
        CategoriaProducto cerdo = obtenerOCrear("Cerdo");
        CategoriaProducto ave = obtenerOCrear("Aves");
 
        productoRepository.saveAll(List.of(
            new Producto(null, "Lomo Vetado 1kg", "CR-001", new BigDecimal("14500"), "SuperCarne", "Vacio", "Parrilla", LocalDate.of(2026, 6, 10), vacuno),
            new Producto(null, "Pulpa de Cerdo 1kg", "PO-001", new BigDecimal("5400"), "Doña Carne", "Bandeja", "Horno", LocalDate.of(2026, 5, 28), cerdo),
            new Producto(null, "Pechuga de Pollo 1kg", "AV-001", new BigDecimal("4200"), "PolloVivo", "Bandeja", "Deshuesada", LocalDate.of(2026, 5, 30), ave)
        ));
    }

    private CategoriaProducto obtenerOCrear(String n) {
        return categoriaRepository.buscarPorNombreIgualIgnoreCase(n).orElseGet(() ->
            categoriaRepository.save(new CategoriaProducto(null, n, "Cortes de " + n)));
    }
}