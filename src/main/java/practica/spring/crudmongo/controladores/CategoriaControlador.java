package practica.spring.crudmongo.controladores;

import practica.spring.crudmongo.dtos.CategoriaConProductosDTO;
import practica.spring.crudmongo.dtos.CategoriaDTO;
import practica.spring.crudmongo.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/categorias")
@RestController
public class CategoriaControlador {
    @Autowired
    private CategoriaServicio categoriaServicio;

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoriaDTO = categoriaServicio.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(nuevaCategoriaDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable String id) {
        CategoriaDTO categoriaDTO = categoriaServicio.obtenerCategoriaPorId(id);
        return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable String id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaActualizadaDTO = categoriaServicio.actualizarCategoria(id, categoriaDTO);
        return new ResponseEntity<>(categoriaActualizadaDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable String id) {
        categoriaServicio.eliminarCategoria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        List<CategoriaDTO> categorias = categoriaServicio.obtenerTodasLasCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
    @GetMapping("/conProductos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasCategoriasConProductos() {
        List<Map<String, Object>> categorias = categoriaServicio.obtenerTodasLasCategoriasConProductos();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
    @GetMapping("/conProductosDto")
    public ResponseEntity<List<CategoriaConProductosDTO>> obtenerTodasLasCategoriasConProductosDto() {
        List<CategoriaConProductosDTO> categorias = categoriaServicio.obtenerTodasLasCategoriasConProductosDto();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}
