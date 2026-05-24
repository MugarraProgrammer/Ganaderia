package com.duocuc.ganaderia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duocuc.ganaderia.dto.ProductoResponseDTO;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.service.ProductoService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor

public class ProductoController {
    private final ProductoService productoService;
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO p) {
        return ResponseEntity.status(201).body(productoService.guardar(p)); 
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO p){
        return ResponseEntity.ok(productoService.actualizar(id, p));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/buscar-empieza")
    public ResponseEntity<List<Producto>> buscarPorNombreQueEmpieceCon(@RequestParam String prefijo){
        return ResponseEntity.ok(productoService.uscarPorNombreQueEmpieceCon(prefijo));
    }

    @GetMapping("/buscar-termina")
    public ResponseEntity<List<Producto>> buscarPorNombreQueTermineCon(@RequestParam String sufijo){
        return ResponseEntity.ok(productoService.buscarPorNombrreQueTermineCon(sufijo));
    }

    @GetMapping("/buscar-menor")
    public ResponseEntity<List<Producto>> buscarPorPrecioMenor(@RequestParam BigDecimal max){
        return ResponseEntity.ok(productoService.buscarPorPrecioMenorQue(max));

    }
    
    @GetMapping("/buscar-minimo")
    public ResponseEntity<List<Producto>> buscarPorPrecioMinimo(@RequestParam BigDecimal min){
        return ResponseEntity.ok(productoService.buscarPorPrecioMinimo(min));

    }
    @GetMapping("/buscar-entre")
    public ResponseEntity<List<Producto>> buscarPorPrecioEntre(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        return ResponseEntity.ok(productoService.buscarPorPrecioEntre(min, max));

    }

    @GetMapping("/buscar-sql") public ResponseEntity<List<Producto>> buscarNativo(@RequestParam String texto){
        return ResponseEntity.ok(productoService.buscarPorNombreNativo(texto));
    }
    
    
    
    
   
    
    

}
