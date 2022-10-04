package edu.pe.pucp.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ambulancias Mascotín");
        setContentView(R.layout.activity_main);
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    public void irEmergencia(View view){
        Intent intent = new Intent(this,EmergenciaActivity.class);
        startActivity(intent);
    }

    public void irHistorial(View view){
        Intent intent = new Intent(this, HistorialActivity.class);
        startActivity(intent);
    }

}