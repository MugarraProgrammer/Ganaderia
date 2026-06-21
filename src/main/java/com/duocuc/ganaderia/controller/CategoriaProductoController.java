package com.duocuc.ganaderia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.service.CategoriaProductoService;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController 
@RequestMapping("/api/categorias")
@RequiredArgsConstructor

public class CategoriaProductoController {
    private final CategoriaProductoService categoriaService;
    @GetMapping public ResponseEntity<List<CategoriaProducto>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaProducto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombre(nombre).map(List::of).orElse(List.of()));
    }
    @PostMapping
    public ResponseEntity<CategoriaProducto> crear(@Valid @RequestBody CategoriaProducto c) {
        return ResponseEntity.status(201).body(categoriaService.guardar(c)); 
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProducto> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaProducto d){
        return categoriaService.obtenerPorId(id).map(x -> { d.setId(id); return ResponseEntity.ok(categoriaService.guardar(d));}).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (categoriaService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    

    
    
    
    
    

}
