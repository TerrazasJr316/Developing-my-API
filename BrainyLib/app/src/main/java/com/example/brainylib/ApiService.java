package com.example.brainylib;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {

    @GET("/biblioapi/ver-todoslibros")
    Call<List<DatosBiblioApi>> getTodosLibros();

    @PUT("/biblioapi/modificar-libro/{idLibro}")
    Call<DatosBiblioApi> modificarLibro(@Path("idLibro") long id, @Body DatosBiblioApi libro);

    @POST("/biblioapi/guardar-libro")
    Call<DatosBiblioApi> guardarLibro(@Body DatosBiblioApi libro);

    @DELETE("/biblioapi/eliminar-libro/{idLibro}")
    Call<Void> eliminarLibro(@Path("idLibro") long id);

    @GET("/biblioapi/ver-libro/{idLibro}")
    Call<DatosBiblioApi> getLibroPorID(@Path("idLibro") long id);

}
