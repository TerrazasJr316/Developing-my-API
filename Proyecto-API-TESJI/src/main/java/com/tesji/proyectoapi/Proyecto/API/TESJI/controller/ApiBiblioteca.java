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
    @RequestMapping("biblioapi")//Define la URL de la API
    public class ApiBiblioteca {

        //Inyectar objeto de servicio para utilizar las operaciones CRUD
        @Autowired
        BiblioApiService biblioApiService;

        @GetMapping("/get-prueba")
        public String pruebaApi(){
            return "Funciona!! Hola Mundo, mi primera API";
        }

        @GetMapping("/ver-todoslibros")
        public ArrayList<DatosBiblioApi>getVerTodosLibros(){
            return biblioApiService.mostrarTodoLibros();
        }

        @GetMapping("/ver-libro/{idLibro}")
        public Optional getVerLibro(@PathVariable("idLibro") long id){
            return biblioApiService.mostrarLibroPor(id);
        }

        @PostMapping("/guardar-libro")
        public DatosBiblioApi postGuardarLibro(@RequestBody DatosBiblioApi book){
            return biblioApiService.registrarLibro(book);
        }

        @DeleteMapping("/eliminar-libro/{idLibro}")
        public boolean deleteBorrarLibro(@PathVariable("idLibro") long id){
            return biblioApiService.eliminarLibro(id);
        }

        @PutMapping("/modificar-libro/{idLibro}")  // Corregimos la anotación y el nombre del parámetro
        public DatosBiblioApi putModificarLibro(@PathVariable("idLibro") long idLibro, @RequestBody DatosBiblioApi libro) {
            libro.setId(idLibro);
            return biblioApiService.modificarLibro(libro);
        }

    }
