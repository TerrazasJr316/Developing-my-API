package com.example.brainylib;
public class DatosBiblioApi {
    private long id;
    private String titulo;
    private String autores;
    private String editorial;
    private String edicion;
    // Getters y setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutores() {
        return autores;
    }
    public void setAutores(String autores) {
        this.autores = autores;
    }
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public String getEdicion() {
        return edicion;
    }
    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
}
