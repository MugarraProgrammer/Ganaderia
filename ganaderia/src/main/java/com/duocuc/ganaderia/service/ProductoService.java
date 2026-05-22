package com.duocuc.ganaderia.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.duocuc.ganaderia.dto.ProductoRequestDTO;
import com.duocuc.ganaderia.dto.ProductoResponseDTO;
import com.duocuc.ganaderia.model.CategoriaProducto;
import com.duocuc.ganaderia.model.Producto;
import com.duocuc.ganaderia.repository.CategoriaProductoRepository;
import com.duocuc.ganaderia.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaRepository;

    private ProductoResponseDTO  mapToDto(Producto p) {
        return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getCodigo(), p.getPrecio(), p.getMarca(), p.getPresentacion(), p.getNotas(), p.getFechaVencimiento(), p.getCategoria().getNombre());
    }
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }
    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(this::mapToDto);
    }
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        CategoriaProducto cat = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe"));
        Producto p = new Producto(null, dto.getNombre(), dto.getCodigo(), dto.getPrecio(), dto.getMarca(), dto.getPresentacion(), dto.getNotas(), dto.getFechaVencimiento(), cat);
        return mapToDto(productoRepository.save(p));
    }
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto e = productoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe"));
        CategoriaProducto cat = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe"));
        e.setNombre(dto.getNombre()); e.setCodigo(dto.getCodigo());
        e.setPrecio(dto.getPrecio()); e.setMarca(dto.getMarca());
        e.setPresentacion(dto.getPresentacion()); e.setNotas(dto.getNotas());
        e.setFechaVencimiento(dto.getFechaVencimiento()); e.setCategoria(cat);
        return mapToDto(productoRepository.save(e)); 
    }

    public List<Producto> buscarPorNombre(String texto){
        return productoRepository.buscarPorNombreContieneIgnoreCase(texto);
    }
    public List<Producto> buscarPorNombreQueEmpieceCon (String prefijo){
        return productoRepository.buscarPorNmbreEmpiezaConIgnoreCase(prefijo);
    }
    public List<Producto> buscarPorNombreQueTermineCon( String sufijo){
        return productoRepository.buscarPorNombreTerminaConIgnoreCase(sufijo);
    }

}
