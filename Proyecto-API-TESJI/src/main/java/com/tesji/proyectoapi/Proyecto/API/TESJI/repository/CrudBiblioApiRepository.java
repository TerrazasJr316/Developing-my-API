package com.tesji.proyectoapi.Proyecto.API.TESJI.repository;

import com.tesji.proyectoapi.Proyecto.API.TESJI.model.DatosBiblioApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*
Esta clase define las aplicaciones CRUD por herencia y métodos a btractos
*/
@Repository
public interface CrudBiblioApiRepository extends CrudRepository<DatosBiblioApi,Long> {
}

