package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class mapa_drawingRoute extends FragmentActivity implements OnMapReadyCallback, Runnable{

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private String msgSETED;

    TextView messageTV;

    private GoogleMap mMap, googleMap;
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

            msgSETED="Latitud: "+ Double.toString(latVal) +
                    "\nLongitud: "+Double.toString(longVal);
            System.out.println(" ++++++++++++++++++++ "+"msgSETED: "+msgSETED);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_drawing_route);

        messageTV = (TextView) findViewById(R.id.mensaje_from_drawMap);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void run() {

    }
}
