package com.tesji.proyectoapi.Proyecto.API.TESJI.service;

import com.tesji.proyectoapi.Proyecto.API.TESJI.model.DatosBiblioApi;
import com.tesji.proyectoapi.Proyecto.API.TESJI.repository.CrudBiblioApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

//Vamos a implementar y sobreescribir los metodos abstractos del Servicio (CRUD)
@Service
public class BiblioApiServiceImpl implements BiblioApiService{
    //Inyectar una Clase para ocuparla como objeto (no requiere instanciar)
    //el objeto heredó los metodos CRUD
    @Autowired
    CrudBiblioApiRepository crudBiblioApiRepository;

    @Override
    public ArrayList<DatosBiblioApi> mostrarTodoLibros() {
        return (ArrayList<DatosBiblioApi>) crudBiblioApiRepository.findAll();
    }

    @Override
    public Optional<DatosBiblioApi> mostrarLibroPor(long id) {
        return crudBiblioApiRepository.findById(id);
    }

    @Override
    public DatosBiblioApi registrarLibro(DatosBiblioApi libro) {
        return crudBiblioApiRepository.save(libro);
    }

    @Override
    public boolean eliminarLibro(long id) {
        try{
            //Buscar Libro
            Optional<DatosBiblioApi> libro = mostrarLibroPor(id);
            //Eliminar
            crudBiblioApiRepository.delete(libro.get());
            return true;
        }catch (Exception err){
            return false;
        }
    }

    public DatosBiblioApi modificarLibro(DatosBiblioApi libro){
        return crudBiblioApiRepository.save(libro);
    }
}
