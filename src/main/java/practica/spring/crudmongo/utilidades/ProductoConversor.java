package practica.spring.crudmongo.utilidades;

import practica.spring.crudmongo.dtos.ProductoDTO;
import practica.spring.crudmongo.entidades.Producto;
public class ProductoConversor {
    public static ProductoDTO convertirAproductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCategoriaId(producto.getCategoriaId());
        return productoDTO;
    }
    public static Producto convertirAproducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setCategoriaId(productoDTO.getCategoriaId());
        return producto;
    }
}
