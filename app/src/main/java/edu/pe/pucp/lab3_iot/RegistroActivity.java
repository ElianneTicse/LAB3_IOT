package edu.pe.pucp.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

        if(!nombreMascotaStr.isEmpty() && !(genero.getSelectedItemPosition()==0)&& !nombreDuenioStr.isEmpty() && !dniStr.isEmpty() && !descripcionStr.isEmpty()) {
            Mascotita mascota = new Mascotita(nombreMascotaStr, generoStr, nombreDuenioStr, dniStr, descripcionStr);
            Listas.addMascota(mascota);
            nombreMascota.setText("");
            genero.setSelection(0);
            nombreDuenio.setText("");
            dni.setText("");
            descripcion.setText("");
        }else{
            Toast.makeText(RegistroActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}