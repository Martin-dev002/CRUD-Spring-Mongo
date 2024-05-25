package practica.spring.crudmongo.utilidades;

import practica.spring.crudmongo.dtos.CategoriaDTO;
import practica.spring.crudmongo.entidades.Categoria;
public class CategoriaConversor {
    public static CategoriaDTO convertirAcategoriaDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setProductosId(categoria.getProductosId());
        return categoriaDTO;
    }
    public static Categoria convertirAcategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setProductosId(categoriaDTO.getProductosId());
        return categoria;
    }
}
