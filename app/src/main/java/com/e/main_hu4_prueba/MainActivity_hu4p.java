package com.e.main_hu4_prueba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity_hu4p extends AppCompatActivity {
    private static final int MY_Permission_REQUEST_RECEIVE_SMS=0;

    private Button btn_iniciar_app;
    //objeto firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar_app= (Button) findViewById(R.id.btn_paradas_d);
        btn_iniciar_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.e.main_hu4_prueba.MainActivity_hu4p.this,init_app_as.class ));

                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();

            }
        });


    }

    /*
    //revisar si el usuario esta logeado
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    //metodo updateUI, revisa si el usuario ya esta logeado
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this.AnotherActivity.class));
        } else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }
    */

    //solamente advierte sobre los permisos
    @Override
    public void onRequestPermissionsResult (int requestcode, String permissions[], int[] grantResults)
    {
        switch(requestcode)
        {
            case MY_Permission_REQUEST_RECEIVE_SMS:
            {
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Tankyou for permitting!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "Well i can't do anythig until you permit me", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

}
