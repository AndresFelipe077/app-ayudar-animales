package com.example.aplicacion_felipe_sebastian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class pantallaPrincipal extends AppCompatActivity {

    CircleImageView cuenta,tomarFoto,bt1,imagenUsuario;
    TextView nombreUsuario;
    ImageButton menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        cuenta         = findViewById(R.id.cuenta);
        tomarFoto      = findViewById(R.id.tomarFoto);
        bt1            = findViewById(R.id.btn1);
        imagenUsuario  = findViewById(R.id.imagenUsuario);
        nombreUsuario  = findViewById(R.id.nombreUsuario);
        menu           = findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
               // startActivity(new Intent(pantallaPrincipal.this,login.class));
                finish();
            }
        });


    }//fin onCreate



}