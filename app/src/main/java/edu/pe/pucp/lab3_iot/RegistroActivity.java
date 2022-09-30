package edu.pe.pucp.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import edu.pe.pucp.lab3_iot.Entities.Listas;
import edu.pe.pucp.lab3_iot.Entities.Mascotita;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


    }


    public void registrar(View view){
        EditText nombreMascota = findViewById(R.id.et_nombremascota);
        Spinner genero = findViewById(R.id.spinner_genero);
        EditText nombreDuenio = findViewById(R.id.et_nombredueño);
        EditText dni = findViewById(R.id.et_dni);
        EditText descripcion = findViewById(R.id.et_descripcion);

        String nombreMascotaStr = nombreMascota.getText().toString();
        String generoStr = genero.getSelectedItem().toString();
        String nombreDuenioStr = nombreDuenio.getText().toString();
        String dniStr = dni.getText().toString();
        String descripcionStr = descripcion.getText().toString();

        Mascotita mascota = new Mascotita(nombreMascotaStr,generoStr,nombreDuenioStr,dniStr,descripcionStr);
        Listas.addMascota(mascota);

    }
}