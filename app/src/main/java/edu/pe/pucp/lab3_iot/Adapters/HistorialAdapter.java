package edu.pe.pucp.lab3_iot.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.pe.pucp.lab3_iot.Entities.Historial;
import edu.pe.pucp.lab3_iot.Entities.Listas;
import edu.pe.pucp.lab3_iot.Entities.Mascotita;
import edu.pe.pucp.lab3_iot.R;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder>{
    private ArrayList<Historial> listaHistorial;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
        public TextView getTextView(){
            return getTextView();
        }

    }

    public HistorialAdapter(ArrayList<Historial> dataSet){
        listaHistorial=dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.historial_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Historial historial = listaHistorial.get(position);
        Mascotita masc= historial.getMascotita();
        String mostrar= "Mascota: "+masc.getNombre();
        mostrar+= "\nGénero: "+masc.getNombre();
        mostrar+= "\nDueño: "+masc.getDuenho();
        mostrar+="\nDNI: "+masc.getDni();
        mostrar+="\nDescripción: "+masc.getDescripcion();
        mostrar+="\nRuta: "+historial.getOrigen()+" - "+historial.getDestino();
        TextView textShow = holder.itemView.findViewById(R.id.textHistorial);
        textShow.setText(mostrar);
    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }
}
