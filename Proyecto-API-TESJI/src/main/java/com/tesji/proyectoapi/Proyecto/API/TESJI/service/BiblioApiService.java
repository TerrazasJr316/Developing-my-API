package com.tesji.proyectoapi.Proyecto.API.TESJI.service;

import com.tesji.proyectoapi.Proyecto.API.TESJI.model.DatosBiblioApi;

import java.util.ArrayList;
import java.util.Optional;

/*
Definir los metodos para las operaciones CRUD
 */
public interface BiblioApiService {

    ArrayList<DatosBiblioApi> mostrarTodosLosLibros();
    Optional<DatosBiblioApi> mostrarLibroPorID (long id);
    DatosBiblioApi registrarLibro(DatosBiblioApi libro); //agregar y actualizar
    boolean eliminarLibro (long id);
}
