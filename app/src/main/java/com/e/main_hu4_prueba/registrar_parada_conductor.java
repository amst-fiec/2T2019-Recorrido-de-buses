package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registrar_parada_conductor extends AppCompatActivity {

    Button btn_reg;
    TextView txt_parda, txt_coordenadas1,txt_coordenadas2;
    parada parada_o;
    String parada_fromTXT, coor_fromTXT, nombre_parada;
    float latitud,longitud;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_parada);

        btn_reg= (Button) findViewById(R.id.btn_registrar);
        txt_coordenadas1=(TextView) findViewById(R.id.edit_coordenadas1);
        txt_coordenadas2=(TextView) findViewById(R.id.edit_coordenadas1);
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
                        txt_coordenadas1.getEditableText().toString(),txt_coordenadas2.getEditableText().toString()
                );

                //adquiero parametros; longitud, latitud y nombre parada
                longitud=parada_o.longitud_f();
                latitud=parada_o.latitud_f();
                nombre_parada=parada_o.getNombre_parada();
                Map<String, Object> mapP = new HashMap<>();
                mapP.put("longitud", parada_o.getCoordenadas2());
                mapP.put("latitud", parada_o.getCoordenadas1());
                mapP.put("nombre parada", nombre_parada);
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("Paradas").push().setValue(mapP).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (task2.isSuccessful()) {
                            Toast.makeText(registrar_parada_conductor.this, "Se ha registrado la parada", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(registrar_parada_conductor.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                System.out.print("longitud, latitud: ");
                System.out.println(latitud);
                startActivity(new Intent(registrar_parada_conductor.this, panel_opcion_conductor.class));


            }
        });

    }
}