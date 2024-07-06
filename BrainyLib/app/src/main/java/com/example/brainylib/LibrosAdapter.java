package com.example.brainylib;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class LibrosAdapter extends RecyclerView.Adapter<LibrosAdapter.LibroViewHolder> {
    private List<DatosBiblioApi> libros = new ArrayList<>();
    private OnItemClickListener listener;
    public LibrosAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.api_book, parent, false);
        return new LibroViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        DatosBiblioApi libro = libros.get(position);
        holder.id.setText("ID: " + libro.getId());
        holder.titulo.setText("Título: " + libro.getTitulo());
        holder.autores.setText("Autores: " + libro.getAutores());
        holder.editorial.setText("Editorial: " + libro.getEditorial());
        holder.edicion.setText("Edición: " + libro.getEdicion());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(libro);
            }
        });
    }
    @Override
    public int getItemCount() {
        return libros.size();
    }
    public void setLibros(List<DatosBiblioApi> libros) {
        this.libros = libros;
        notifyDataSetChanged();
    }
    class LibroViewHolder extends RecyclerView.ViewHolder {
        TextView id, titulo, autores, editorial, edicion;
        LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            titulo = itemView.findViewById(R.id.titulo);
            autores = itemView.findViewById(R.id.autores);
            editorial = itemView.findViewById(R.id.editorial);
            edicion = itemView.findViewById(R.id.edicion);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DatosBiblioApi libro);
    }
}