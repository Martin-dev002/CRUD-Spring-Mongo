package practica.spring.crudmongo.servicios;

import practica.spring.crudmongo.dtos.ProductoConCategoriaDTO;
import practica.spring.crudmongo.dtos.ProductoDTO;

import java.util.List;
import java.util.Map;

public interface ProductoServicio {
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO obtenerProductoPorId(String id);
    ProductoDTO actualizarProducto(String id, ProductoDTO productoDTO);
    List<ProductoDTO> obtenerTodosLosProductos();
    void eliminarProducto(String id);
    List<Map<String, Object>> obtenerTodosLosProductosConCategoria();
    List<ProductoConCategoriaDTO> obtenerTodosLosProductosConCategoriaDto();
}
