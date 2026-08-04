package com.media.backend.repository;

import com.media.backend.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    
    // Métodos de consulta personalizados
    List<Media> findByTipo(String tipo);
    
    List<Media> findByVisto(Boolean visto);
    
    List<Media> findByAnio(Integer anio);
    
    List<Media> findByCalificacionGreaterThanEqual(Double calificacion);
}
