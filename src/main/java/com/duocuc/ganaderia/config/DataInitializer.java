package com.duocuc.ganaderia.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.repository.CategoriaProductoRepository;
import com.duocuc.ganaderia.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaProductoRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        CategoriaProducto vacuno = obtenerOCrear("Vacuno");
        CategoriaProducto cerdo = obtenerOCrear("Cerdo");
        CategoriaProducto ave = obtenerOCrear("Aves");

        if (productoRepository.count() > 0) {
            return;
        }

        productoRepository.saveAll(List.of(
            new Producto(null, "Lomo Vetado 1kg", "CR-001", new BigDecimal("14500"), "SuperCarne", "Vacio", "Parrilla", LocalDate.of(2026, 6, 10), vacuno),
            new Producto(null, "Pulpa de Cerdo 1kg", "PO-001", new BigDecimal("5400"), "Dona Carne", "Bandeja", "Horno", LocalDate.of(2026, 5, 28), cerdo),
            new Producto(null, "Pechuga de Pollo 1kg", "AV-001", new BigDecimal("4200"), "PolloVivo", "Bandeja", "Deshuesada", LocalDate.of(2026, 5, 30), ave)
        ));
    }

    private CategoriaProducto obtenerOCrear(String nombre) {
        return categoriaRepository.buscarPorNombreIgualIgnoreCase(nombre).orElseGet(() ->
            categoriaRepository.save(new CategoriaProducto(null, nombre, "Cortes de " + nombre)));
    }
}
