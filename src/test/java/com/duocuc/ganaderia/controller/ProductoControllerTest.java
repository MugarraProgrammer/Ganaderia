package com.duocuc.ganaderia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.duocuc.ganaderia.dto.ProductoResponseDTO;
import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

@Mock
private ProductoService productoService;

@InjectMocks
private ProductoController productoController;

private MockMvc mockMvc;
private ProductoResponseDTO responseDTO;

@BeforeEach
void setUp() {
mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();

responseDTO = new ProductoResponseDTO(
1L,
"Lomo Vetado",
"CR-001",
new BigDecimal("14500"),
"Marca",
"Bandeja",
"Notas",
LocalDate.of(2026, 6, 10),
"Vacuno"
);
}

@Test
void obtenerTodosDevuelve200() throws Exception {
when(productoService.obtenerTodos()).thenReturn(List.of(responseDTO));

mockMvc.perform(get("/api/productos"))
.andExpect(status().isOk())
.andExpect(jsonPath("$[0].nombre").value("Lomo Vetado"))
.andExpect(jsonPath("$[0].categoriaNombre").value("Vacuno"));
}

@Test
void obtenerPorIdDevuelve200SiExiste() throws Exception {
when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));

mockMvc.perform(get("/api/productos/1"))
.andExpect(status().isOk())
.andExpect(jsonPath("$.codigo").value("CR-001"));
}

@Test
void crearDevuelve201() throws Exception {
when(productoService.guardar(any())).thenReturn(responseDTO);

mockMvc.perform(post("/api/productos")
.contentType(MediaType.APPLICATION_JSON)
.content("""
{
"nombre": "Lomo Vetado",
"codigo": "CR-001",
"precio": 14500,
"marca": "Marca",
"presentacion": "Bandeja",
"notas": "Notas",
"fechaVencimiento": "2026-06-10",
"categoriaId": 1
}
"""))
.andExpect(status().isCreated())
.andExpect(jsonPath("$.nombre").value("Lomo Vetado"));
}

@Test
void actualizarDevuelve200() throws Exception {
when(productoService.actualizar(eq(1L), any())).thenReturn(responseDTO);

mockMvc.perform(put("/api/productos/1")
.contentType(MediaType.APPLICATION_JSON)
.content("""
{
"nombre": "Lomo Vetado",
"codigo": "CR-001",
"precio": 14500,
"marca": "Marca",
"presentacion": "Bandeja",
"notas": "Notas",
"fechaVencimiento": "2026-06-10",
"categoriaId": 1
}
"""))
.andExpect(status().isOk())
.andExpect(jsonPath("$.categoriaNombre").value("Vacuno"));
}

@Test
void eliminarDevuelve204CuandoExiste() throws Exception {
when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(responseDTO));
doNothing().when(productoService).eliminar(1L);

mockMvc.perform(delete("/api/productos/1"))
.andExpect(status().isNoContent());

verify(productoService).eliminar(1L);
}

@Test
void buscarPorNombreDevuelve200() throws Exception {
Producto producto = new Producto(
1L,
"Lomo Vetado",
"CR-001",
new BigDecimal("14500"),
"Marca",
"Bandeja",
"Notas",
LocalDate.of(2026, 6, 10),
new CategoriaProducto(1L, "Vacuno", "Cortes")
);

when(productoService.buscarPorNombre("Lomo")).thenReturn(List.of(producto));

mockMvc.perform(get("/api/productos/buscar").param("nombre", "Lomo"))
.andExpect(status().isOk())
.andExpect(jsonPath("$[0].nombre").value("Lomo Vetado"));
}
}
