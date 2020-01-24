package com.e.main_hu4_prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class registrar_parada_conductor extends AppCompatActivity {

    Button btn_reg;
    TextView txt_parda, txt_coordenadas;
    parada parada_o;
    String parada_fromTXT, coor_fromTXT, nombre_parada;
    float latitud,longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_parada);

        btn_reg= (Button) findViewById(R.id.btn_registrar);
        txt_coordenadas=(TextView) findViewById(R.id.edit_coordenadas);
        txt_parda= (TextView) findViewById(R.id.edit_nombre_parada);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("Coordenadas: "+txt_coordenadas.getEditableText().toString());
                //System.out.println("parada: "+txt_parda.getEditableText().toString());
                //coor_fromTXT= txt_coordenadas.getEditableText().toString();
                //parada_fromTXT=txt_parda.getEditableText().toString();

                //creo objeto
                parada_o= new parada (txt_parda.getEditableText().toString(),
                        txt_coordenadas.getEditableText().toString()
                );

                //adquiero parametros; longitud, latitud y nombre parada
                longitud=parada_o.longitud_f();
                latitud=parada_o.latitud_f();
                nombre_parada=parada_o.getNombre_parada();

                System.out.print("longitud, latitud: ");
                System.out.println(latitud);


            }
        });

    }
}