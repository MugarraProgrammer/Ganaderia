package com.duocuc.ganaderia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.service.CategoriaProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CategoriaProductoControllerTest {

    @Mock
    private CategoriaProductoService categoriaProductoService;

    @InjectMocks
    private CategoriaProductoController categoriaProductoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaProductoController).build();
    }

    @Test
    void obtenerTodasDevuelve200() throws Exception {
        when(categoriaProductoService.obtenerTodas())
            .thenReturn(List.of(new CategoriaProducto(1L, "Vacuno", "Cortes")));

        mockMvc.perform(get("/api/categorias"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Vacuno"));
    }

    @Test
    void obtenerPorIdDevuelve200SiExiste() throws Exception {
        when(categoriaProductoService.obtenerPorId(1L))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes")));

        mockMvc.perform(get("/api/categorias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Vacuno"));
    }

    @Test
    void obtenerPorIdDevuelve404SiNoExiste() throws Exception {
        when(categoriaProductoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categorias/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorNombreDevuelve200() throws Exception {
        when(categoriaProductoService.buscarPorNombre("Vacuno"))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes")));

        mockMvc.perform(get("/api/categorias/buscar").param("nombre", "Vacuno"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Vacuno"));
    }

    @Test
    void crearDevuelve201() throws Exception {
        when(categoriaProductoService.guardar(any())).thenReturn(
            new CategoriaProducto(1L, "Vacuno", "Cortes")
        );

        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "nombre": "Vacuno",
                    "descripcion": "Cortes"
                }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Vacuno"));
    }

    @Test
    void actualizarDevuelve200() throws Exception {
        when(categoriaProductoService.obtenerPorId(1L))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes")));
        when(categoriaProductoService.guardar(any())).thenReturn(
            new CategoriaProducto(1L, "Vacuno", "Cortes actualizados")
        );

        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "nombre": "Vacuno",
                    "descripcion": "Cortes actualizados"
                }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.descripcion").value("Cortes actualizados"));
    }

    @Test
    void actualizarDevuelve404SiNoExiste() throws Exception {
        when(categoriaProductoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "nombre": "Vacuno",
                    "descripcion": "Cortes"
                }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarDevuelve204() throws Exception {
        when(categoriaProductoService.obtenerPorId(1L))
            .thenReturn(Optional.of(new CategoriaProducto(1L, "Vacuno", "Cortes")));
        doNothing().when(categoriaProductoService).eliminar(1L);

        mockMvc.perform(delete("/api/categorias/1"))
            .andExpect(status().isNoContent());

        verify(categoriaProductoService).eliminar(1L);
    }
}
