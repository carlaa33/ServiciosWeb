package mx.edu.ittepic.serviciowebphp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ListView lista;

    Button mostrar,nuevo,eliminar,editar,buscar;
    String socket;

    @Override
    protected void onResume() {
        super.onResume();
        new MostrarTodos().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrar=findViewById(R.id.btnMostrar);
        nuevo=findViewById(R.id.btnNuevo);
        eliminar=findViewById(R.id.btnEliminar);
        editar=findViewById(R.id.btnEditar);
        buscar=findViewById(R.id.btnBuscar);
        lista=findViewById(R.id.LV);
        socket="192.168.10.2";

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
        new MostrarTodos().execute();

    }

    public class MostrarTodos extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... Voids) {
            String tmp="";
            String URL="http://"+socket+"/datos1/obtener_alumnos.php";
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int respuesta = httpURLConnection.getResponseCode();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {
                    while ((URL = bufferedReader.readLine()) != null) {
                        stringBuilder.append(URL + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    tmp = stringBuilder.toString().trim();
                    tmp=tmp.replace("{\"estado\":1,\"alumnos\":","{alumnos:");

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                Log.e("Error",e.getMessage());
            }
            return tmp;
        }

        @Override
        protected void onPostExecute(String result) {
            String a="";
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.getJSONArray("alumnos");for(int i=0; i<jArray.length(); i++){
                    JSONObject datos = jArray.getJSONObject(i);

                    a+= datos.getString("idalumno")+" "+datos.getString("nombre")+" "+datos.getString("direccion")+"\n";
                }
            } catch (JSONException e) {
                Log.e("Error",e.getMessage());
            }

            String nLista[]=a.split("\n");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, nLista);
            lista.setAdapter(adaptador);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
