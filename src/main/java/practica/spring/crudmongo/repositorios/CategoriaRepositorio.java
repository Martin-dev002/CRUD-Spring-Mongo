package practica.spring.crudmongo.repositorios;
import practica.spring.crudmongo.entidades.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends MongoRepository<Categoria, String> {

}
