package com.duocuc.ganaderia.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoRequestDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;
    
    @NotNull(message = "El precio no puede ser nulo")
    private BigDecimal precio;
    
    private String marca;
    private String presentacion;
    private String notas;
    private LocalDate fechaVencimiento; 
    
    @NotNull(message = "La categoría no puede ser nula")
    private Long categoriaId;
}