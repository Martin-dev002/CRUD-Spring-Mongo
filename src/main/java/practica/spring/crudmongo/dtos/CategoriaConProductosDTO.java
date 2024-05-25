package practica.spring.crudmongo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoriaConProductosDTO {
    private CategoriaDTO categoria;
    private List<ProductoDTO> productos;
}