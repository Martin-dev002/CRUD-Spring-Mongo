package practica.spring.crudmongo.servicios;

import practica.spring.crudmongo.dtos.CategoriaConProductosDTO;
import practica.spring.crudmongo.dtos.CategoriaDTO;

import java.util.List;
import java.util.Map;

public interface CategoriaServicio {
    CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO obtenerCategoriaPorId(String id);
    CategoriaDTO actualizarCategoria(String id, CategoriaDTO categoriaDTO);
    List<CategoriaDTO> obtenerTodasLasCategorias();
    void eliminarCategoria(String id);
    List<CategoriaConProductosDTO> obtenerTodasLasCategoriasConProductosDto();
    List<Map<String, Object>> obtenerTodasLasCategoriasConProductos();
}
