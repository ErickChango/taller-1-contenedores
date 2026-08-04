package com.media.backend.service;

import com.media.backend.model.Media;
import com.media.backend.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    
    @Autowired
    private MediaRepository mediaRepository;
    
    public List<Media> obtenerTodosLosMedia() {
        return mediaRepository.findAll();
    }
    
    public Optional<Media> obtenerMediaPorId(Long id) {
        return mediaRepository.findById(id);
    }
    
    public Media crearMedia(Media media) {
        return mediaRepository.save(media);
    }
    
    public Media actualizarMedia(Long id, Media mediaActualizado) {
        return mediaRepository.findById(id)
                .map(media -> {
                    media.setTitulo(mediaActualizado.getTitulo());
                    media.setTipo(mediaActualizado.getTipo());
                    media.setAnio(mediaActualizado.getAnio());
                    media.setCalificacion(mediaActualizado.getCalificacion());
                    media.setVisto(mediaActualizado.getVisto());
                    return mediaRepository.save(media);
                })
                .orElseThrow(() -> new RuntimeException("Media no encontrado con id: " + id));
    }
    
    public void eliminarMedia(Long id) {
        mediaRepository.deleteById(id);
    }
    
    public List<Media> obtenerPorTipo(String tipo) {
        return mediaRepository.findByTipo(tipo);
    }
    
    public List<Media> obtenerPorEstadoVisto(Boolean visto) {
        return mediaRepository.findByVisto(visto);
    }
}
