package edu.pe.pucp.lab3_iot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Ambulancias Mascotín");
        setContentView(R.layout.activity_main);
    }

    public void irRegistro(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivityForResult(intent,1);
    }

    public void irEmergencia(View view){
        Intent intent = new Intent(this,EmergenciaActivity.class);
        startActivity(intent);
    }

    public void irHistorial(View view){
        Intent intent = new Intent(this, HistorialActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String mascota = data.getStringExtra("mascota");
            Toast.makeText(MainActivity.this, "Se ha registrado a "+mascota, Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}