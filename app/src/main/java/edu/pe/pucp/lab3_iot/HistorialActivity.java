package edu.pe.pucp.lab3_iot;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.pe.pucp.lab3_iot.Adapters.HistorialAdapter;
import edu.pe.pucp.lab3_iot.Entities.Historial;
import edu.pe.pucp.lab3_iot.Entities.Listas;
import edu.pe.pucp.lab3_iot.Entities.Mascotita;


public class HistorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        ArrayList<Historial> listaHistorial = Listas.getListaHistorial();

        /*Para agregar al historial
        Mascotita masc = new Mascotita("Perro","Macho","Oliver","71231313","Bomnito");
        Historial historial = new Historial(masc,"Lince","Miraflores");
        Listas.addHistorial(historial);
        */

        RecyclerView recyclerView = findViewById(R.id.recycleHistorial);
        HistorialAdapter historialAdapter = new HistorialAdapter(listaHistorial);
        recyclerView.setAdapter(historialAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistorialActivity.this));
    }
}