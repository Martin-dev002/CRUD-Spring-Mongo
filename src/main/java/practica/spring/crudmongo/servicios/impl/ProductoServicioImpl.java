package practica.spring.crudmongo.servicios.impl;

import practica.spring.crudmongo.entidades.Categoria;
import practica.spring.crudmongo.entidades.Producto;
import practica.spring.crudmongo.repositorios.CategoriaRepositorio;
import practica.spring.crudmongo.repositorios.ProductoRepositorio;
import practica.spring.crudmongo.dtos.CategoriaDTO;
import practica.spring.crudmongo.dtos.ProductoConCategoriaDTO;
import practica.spring.crudmongo.dtos.ProductoDTO;
import practica.spring.crudmongo.excepciones.CreacionFallida;
import practica.spring.crudmongo.excepciones.RecursoNoEncontrado;
import practica.spring.crudmongo.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practica.spring.crudmongo.utilidades.ProductoConversor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        if (productoDTO.getNombre() == null || productoDTO.getPrecio() == null || productoDTO.getPrecio().compareTo(BigDecimal.ZERO) < 0
                || productoDTO.getNombre().trim().isBlank() || productoDTO.getCategoriaId().trim().isBlank()) {
            throw new CreacionFallida("Algun campo es incorrecto, o vacio");
        }
        Categoria categoria = categoriaRepositorio.findById(productoDTO.getCategoriaId().trim()).
                orElseThrow(()-> new RecursoNoEncontrado("Categoria no erronea o encontrada"));

        Producto producto = ProductoConversor.convertirAproducto(productoDTO);
        producto = productoRepositorio.save(producto);
        // Obtiene la categoría correspondiente y añade el ID del producto a la lista de IDs de productos
        if (categoria.getProductosId() == null) {
            categoria.setProductosId(new ArrayList<>());
        }
            categoria.getProductosId().add(producto.getId());
            categoriaRepositorio.save(categoria);
        return ProductoConversor.convertirAproductoDTO(producto);
    }
    @Override
    public ProductoDTO obtenerProductoPorId(String id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("El producto con el id: " + id + " No existe"));
        return ProductoConversor.convertirAproductoDTO(producto);
    }
    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepositorio.findAll();
        return productos.stream()
                .map(ProductoConversor::convertirAproductoDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ProductoDTO actualizarProducto(String id, ProductoDTO productoDTO) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontrado("El producto con el id: " + id + " No existe"));

        if (productoDTO.getNombre() == null || productoDTO.getPrecio() == null || productoDTO.getNombre().trim().isBlank()) {
            throw new CreacionFallida("Algun campo es incorrecto, o vacio");
        }

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoriaId(productoDTO.getCategoriaId());
        producto = productoRepositorio.save(producto);

        return ProductoConversor.convertirAproductoDTO(producto);
    }
    @Override
    public void eliminarProducto(String id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(()-> new RecursoNoEncontrado("El producto con el id: " + id + " No existe"));
        Categoria categoria = categoriaRepositorio.findById(producto.getCategoriaId())
                .orElseThrow(()-> new RecursoNoEncontrado("La categoria con el id: " + id + " No existe"));

            categoria.getProductosId().remove(producto.getId());

            categoriaRepositorio.save(categoria);
            productoRepositorio.deleteById(id);
    }
    @Override
    public List<Map<String, Object>> obtenerTodosLosProductosConCategoria() {
        List<Producto> productos = productoRepositorio.findAll();

        if (productos.isEmpty()) {
            return Collections.emptyList();
        }

        return productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoriaId))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> categoriaMap = new HashMap<>();
                    categoriaMap.put("categoriaId", entry.getKey());
                    categoriaRepositorio.findById(entry.getKey()).ifPresent(categoria ->
                            categoriaMap.put("categoriaNombre", categoria.getNombre()));
                    categoriaMap.put("productos", entry.getValue().stream()
                            .map(producto -> {
                                Map<String, Object> productoMap = new HashMap<>();
                                productoMap.put("id", producto.getId());
                                productoMap.put("nombre", producto.getNombre());
                                productoMap.put("precio", producto.getPrecio());
                                return productoMap;
                            })
                            .collect(Collectors.toList()));
                    return categoriaMap;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductoConCategoriaDTO> obtenerTodosLosProductosConCategoriaDto() {
        List<Producto> productos = productoRepositorio.findAll();

        if (productos.isEmpty()) {
            return Collections.emptyList();
        }

        return productos.stream()
                .map(producto -> {
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setCategoriaId(producto.getCategoriaId());

                    // Buscar la categoría por su ID
                    CategoriaDTO categoriaDTO = categoriaRepositorio.findById(producto.getCategoriaId())
                            .map(categoria -> {
                                CategoriaDTO dto = new CategoriaDTO();
                                dto.setId(categoria.getId());
                                dto.setNombre(categoria.getNombre());
                                dto.setProductosId(categoria.getProductosId());

                                return dto;
                            })
                            .orElse(null);

                    ProductoConCategoriaDTO productoConCategoriaDTO = new ProductoConCategoriaDTO();
                    productoConCategoriaDTO.setProducto(productoDTO);
                    productoConCategoriaDTO.setCategoria(categoriaDTO);

                    return productoConCategoriaDTO;
                })
                .collect(Collectors.toList());
    }
}
