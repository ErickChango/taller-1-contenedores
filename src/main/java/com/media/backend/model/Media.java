package com.media.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "media")
public class Media {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false)
    private String tipo; // "película" o "serie"
    
    @Min(value = 1888, message = "El año debe ser válido")
    @Max(value = 2100, message = "El año debe ser válido")
    @Column(nullable = false)
    private Integer anio;
    
    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 10, message = "La calificación máxima es 10")
    private Double calificacion;
    
    @Column(nullable = false)
    private Boolean visto = false;
    
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructor vacío requerido por JPA
    public Media() {
    }
    
    // Constructor con parámetros
    public Media(String titulo, String tipo, Integer anio, Double calificacion, Boolean visto) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.anio = anio;
        this.calificacion = calificacion;
        this.visto = visto;
    }
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public Integer getAnio() {
        return anio;
    }
    
    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    
    public Double getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public Boolean getVisto() {
        return visto;
    }
    
    public void setVisto(Boolean visto) {
        this.visto = visto;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", anio=" + anio +
                ", calificacion=" + calificacion +
                ", visto=" + visto +
                '}';
    }
}
