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

import java.text.SimpleDateFormat;
import java.util.Date;


public class pasajero_show_parada extends FragmentActivity implements OnMapReadyCallback{
    //variables
    private String msgSETED,whole_coor;
    public double latVal=0;
    public double longVal=0;
    LatLng ubicacion;

    //views
    TextView txt_parada_select;

    // google
    private GoogleMap mMap, googleMap;

    @Override
    protected void onCreate (Bundle saveInstancesState) {

        super.onCreate(saveInstancesState);
        setContentView(R.layout.activity_pasajero_show_parada);

        txt_parada_select= (TextView) findViewById(R.id.txt_nombre_parada_select);

        whole_coor= pasajero_seleccionar_parada.get_coor();

        latVal= Float.parseFloat(whole_coor.split(";")[0]);
        longVal= Float.parseFloat(whole_coor.split(";")[1]);

        msgSETED="Latitud: "+ Double.toString(latVal) +
                "\nLongitud: "+Double.toString(longVal)+
                "\nnombre parada: "+pasajero_seleccionar_parada.parada_seleccionada_user();



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
        if (latVal==0){
            latVal=-2.1629;
        }
        else{
            System.out.println(" ####################### ####################### latval : "+ latVal);
        }
        if (longVal==0){
            longVal=-79.9389;
        }
        else{
            System.out.println(" ####################### ####################### longVal : "+ longVal);
        }
        //
        ubicacion= new LatLng(latVal,longVal);// setea posicion
        CameraPosition cameraPosition = CameraPosition.builder().target(ubicacion)
                .zoom(18)
                .bearing(0)
                .tilt(45)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));

        txt_parada_select.setText(msgSETED);
    }

    public void map_set_location(GoogleMap googleMap, GoogleMap mMap){

    }


}