package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class panel_opcion_pasajero extends AppCompatActivity {
    Button btn_historico, btn_B_C_p, btn_map_gsm, btn_map_SigFox, btn_out_p,btn_select_parada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_opcion_pasajero);

        btn_historico= (Button) findViewById(R.id.btn_historico);
        btn_B_C_p= (Button) findViewById(R.id.btn_datos_B_C_p);
        btn_map_gsm= (Button) findViewById(R.id.btn_map_gsm);
        btn_map_SigFox= (Button) findViewById(R.id.btn_map_SigFox);
        btn_out_p= (Button) findViewById(R.id.btn_out_p);
        btn_select_parada= (Button) findViewById(R.id.btn_select_parada);


        //Cierra sesion, devuelve al activity menu de "inicio de sesion como"
        btn_out_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, init_app_as.class));
            }
        });
        //abre activity  menu "informacion Bus/Conductor (HU4)"
        btn_B_C_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, Datos_conductor.class));
            }
        });
        //abre activity mapa gsm (HU1)
        btn_map_gsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, Mapa_GSM.class));
            }
        });
        //abre activity mapa SigFox (HU1)
        btn_map_SigFox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, Mapa_SigFox.class));
            }
        });
        //abre activity historico (HU5)
        btn_historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, activity_historico.class));
            }
        });
        btn_select_parada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_pasajero.this, pasajero_seleccionar_ruta.class));
            }
        });

    }
}
