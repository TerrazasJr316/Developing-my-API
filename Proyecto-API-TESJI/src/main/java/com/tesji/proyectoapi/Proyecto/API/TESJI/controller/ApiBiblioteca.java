package com.tesji.proyectoapi.Proyecto.API.TESJI.controller;
//Esta clase controlador va a permitir
//gestionar las peticiones http (get, post, put, delete)
//y exponer p deployar la API

import com.tesji.proyectoapi.Proyecto.API.TESJI.model.DatosBiblioApi;
import com.tesji.proyectoapi.Proyecto.API.TESJI.service.BiblioApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController //Expone la clase como una API REST
@RequestMapping("biblioAPI")//Define la URL de la API
public class ApiBiblioteca {

    /*
    Inyectar objeto de servicio para utilizar las operaciones CRUD
     */
    @Autowired
    BiblioApiService biblioApiService;

    @GetMapping("/get-prueba")
    public String pruebaApi(){
        return "Funciona!! Hola Mundo, mi primera API";
    }

    @GetMapping("/ver-todoslibros")
    public ArrayList<DatosBiblioApi> getVerTodosLosLibros(){
        return biblioApiService.mostrarTodosLosLibros();
    }

    @GetMapping("/ver-libro/{idLibro}")
    public Optional getVerLibro (@PathVariable("idLibro") long id){
        return biblioApiService.mostrarLibroPorID(id);
    }

    @PostMapping("/guardar-libro")
    public DatosBiblioApi postGuardarLibro (@RequestBody DatosBiblioApi book) {
        return biblioApiService.registrarLibro(book);
    }

    @DeleteMapping("/eliminar-libro/{id-libro}")
    public boolean deleteLibro (long id) {
        return biblioApiService.eliminarLibro(id);
    }
}