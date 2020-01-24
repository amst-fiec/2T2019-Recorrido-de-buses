package com.e.main_hu4_prueba;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class seleccionar_parada extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_parada);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        obtenerPosicionFirebase();
    }
    private void obtenerPosicionFirebase() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e( "Latitud: ",+location.getLatitude()+"Longitud: "+location.getLongitude());

                        }
                    }
                });
    }
}
