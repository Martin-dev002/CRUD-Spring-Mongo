package practica.spring.crudmongo.controladores;

import lombok.AllArgsConstructor;
import practica.spring.crudmongo.dtos.ProductoConCategoriaDTO;
import practica.spring.crudmongo.dtos.ProductoDTO;
import practica.spring.crudmongo.servicios.ProductoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RequestMapping("/productos")
@RestController
public class ProductoControlador {
    private ProductoServicio productoServicio;

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProductoDTO = productoServicio.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProductoDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable String id) {
        ProductoDTO productoDTO = productoServicio.obtenerProductoPorId(id);
            return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable String id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO productoActualizadoDTO = productoServicio.actualizarProducto(id, productoDTO);
        return new ResponseEntity<>(productoActualizadoDTO, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id) {
        productoServicio.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodosLosProductos() {
        List<ProductoDTO> productos = productoServicio.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    @GetMapping("/conCategoria")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosProductosConCategoria() {
        List<Map<String, Object>> productos = productoServicio.obtenerTodosLosProductosConCategoria();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    @GetMapping("/conCategoriaDto")
    public ResponseEntity<List<ProductoConCategoriaDTO>> obtenerTodosLosProductosConCategoriaDto() {
        List<ProductoConCategoriaDTO> productos = productoServicio.obtenerTodosLosProductosConCategoriaDto();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
