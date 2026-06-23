package com.duocuc.ganaderia.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duocuc.ganaderia.dto.ProductoResponseDTO;
import com.duocuc.ganaderia.dto.ProductoRequestDTO;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.service.ProductoService;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Operaciones para gestionar productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o la categoría no existe")
    })
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO p) {
        return ResponseEntity.status(201).body(productoService.guardar(p)); 
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO p){
        return ResponseEntity.ok(productoService.actualizar(id, p));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron productos")
    })
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @GetMapping("/buscar-empieza")
    @Operation(summary = "Buscar productos por prefijo")
    public ResponseEntity<List<Producto>> buscarPorNombreQueEmpieceCon(@RequestParam String prefijo){
        return ResponseEntity.ok(productoService.buscarPorNombreQueEmpieceCon(prefijo));
    }

    @GetMapping("/buscar-termina")
    @Operation(summary = "Buscar productos por sufijo")
    public ResponseEntity<List<Producto>> buscarPorNombreQueTermineCon(@RequestParam String sufijo){
        return ResponseEntity.ok(productoService.buscarPorNombreQueTermineCon(sufijo));
    }

    @GetMapping("/buscar-menor")
    @Operation(summary = "Buscar productos con precio menor o igual al máximo")
    public ResponseEntity<List<Producto>> buscarPorPrecioMenor(@RequestParam BigDecimal max){
        return ResponseEntity.ok(productoService.buscarPorPrecioMenor(max));
    }
    
    @GetMapping("/buscar-minimo")
    @Operation(summary = "Buscar productos con precio mayor o igual al mínimo")
    public ResponseEntity<List<Producto>> buscarPorPrecioMinimo(@RequestParam BigDecimal min){
        return ResponseEntity.ok(productoService.buscarPorPrecioMinimo(min));
    }

    @GetMapping("/buscar-entre")
    @Operation(summary = "Buscar productos entre dos precios")
    public ResponseEntity<List<Producto>> buscarPorPrecioEntre(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        return ResponseEntity.ok(productoService.buscarPorPrecioEntre(min, max));
    }

    @GetMapping("/buscar-sql") 
    @Operation(summary = "Buscar productos con consulta nativa")
    public ResponseEntity<List<Producto>> buscarNativo(@RequestParam String texto){
        return ResponseEntity.ok(productoService.buscarPorNombreNativo(texto));
    }
}
