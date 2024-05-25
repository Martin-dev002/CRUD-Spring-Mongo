package practica.spring.crudmongo.entidades;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Producto")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private BigDecimal precio;
    private String categoriaId;
}
