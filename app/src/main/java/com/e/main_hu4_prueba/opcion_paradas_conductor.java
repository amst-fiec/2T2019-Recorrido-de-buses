package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class opcion_paradas_conductor extends AppCompatActivity {
    private Button btn_reg_parada, btn_del_parada, btn_mod_parada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conductor);

        //btn_del_parada= (Button)findViewById(R.id.btn_delete_parada);
        btn_reg_parada= (Button)findViewById(R.id.btn_regis_parada);
        btn_mod_parada= (Button)findViewById(R.id.btn_modify_parada);
        btn_del_parada= (Button)findViewById(R.id.btn_delete_parada);
<<<<<<< HEAD

=======
        //btn_perfil= (Button)findViewById(R.id.btn_perfilC);
>>>>>>> 1f8c3f7f6188ddc16531d50f7ba55b85afec99e8

        btn_reg_parada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hola_from_menu-conductor");
                startActivity(new Intent(opcion_paradas_conductor.this, registrar_parada_conductor.class ));
            }
        });
        btn_del_parada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hola_from_menu-conductor");
                startActivity(new Intent(opcion_paradas_conductor.this, eliminar_parada_conductor.class ));
            }
        });
        btn_mod_parada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hola_from_menu-conductor");
                startActivity(new Intent(opcion_paradas_conductor.this, modificar_parada_condutor.class ));
            }
        });
<<<<<<< HEAD



=======
/*
        btn_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(opcion_paradas_conductor.this, perfil_conductor.class));
            }
        });
*/
>>>>>>> 1f8c3f7f6188ddc16531d50f7ba55b85afec99e8
        //btn_reg_parada= (Button)findViewById(R.id.btn_regis_parada);
    }




}
