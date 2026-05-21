package com.duocuc.ganaderia.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.duocuc.ganaderia.model.CategoriaProducto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CategoriaProductoService {
    private final CategoriaProductoRepository categoriaProductoRepository;
    public List<CategoriaProducto> obtenerTodas() {
        return categoriaProductoRepository.findAll();
    }
    public Optional<CategoriaProducto> obtenerPorId(Long id) {
        return categoriaProductoRepository.findById(id);
    }
    public Optional<CategoriaProducto> buscarPorNombre(String nombre) {
        return categoriaProductoRepository.buscarPorNombreIgualIgnoreCase(nombre);
    }
    public CategoriaProducto guardar(CategoriaProducto cat) {
        return categoriaProductoRepository.save(cat);
    }
    public void eliminar(Long id) {
        categoriaProductoRepository.deleteById(id);
    }












}
