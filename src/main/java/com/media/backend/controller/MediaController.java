package com.media.backend.controller;

import com.media.backend.model.Media;
import com.media.backend.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@CrossOrigin(origins = "*")
public class MediaController {
    
    @Autowired
    private MediaService mediaService;
    
    // GET /api/media - Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<Media>> obtenerTodosLosMedia() {
        List<Media> mediaList = mediaService.obtenerTodosLosMedia();
        return ResponseEntity.ok(mediaList);
    }
    
    // GET /api/media/{id} - Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Media> obtenerMediaPorId(@PathVariable Long id) {
        return mediaService.obtenerMediaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/media - Crear un nuevo registro
    @PostMapping
    public ResponseEntity<Media> crearMedia(@Valid @RequestBody Media media) {
        Media nuevoMedia = mediaService.crearMedia(media);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMedia);
    }
    
    // PUT /api/media/{id} - Actualizar un registro
    @PutMapping("/{id}")
    public ResponseEntity<Media> actualizarMedia(
            @PathVariable Long id,
            @Valid @RequestBody Media media) {
        try {
            Media mediaActualizado = mediaService.actualizarMedia(id, media);
            return ResponseEntity.ok(mediaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE /api/media/{id} - Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedia(@PathVariable Long id) {
        mediaService.eliminarMedia(id);
        return ResponseEntity.noContent().build();
    }
    
    // GET /api/media/tipo/{tipo} - Filtrar por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Media>> obtenerPorTipo(@PathVariable String tipo) {
        List<Media> mediaList = mediaService.obtenerPorTipo(tipo);
        return ResponseEntity.ok(mediaList);
    }
    
    // GET /api/media/visto/{visto} - Filtrar por estado visto
    @GetMapping("/visto/{visto}")
    public ResponseEntity<List<Media>> obtenerPorEstadoVisto(@PathVariable Boolean visto) {
        List<Media> mediaList = mediaService.obtenerPorEstadoVisto(visto);
        return ResponseEntity.ok(mediaList);
    }
}
