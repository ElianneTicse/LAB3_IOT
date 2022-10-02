package edu.pe.pucp.lab3_iot;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContadorViewModel extends ViewModel {

    private MutableLiveData<Integer> contador = new MutableLiveData<>();
    private Thread thread = null;

    public void contarNal0(int n) {
        thread = new Thread(() -> {
            int i = n;
            while (i >= 0) {
                contador.postValue(i);
                i--;

                try {
                    Thread.sleep(100); //Acelerado, revertir luego
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public MutableLiveData<Integer> getContador() {
        return contador;
    }

    public void setContador(MutableLiveData<Integer> contador) {
        this.contador = contador;
    }
}
