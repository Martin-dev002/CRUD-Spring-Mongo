package practica.spring.crudmongo.repositorios;

import practica.spring.crudmongo.entidades.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends MongoRepository<Producto,String> {
   // List<Producto> findAllByCategoriaId(String categoriaId);
}
