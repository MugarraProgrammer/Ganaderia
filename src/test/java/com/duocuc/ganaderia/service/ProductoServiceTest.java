package com.duocuc.ganaderia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.duocuc.ganaderia.dto.ProductoRequestDTO;
import com.duocuc.ganaderia.dto.ProductoResponseDTO;
import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.repository.CategoriaProductoRepository;
import com.duocuc.ganaderia.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;



@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaProductoRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerTodosDevuelveListaDeDtos() {
        CategoriaProducto categoria = new CategoriaProducto(1L, "Vacuno", "Cortes");
        Producto producto = new Producto(
            10L,
            "Lomo Vetado",
            "CR-001",
            new BigDecimal("14500"),
            "Marca",
            "Bandeja",
            "Notas",
            LocalDate.of(2026, 6, 10),
            categoria
        );

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoResponseDTO> resultado = productoService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Lomo Vetado", resultado.get(0).getNombre());
        assertEquals("Vacuno", resultado.get(0).getCategoriaNombre());
        verify(productoRepository).findAll();
    }

     @Test
    void obtenerPorIdDevuelveDtoSiExiste() {
        CategoriaProducto categoria = new CategoriaProducto(1L, "Vacuno", "Cortes");
        Producto producto = new Producto(
            10L,
            "Lomo Vetado",
            "CR-001",
            new BigDecimal("14500"),
            "Marca",
            "Bandeja",
            "Notas",
            LocalDate.of(2026, 6, 10),
            categoria
        );

        when(productoRepository.findById(10L)).thenReturn(Optional.of(producto));

        Optional<ProductoResponseDTO> resultado = productoService.obtenerPorId(10L);

        assertTrue(resultado.isPresent());
        assertEquals(10L, resultado.get().getId());
        assertEquals("CR-001", resultado.get().getCodigo());
    }

    @Test
    void guardarCreaProductoConCategoriaExistente() {
        CategoriaProducto categoria = new CategoriaProducto(1L, "Vacuno", "Cortes");

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Lomo Vetado");
        dto.setCodigo("CR-001");
        dto.setPrecio(new BigDecimal("14500"));
        dto.setMarca("Marca");
        dto.setPresentacion("Bandeja");
        dto.setNotas("Notas");
        dto.setFechaVencimiento(LocalDate.of(2026, 6, 10));
        dto.setCategoriaId(1L);

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto producto = invocation.getArgument(0);
            producto.setId(99L);
            return producto;
        });
          ProductoResponseDTO resultado = productoService.guardar(dto);

        assertEquals(99L, resultado.getId());
        assertEquals("Lomo Vetado", resultado.getNombre());
        assertEquals("Vacuno", resultado.getCategoriaNombre());
        verify(categoriaRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void actualizarLanza404SiNoExisteProducto() {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Lomo Vetado");
        dto.setCodigo("CR-001");
        dto.setPrecio(new BigDecimal("14500"));
        dto.setCategoriaId(1L);

        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> productoService.actualizar(1L, dto)
        );

        assertEquals(404, ex.getStatusCode().value());
        verify(productoRepository).findById(1L);
    }
}
