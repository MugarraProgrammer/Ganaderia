package com.duocuc.ganaderia.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto_carnes")

public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String nombre;
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;
    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal precio;
    @Column(length = 80)
    private String marca;
    @Column(length = 80)
    private String presentacion;
    @Column(length = 255)
    private String notas;
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    @ManyToOne(optional = false) @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaProducto categoria;
    
}
