package mx.edu.ittepic.serviciowebphp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;

    Button mostrar,nuevo,eliminar,editar,buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrar=findViewById(R.id.btnMostrar);
        nuevo=findViewById(R.id.btnNuevo);
        eliminar=findViewById(R.id.btnEliminar);
        editar=findViewById(R.id.btnEditar);
        buscar=findViewById(R.id.btnBuscar);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar();
            }
        });

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevo();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });




    }

    private void buscar() {
        Intent ventana=new Intent(MainActivity.this,Alumno.class);
        ventana.putExtra("accion", "Buscar");
        startActivity(ventana);
    }

    private void editar() {
        Intent ventana=new Intent(MainActivity.this,Alumno.class);
        ventana.putExtra("accion", "Editar");
        startActivity(ventana);
    }

    private void eliminar() {
        Intent ventana=new Intent(MainActivity.this,Alumno.class);
        ventana.putExtra("accion", "Eliminar");
        startActivity(ventana);
    }

    private void nuevo() {
        Intent ventana=new Intent(MainActivity.this,Alumno.class);
        ventana.putExtra("accion", "Nuevo");
        startActivity(ventana);
    }

    private void mostrar() {
        //MostrarTodo
    }


}
