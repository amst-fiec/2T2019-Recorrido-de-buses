package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class panel_opcion_conductor extends AppCompatActivity {
    Button btn_out_d, btn_paradas_d, btn_reg_datos_d, btn_select_parada_d, btn_historico, btn_map_sigfox,btn_map_gsm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_opcion_conductor);

        btn_out_d= (Button) findViewById(R.id.btn_out_d);
        btn_paradas_d= (Button) findViewById(R.id.btn_paradas_d);
        btn_reg_datos_d= (Button) findViewById(R.id.btn_reg_datos_d);

        btn_historico= (Button) findViewById(R.id.btn_historico);

        btn_historico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("historico");
                startActivity(new Intent(panel_opcion_conductor.this, activity_historico.class ));
            }
        });

        btn_map_sigfox= (Button) findViewById(R.id.btn_map_SigFox);
        btn_map_gsm= (Button) findViewById(R.id.btn_map_gsm);


        //Cierra sesion, devuelve al activity menu de "inicio de sesion como"
        btn_out_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_conductor.this, init_app_as.class));
            }
        });
        //abre activity paradas (HU2, alli se registran y modifican las paradas)
        btn_paradas_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_conductor.this, opcion_paradas_conductor.class));
            }
        });
        //abre activity registrar de datos (HU4, alli se registran datos del bus y de su chofer)
        btn_reg_datos_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_conductor.this, perfil_conductor.class));
            }
        });
        //abre activity seleccionar parda (HU3)

        //abre activity historico (HU5, se comparan los datos recibidos por SigFox y GSM)

        //abre activity mapa SigFox (HU1)
        btn_map_sigfox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_conductor.this, Mapa_SigFox.class));
            }
        });
        //abre activity mapa gsm (HU1)
        btn_map_gsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.panel_opcion_conductor.this, Mapa_GSM.class));
            }
        });

    }
}
