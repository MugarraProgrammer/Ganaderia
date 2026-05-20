package com.duocuc.ganaderia.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.*;


@Data 
@NoArgsConstructor
@AllArgsConstructor

public class ProductoRequestDTO {

    // Campos del DTO de solicitud
    @notblank private String nombre;
    @notblank private String codigo;
    @notnull @Positive @Digits(integer = 10, fraction = 0) private BigDecimal precio;
    private String marca;
    private String presentacion;
    private String notas;
    @notnull private LocalDate fechaVencimiento;
    @notnull private Long categoriaId;


}
