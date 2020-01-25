package com.e.main_hu4_prueba;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;


public class Mapa_GSM  extends FragmentActivity implements OnMapReadyCallback, Runnable {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private String msgSETED;

    TextView messageTV;

    private GoogleMap mMap;
    public double latVal=0;
    public double longVal=0;

    /////////////////////////// nuevo no updated
    LatLng ubicacion;
    ///////////////////////////
    MyReceiver receiver = new MyReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

            latVal= Float.parseFloat(msg.split(";")[0]);
            longVal= Float.parseFloat(msg.split(";")[1]);

            msgSETED="Latitud: "+ Double.toString(latVal)+" ; "+
                    "Longitud: "+Double.toString(longVal);

            messageTV.setText(msgSETED);

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate (Bundle saveInstancesState) {

        super.onCreate(saveInstancesState);
        setContentView(R.layout.activity_mapa__gsm);

        messageTV = (TextView) findViewById(R.id.mensaje);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear();

        UiSettings uiSettings= mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        //validacion
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(Mapa_GSM.this, "NO mMap.setMyLocationEnabled(true)", Toast.LENGTH_SHORT).show();
        }

        if (latVal==0){
            latVal=-2.1629;
        }
        if (longVal==0){
            longVal=-79.9389;
        }

        ubicacion= new LatLng(latVal,longVal);// setea posicion

        CameraPosition cameraPosition = CameraPosition.builder().target(ubicacion)
                .zoom(18)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
    }

    @Override
    public void run() {

    }
}