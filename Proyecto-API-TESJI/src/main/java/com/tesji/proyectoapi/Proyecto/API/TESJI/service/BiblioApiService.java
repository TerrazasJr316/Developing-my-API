package com.tesji.proyectoapi.Proyecto.API.TESJI.service;

import com.tesji.proyectoapi.Proyecto.API.TESJI.model.DatosBiblioApi;

import java.util.ArrayList;
import java.util.Optional;

//Definir los metodos abstractos para las operaciones CRUD
public interface BiblioApiService {
    ArrayList<DatosBiblioApi> mostrarTodoLibros();
    Optional<DatosBiblioApi> mostrarLibroPor(long id);
    DatosBiblioApi registrarLibro(DatosBiblioApi libro); //agregar y atualizar
    DatosBiblioApi modificarLibro(DatosBiblioApi libro);
    boolean eliminarLibro(long id);
}
