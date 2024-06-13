package com.tesji.proyectoapi.Proyecto.API.TESJI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity //Convierte la clase en una tabla con los atributos definidos
public class DatosBiblioApi {

    @Id //El atributo id lo convierte a una llave primaria
    @GeneratedValue (strategy = GenerationType.IDENTITY) //Identificador autoincrementable
    private long id;
    private String titulo;
    private String autores;
    private String editorial;
    private String edicion;

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public DatosBiblioApi(String titulo, String autores, String editorial, String edicion) {
        this.titulo = titulo;
        this.autores = autores;
        this.editorial = editorial;
        this.edicion = edicion;
    }
}