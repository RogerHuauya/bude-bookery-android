package com.proyecto.bookapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.proyecto.bookapp.databinding.ActivityMain2Binding;
public class MainActivity2 extends AppCompatActivity {

    //Esta variable se utiliza para acceder a los elementos de la interfaz de usuario definidos en el archivo XML
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Se utiliza para inflar la vista y obtener una referencia a los elementos de la interfaz de usuario
        binding = ActivityMain2Binding.inflate(getLayoutInflater());

        // Se utiliza para obtener la vista raíz
        setContentView(binding.getRoot());

        // registerBookBtn inicia la actividad RegisterBookActivity cuando se hace clic en él
        binding.registerBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,RegisterBookActivity.class));
            }
        });

        // viewListBtn inicia la actividad RegisterActivity cuando se hace clic en él
        binding.viewListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,ListalibrosActivity.class));
            }
        });

        // logoutBtn inicia la actividad SplashActivity y finaliza la actividad actual (MainActivity2) cuando se hace clic en él
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,SplashActivity.class));
                finish();
            }
        });
    }
}