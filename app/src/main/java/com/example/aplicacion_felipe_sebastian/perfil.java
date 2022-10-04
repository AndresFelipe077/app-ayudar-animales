package com.example.aplicacion_felipe_sebastian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class perfil extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private TextView nombreOriginal,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreOriginal = findViewById(R.id.nombreOriginal);
        descripcion    = findViewById(R.id.descripcion);

    }// fin onCreate


}//fin de clase
