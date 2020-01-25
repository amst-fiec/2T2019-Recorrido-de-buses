package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Mapa_GSM extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public DatabaseReference db_reference;
    public double latVal;
    public double longVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa__gsm);
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
        mMap.setMyLocationEnabled(true);
        latVal=-2.1629;
        longVal=-79.9389;
        db_reference = FirebaseDatabase.getInstance().getReference().child("Dispotivo");

        db_reference.addValueEventListener(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                String latVal1 = String.valueOf(snapshot.child("Latitud").getValue());
                String longVal1 = String.valueOf(snapshot.child("Longitud").getValue());

                if (latVal1.equals("null") || longVal1.equals("null")){
                    latVal=-2.1629;
                    longVal=-79.9389;
                }
                else if (latVal1.equals(" ") || longVal1.equals(" ")){
                    latVal=-2.1629;
                    longVal=-79.9389;
                }
                else{
                    latVal= new Double(latVal1);
                    longVal=new Double (longVal1);

                } }}
            @Override
            public void onCancelled(DatabaseError error) { System.out.println(error.toException());
            } });


        LatLng ubicacion = new LatLng(latVal,longVal);
        CameraPosition cameraPosition = CameraPosition.builder().target(ubicacion)
                .zoom(18)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));


    }

}