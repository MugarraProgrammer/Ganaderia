package com.duocuc.ganaderia.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoResponseDTO {

    // Campos del DTO de respuesta
    private Long id;
    private String nombre;
    private String codigo;
    private BigDecimal precio;
    private String marca;
    private String presentacion;
    private String notas;
    private LocalDate fechaVencimiento;
    private String categoriaNombre;

}
