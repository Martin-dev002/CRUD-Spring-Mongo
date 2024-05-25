package practica.spring.crudmongo.servicios.impl;

import practica.spring.crudmongo.entidades.Categoria;
import practica.spring.crudmongo.entidades.Producto;
import practica.spring.crudmongo.excepciones.CreacionFallida;
import practica.spring.crudmongo.repositorios.CategoriaRepositorio;
import practica.spring.crudmongo.repositorios.ProductoRepositorio;
import practica.spring.crudmongo.dtos.CategoriaConProductosDTO;
import practica.spring.crudmongo.dtos.CategoriaDTO;
import practica.spring.crudmongo.dtos.ProductoDTO;
import practica.spring.crudmongo.excepciones.RecursoNoEncontrado;
import practica.spring.crudmongo.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practica.spring.crudmongo.utilidades.CategoriaConversor;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        if (categoriaDTO.getNombre() == null || categoriaDTO.getNombre().trim().isBlank()) {
            throw  new CreacionFallida("Algun campo es incorrecto, o vacio");
        }
        Categoria categoria = CategoriaConversor.convertirAcategoria(categoriaDTO);
        categoria = categoriaRepositorio.save(categoria);
        return CategoriaConversor.convertirAcategoriaDTO(categoria);
    }
    @Override
    public CategoriaDTO obtenerCategoriaPorId(String id) {
        Categoria categoria = categoriaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("La categoria con el id " + id + " no existe"));
        CategoriaDTO categoriaDTO = CategoriaConversor.convertirAcategoriaDTO(categoria);
        categoriaDTO.setProductosId(categoria.getProductosId());
        return categoriaDTO;
    }
    @Override
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepositorio.findAll();
        return categorias.stream()
                .map(CategoriaConversor::convertirAcategoriaDTO)
                .collect(Collectors.toList());
    }
    @Override
    public CategoriaDTO actualizarCategoria(String id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("La categoria con el id " + id + " no existe"));

        if (categoriaDTO.getNombre().trim().isBlank()) {
            throw new CreacionFallida("Algun campo es incorrecto, o vacio");
        }

        categoria.setNombre(categoriaDTO.getNombre());
        categoria = categoriaRepositorio.save(categoria);

        return CategoriaConversor.convertirAcategoriaDTO(categoria);
    }
    @Override
    public void eliminarCategoria(String id) {
        Categoria categoria = categoriaRepositorio.findById(id).orElseThrow(()->
                new RecursoNoEncontrado("La categoria con el id " + id + " no existe"));
        List<Producto> productos = productoRepositorio.findAllById(categoria.getProductosId());
        productoRepositorio.deleteAll(productos);
        categoriaRepositorio.deleteById(id);
    }
    @Override
    public List<Map<String, Object>> obtenerTodasLasCategoriasConProductos() {
        List<Categoria> categorias = categoriaRepositorio.findAll();

        if (categorias.isEmpty()) {
            return Collections.emptyList();
        }
        return categorias.stream()
                .map(categoria -> {
                    Map<String, Object> categoriaMap = new HashMap<>();
                    categoriaMap.put("id", categoria.getId());
                    categoriaMap.put("nombre", categoria.getNombre());

                    // Buscar cada producto por su ID y agregarlo al mapa de la categoría
                    List<Map<String, Object>> productos = categoria.getProductosId().stream()
                            .map(id -> productoRepositorio.findById(id).orElse(null))
                            .filter(Objects::nonNull)
                            .map(producto -> {
                                Map<String, Object> productoMap = new HashMap<>();
                                productoMap.put("id", producto.getId());
                                productoMap.put("nombre", producto.getNombre());
                                productoMap.put("precio", producto.getPrecio());

                                return productoMap;
                            })
                            .collect(Collectors.toList());

                    categoriaMap.put("productos", productos);

                    return categoriaMap;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<CategoriaConProductosDTO> obtenerTodasLasCategoriasConProductosDto() {
        List<Categoria> categorias = categoriaRepositorio.findAll();

        if (categorias.isEmpty()) {
            return Collections.emptyList();
        }

        return categorias.stream()
                .map(categoria -> {
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    categoriaDTO.setId(categoria.getId());
                    categoriaDTO.setNombre(categoria.getNombre());
                    categoriaDTO.setProductosId(categoria.getProductosId());

                    // Buscar cada producto por su ID y agregarlo a la lista de productos
                    List<ProductoDTO> productos = categoria.getProductosId().stream()
                            .map(id -> productoRepositorio.findById(id).orElse(null))
                            .filter(Objects::nonNull)
                            .map(producto -> {
                                ProductoDTO productoDTO = new ProductoDTO();
                                productoDTO.setId(producto.getId());
                                productoDTO.setNombre(producto.getNombre());
                                productoDTO.setPrecio(producto.getPrecio());
                                productoDTO.setCategoriaId(producto.getCategoriaId());

                                return productoDTO;
                            })
                            .collect(Collectors.toList());

                    CategoriaConProductosDTO categoriaConProductosDTO = new CategoriaConProductosDTO();
                    categoriaConProductosDTO.setCategoria(categoriaDTO);
                    categoriaConProductosDTO.setProductos(productos);

                    return categoriaConProductosDTO;
                })
                .collect(Collectors.toList());
    }

}

