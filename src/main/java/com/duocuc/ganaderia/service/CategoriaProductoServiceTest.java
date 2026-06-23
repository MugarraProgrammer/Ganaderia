
package com.duocuc.ganaderia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.Mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.repository.CategoriaProductoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoriaProductoServiceTest {

    @Mock
    private CategoriaProductoRepository categoriaProductoRepository;

    @InjectMocks
    private CategoriaProductoService categoriaProductoService;

    @Test
    void obtenerTodasDevuelveLista() {
        when(categoriaProductoRepository.findAll())
            .thenReturn(List.of(new CategoriaProducto(1L, "Vacuno", "Cortes de vacuno")));

        var resultado = categoriaProductoService.obtenerTodas();

        assertEquals(1, resultado.size());
        assertEquals("Vacuno", resultado.get(0).getNombre());
    }

    @Test
    void obtenerPorIdDevuelveCategoriaSiExiste() {
        when(categoriaProductoRepository.findById(1L))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes de vacuno")));

        var resultado = categoriaProductoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Vacuno", resultado.get().getNombre());
    }

    @Test
    void obtenerPorIdDevuelveVacioSiNoExiste() {
        when(categoriaProductoRepository.findById(99L)).thenReturn(Optional.empty());

        var resultado = categoriaProductoService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorNombreDevuelveCategoria() {
        when(categoriaProductoRepository.buscarPorNombreIgualIgnoreCase("Vacuno"))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes de vacuno")));

        var resultado = categoriaProductoService.buscarPorNombre("Vacuno");

        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorNombreDevuelveVacioSiNoCoincide() {
        when(categoriaProductoRepository.buscarPorNombreIgualIgnoreCase("Inexistente"))
            .thenReturn(Optional.empty());

        var resultado = categoriaProductoService.buscarPorNombre("Inexistente");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void guardarCategoriaExitosamente() {
        CategoriaProducto nueva = new CategoriaProducto(null, "Porcino", "Cortes de cerdo");
        CategoriaProducto guardada = new CategoriaProducto(2L, "Porcino", "Cortes de cerdo");
        
        when(categoriaProductoRepository.save(nueva)).thenReturn(guardada);

        var resultado = categoriaProductoService.guardar(nueva);

        assertNotNull(resultado.getId());
        assertEquals("Porcino", resultado.getNombre());
    }

    @Test
    void eliminarCategoriaLlamaAlRepository() {
        doNothing().when(categoriaProductoRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoriaProductoService.eliminar(1L));
        verify(categoriaProductoRepository, times(1)).deleteById(1L);
    }
}