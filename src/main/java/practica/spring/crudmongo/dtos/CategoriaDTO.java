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
public class CategoriaDTO {
    private String id;
    private String nombre;
    private List<String> productosId;
}
