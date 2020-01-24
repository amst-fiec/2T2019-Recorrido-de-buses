package com.e.main_hu4_prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_user extends AppCompatActivity {

    private Button btnInicio;
    private EditText txtmail,txtcontraseña;

    private String email,contraseña;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnInicio=(Button) findViewById(R.id.Inicio);
        txtmail=(EditText) findViewById(R.id.email);
        txtcontraseña=(EditText) findViewById(R.id.contraseña);

        mAuth=FirebaseAuth.getInstance();
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=txtmail.getText().toString();
                contraseña= txtcontraseña.getText().toString();

                if (!email.isEmpty() && !contraseña.isEmpty()){
                    loginUser();
                }
                else{
                    Toast.makeText(login_user.this, "complete todos los campos",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void loginUser() {
        mAuth.signInWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //REVISA
                    //startActivity(new Intent(login_user.this,Mapa_SigFox.class));
                    //hay que validar si es usuairo o pasajero el que inicia sesion
                    if (init_app_as.Listener.equals("btn_conductor")){
                        init_app_as.setListener("");
                        startActivity(new Intent(login_user.this,panel_opcion_conductor.class));
                        finish();
                    }


                    if (init_app_as.Listener.equals("btn_pasajero")){
                        init_app_as.setListener("");
                        startActivity(new Intent(login_user.this,panel_opcion_pasajero.class));
                        finish();
                    }


                }
                else{
                    Toast.makeText(login_user.this, "No se puede iniciar sesion,compruebe los datos",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}

