package practica.spring.crudmongo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoConCategoriaDTO {
    private ProductoDTO producto;
    private CategoriaDTO categoria;
}