package com.duocuc.ganaderia.config;


import java.util.List;
import com.duocuc.ganaderia.model.Producto.*;
import com.duocuc.ganaderia.repository.*;
import com.duocuc.ganaderia.service.CategoriaProductoService;
import com.duocuc.ganaderia.service.ProductoService;

import java.math.BigDecimal;
import java.time.LocalDate;


import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor



public class DataInitializer implements CommandLineRunner {
    private final CategoriaProductoService categoriaProductoService;
    private final ProductoService productoService;
    
    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0 && productoRepository.count() > 0) 
            return;

        CategoriaValidation vacuno = obtenerOCrear("Vacuno");
        CategoriaValidation cerdo = obtenerOCrear("Cerdo");
        CategoriaValidation ave = obtenerOCrear("Aves");

        productRepository.saveAll(List.of(new Producto(null, "Lomo Vetado 1kg", "CR-001", new BigDecimal("14500"), "SuperCarne", "Vacio", "Parrilla", LocalDate.of(2026, 6, 10), vacuno)
        , new Producto(null, "Pulpa de Cerdo 1kg", "PO-001", new BigDecimal("5400"), "Doña Carne", "Bandeja", "Horno", LocalDate.of(2026, 5, 28, cerdo)
        , new producto(null, "Pechuga de Pollo 1kg", "AV-001", new BigDecimal("4200"), "Pollo Vivo", "Bandeja", "Deshuesada", LocalDate.of(2026, 5, 30), ave))));
    }

    private CategoriaProducto obtenerOCrear(String n) {
        return categoriaRepository.buscarPorNombreIgualIgnoreCase(n).orElseGet(() -> categoriaRespository.save(new CategoriaProducto(null, n, "Cortes de" + n)));
    }


    

}
