package com.example.brainylib;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainHome extends AppCompatActivity implements LibrosAdapter.OnItemClickListener {
    private static final String TAG = "MainHome";
    private ApiService apiService;
    private Button btnConsult, btnUpdate, btnDelete, btnNew, btnConsultID, btnClear;
    private EditText inputLibroID, inputTitulo, inputAutores, inputEditorial, inputEdicion;
    private RecyclerView recyclerView;
    private LibrosAdapter librosAdapter;
    private long selectedLibroId = -1;
    private List<DatosBiblioApi> listaLibros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.102.12:8080/biblioapi/")  // Asegurarse de usar la URL correcta
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);
        btnConsult = findViewById(R.id.btnConsult);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnNew = findViewById(R.id.btnNew);
        btnConsultID = findViewById(R.id.btnConsultID);
        btnClear = findViewById(R.id.btnClear);
        inputLibroID = findViewById(R.id.inputLibroID);
        inputTitulo = findViewById(R.id.inputTitulo);
        inputAutores = findViewById(R.id.inputAutores);
        inputEditorial = findViewById(R.id.inputEditorial);
        inputEdicion = findViewById(R.id.inputEdicion);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        librosAdapter = new LibrosAdapter(this);
        recyclerView.setAdapter(librosAdapter);
        listaLibros = new ArrayList<>();
        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTodosLosLibros();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLibroId != -1) {
                    modificarLibro(selectedLibroId);
                } else {
                    Toast.makeText(MainHome.this, "Selecciona un libro para modificar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputLibroID.getText().toString().isEmpty()) {
                    long id = Long.parseLong(inputLibroID.getText().toString());
                    eliminarLibro(id);
                } else {
                    Toast.makeText(MainHome.this, "ID del libro no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLibro();
            }
        });


        btnConsultID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputLibroID.getText().toString().isEmpty()) {
                    long id = Long.parseLong(inputLibroID.getText().toString());
                    verLibroPorID(id);
                } else {
                    Toast.makeText(MainHome.this, "ID del libro no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void verTodosLosLibros() {
        Log.d(TAG, "Intentando obtener todos los libros...");
        Call<List<DatosBiblioApi>> call = apiService.getTodosLibros();
        call.enqueue(new Callback<List<DatosBiblioApi>>() {
            @Override
            public void onResponse(Call<List<DatosBiblioApi>> call, Response<List<DatosBiblioApi>> response) {
                if (response.isSuccessful()) {
                    List<DatosBiblioApi> libros = response.body();
                    Log.d(TAG, "Libros obtenidos: " + libros.size());
                    librosAdapter.setLibros(libros);
                    for (DatosBiblioApi libro : libros) {
                        Log.d(TAG, "Libro ID: " + libro.getId() + ", Título: " + libro.getTitulo());
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                    Toast.makeText(MainHome.this, "Error al obtener los libros", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DatosBiblioApi>> call, Throwable t) {
                Log.e(TAG, "Error al obtener los libros", t);
                Toast.makeText(MainHome.this, "Error al obtener los libros: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modificarLibro(long id) {
        String libroID = inputLibroID.getText().toString();
        DatosBiblioApi libroModificado = new DatosBiblioApi();
        libroModificado.setId(id); // Usar el ID pasado como parámetro para la solicitud
        libroModificado.setTitulo(inputTitulo.getText().toString());
        libroModificado.setAutores(inputAutores.getText().toString());
        libroModificado.setEditorial(inputEditorial.getText().toString());
        libroModificado.setEdicion(inputEdicion.getText().toString());

        Log.d(TAG, "Intentando modificar el libro con ID: " + id);
        Log.d(TAG, "Datos a enviar: " + libroModificado.toString());

        Call<DatosBiblioApi> call = apiService.modificarLibro(id, libroModificado);
        call.enqueue(new Callback<DatosBiblioApi>() {
            @Override
            public void onResponse(Call<DatosBiblioApi> call, Response<DatosBiblioApi> response) {
                if (response.isSuccessful()) {
                    DatosBiblioApi libroModificadoResponse = response.body();
                    Log.d(TAG, "Libro modificado: " + libroModificadoResponse.getTitulo());
                    Toast.makeText(MainHome.this, "Libro modificado: " + libroModificadoResponse.getTitulo(), Toast.LENGTH_SHORT).show();

                    if (!libroID.isEmpty()) {
                        long newId = Long.parseLong(libroID); // Nuevo ID ingresado por el usuario
                        boolean libroEncontrado = false;
                        for (DatosBiblioApi libro : listaLibros) {
                            if (libro.getId() == id) { // Buscar el libro con el ID original
                                libro.setId(newId); // Actualizar con el nuevo ID
                                libro.setTitulo(libroModificado.getTitulo());
                                libro.setAutores(libroModificado.getAutores());
                                libro.setEditorial(libroModificado.getEditorial());
                                libro.setEdicion(libroModificado.getEdicion());
                                libroEncontrado = true;
                                break;
                            }
                        }
                        if (libroEncontrado) {
                            Toast.makeText(MainHome.this, "Libro modificado correctamente", Toast.LENGTH_SHORT).show();
                            verTodosLosLibros();  // Refrescar la lista de libros después de modificar
                        } else {
                            Toast.makeText(MainHome.this, "Libro no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainHome.this, "Por favor, ingrese el ID del libro", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta. Código: " + response.code() + ", Mensaje: " + response.message());
                    Toast.makeText(MainHome.this, "Error al modificar el libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DatosBiblioApi> call, Throwable t) {
                Log.e(TAG, "Error al modificar el libro", t);
                Toast.makeText(MainHome.this, "Error al modificar el libro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void eliminarLibro(long id) {
        Log.d(TAG, "Intentando eliminar libro por ID: " + id);
        Call<Void> call = apiService.eliminarLibro(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Libro eliminado exitosamente");
                    Toast.makeText(MainHome.this, "Libro eliminado exitosamente", Toast.LENGTH_SHORT).show();
                    verTodosLosLibros();  // Refrescar la lista de libros después de eliminar
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                    Toast.makeText(MainHome.this, "Error al eliminar el libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error al eliminar el libro", t);
                Toast.makeText(MainHome.this, "Error al eliminar el libro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarLibro() {
        String libroID = inputLibroID.getText().toString();
        DatosBiblioApi nuevoLibro = new DatosBiblioApi();
        nuevoLibro.setTitulo(inputTitulo.getText().toString());
        nuevoLibro.setAutores(inputAutores.getText().toString());
        nuevoLibro.setEditorial(inputEditorial.getText().toString());
        nuevoLibro.setEdicion(inputEdicion.getText().toString());

        Log.d(TAG, "Intentando agregar un nuevo libro...");

        // Verificación de duplicados antes de llamar a la API
        if (!libroID.isEmpty() && !nuevoLibro.getTitulo().isEmpty() && !nuevoLibro.getAutores().isEmpty() && !nuevoLibro.getEditorial().isEmpty() && !nuevoLibro.getEdicion().isEmpty()) {
            long id = Long.parseLong(libroID);
            for (DatosBiblioApi libro : listaLibros) {
                if (libro.getId() == id ||
                        (libro.getTitulo().equals(nuevoLibro.getTitulo()) &&
                                libro.getAutores().equals(nuevoLibro.getAutores()) &&
                                libro.getEditorial().equals(nuevoLibro.getEditorial()) &&
                                libro.getEdicion().equals(nuevoLibro.getEdicion()))) {
                    Toast.makeText(MainHome.this, "El libro ya existe", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            nuevoLibro.setId(id);
            listaLibros.add(nuevoLibro);

            Call<DatosBiblioApi> call = apiService.guardarLibro(nuevoLibro);
            call.enqueue(new Callback<DatosBiblioApi>() {
                @Override
                public void onResponse(Call<DatosBiblioApi> call, Response<DatosBiblioApi> response) {
                    if (response.isSuccessful()) {
                        DatosBiblioApi libroGuardado = response.body();
                        Log.d(TAG, "Libro agregado: " + libroGuardado.getTitulo());
                        Toast.makeText(MainHome.this, "Libro agregado: " + libroGuardado.getTitulo(), Toast.LENGTH_SHORT).show();
                        verTodosLosLibros();  // Refrescar la lista de libros después de agregar
                    } else {
                        Log.e(TAG, "El libro  ya existe: " + response.message());
                        Toast.makeText(MainHome.this, "Error al agregar el libro", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DatosBiblioApi> call, Throwable t) {
                    Log.e(TAG, "Error al agregar el libro", t);
                    Toast.makeText(MainHome.this, "Error al agregar el libro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainHome.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void verLibroPorID(long id) {
        Log.d(TAG, "Intentando obtener libro por ID: " + id);
        Call<DatosBiblioApi> call = apiService.getLibroPorID(id);
        call.enqueue(new Callback<DatosBiblioApi>() {
            @Override
            public void onResponse(Call<DatosBiblioApi> call, Response<DatosBiblioApi> response) {
                if (response.isSuccessful()) {
                    DatosBiblioApi libro = response.body();
                    if (libro != null) {
                        Log.d(TAG, "Libro obtenido por ID: " + libro.getTitulo());
                        inputLibroID.setText(String.valueOf(libro.getId()));
                        inputTitulo.setText(libro.getTitulo());
                        inputAutores.setText(libro.getAutores());
                        inputEditorial.setText(libro.getEditorial());
                        inputEdicion.setText(libro.getEdicion());
                        selectedLibroId = libro.getId();
                    } else {
                        Log.e(TAG, "Libro no encontrado");
                        Toast.makeText(MainHome.this, "Libro no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.message());
                    Toast.makeText(MainHome.this, "Error al obtener el libro por ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DatosBiblioApi> call, Throwable t) {
                Log.e(TAG, "Error al obtener el libro por ID", t);
                Toast.makeText(MainHome.this, "Error al obtener el libro por ID: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        inputLibroID.setText("");
        inputTitulo.setText("");
        inputAutores.setText("");
        inputEditorial.setText("");
        inputEdicion.setText("");
        selectedLibroId = -1;
        Toast.makeText(MainHome.this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(DatosBiblioApi libro) {
        inputLibroID.setText(String.valueOf(libro.getId()));
        inputTitulo.setText(libro.getTitulo());
        inputAutores.setText(libro.getAutores());
        inputEditorial.setText(libro.getEditorial());
        inputEdicion.setText(libro.getEdicion());
        selectedLibroId = libro.getId();
    }
}
