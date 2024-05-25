package practica.spring.crudmongo.entidades;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Categoria")
public class Categoria {
    @Id
    private String id;
    private String nombre;
    private List<String> productosId;
}