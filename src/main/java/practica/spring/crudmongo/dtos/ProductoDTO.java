package practica.spring.crudmongo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoDTO {
    private String id;
    private String nombre;
    private BigDecimal precio;
    private String categoriaId;
}