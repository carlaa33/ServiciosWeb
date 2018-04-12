package mx.edu.ittepic.serviciowebphp;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Alumno extends AppCompatActivity {
    String accion;
    TextView titulo;
    Button buscar, aceptar, volver,MT;
    EditText id,nombre,direccion;
    URL url;
    String socket;

    LinearLayout layin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        socket="192.168.10.2";

        layin=findViewById(R.id.layin);

        buscar=findViewById(R.id.btnBuscar);
        aceptar=findViewById(R.id.btnAccion);
        volver=findViewById(R.id.btnVolver);

        id=findViewById(R.id.idInput);
        nombre=findViewById(R.id.nombreInput);
        direccion=findViewById(R.id.direccionInput);

        titulo=findViewById(R.id.titulo);
        accion=getIntent().getExtras().getString("accion");

        inicializar(accion);

        volver.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aceptar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Conexion().execute();
                finish();
            }
        });

        buscar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                accion="Buscar";
                new Conexion().execute();
            }
        });
    }

    private void inicializar(String accion) {
        titulo.setText(accion);
        switch(accion) {
            case "Nuevo":
                layin.setVisibility(View.INVISIBLE);
                aceptar.setText("Guardar");
                break;
            case "Eliminar":
                aceptar.setText("Eliminar");
                nombre.setEnabled(false);
                direccion.setEnabled(false);
                break;
            case "Editar":
                aceptar.setText("Editar");
                break;
            case "Buscar":
                aceptar.setVisibility(View.INVISIBLE);
                nombre.setEnabled(false);
                direccion.setEnabled(false);
                break;

        }

    }


    public class Conexion extends AsyncTask<Void, Void, String> {
        private String buscar() {
            String URL="http://"+socket+"/datos1/obtener_alumno_por_id.php?idalumno="+id.getText();
            String cadena="";
            try {
                URL url = new URL(URL);
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
                    cadena = stringBuilder.toString().trim();
                    cadena=cadena.replace("{\"estado\":1,\"alumno\":","");
                    cadena=cadena.replace("\"}}","\"}");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                Log.e("Error",e.getMessage());
            }
            return cadena;
        }

        private void editar() {
            HttpURLConnection conn = null;
            try {
                String URL="http://"+socket+"/datos1/actualizar_alumno.php";

                String json_string;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(URL);
                HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                JSONObject json = new JSONObject();
                json.put("idalumno", id.getText().toString());
                json.put("nombre", nombre.getText().toString());
                json.put("direccion", direccion.getText().toString());
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(json.toString());
                writer.flush();
                writer.close();
                os.close();
                int respuesta = 0;
                respuesta = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                }
            }catch (java.net.MalformedURLException e){

            }catch (IOException e) {

            }catch (org.json.JSONException e){

            }
        }

        private void eliminar() {
            HttpURLConnection conn = null;
            try {
                String URL="http://"+socket+"/datos1/borrar_alumno.php";

                String json_string;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(URL);
                HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                JSONObject json = new JSONObject();
                json.put("idalumno",id.getText().toString());
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(json.toString());
                writer.flush();
                writer.close();
                os.close();
                int respuesta = 0;
                respuesta = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                }
            }catch (java.net.MalformedURLException e){

            }catch (IOException e) {

            }catch (org.json.JSONException e){

            }
        }

        private void nuevo() {
            try {

                String URL="http://"+socket+"/datos1/insertar_alumno.php";
                String json_string;
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(URL);
                HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                JSONObject json = new JSONObject();
                json.put("nombre",nombre.getText().toString());
                json.put("direccion",direccion.getText().toString());
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(json.toString());
                writer.flush();
                writer.close();
                os.close();
                int respuesta = 0;
                respuesta = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                if (respuesta == HttpURLConnection.HTTP_OK) {
                    while ((json_string = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json_string + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                }
            }catch (java.net.MalformedURLException e){

            }catch (IOException e) {

            }catch (org.json.JSONException e){

            }
        }

        @Override
        protected String doInBackground(Void... Voids) {
            String tmp="";

            switch(accion) {
                case "Nuevo":
                    nuevo();
                    break;
                case "Eliminar":
                    eliminar();
                    break;
                case "Editar":
                    editar();
                    break;
                case "Buscar":
                    tmp=buscar();
            }
            return  tmp;
        }

        @Override
        protected void onPostExecute(String result) {
            if(!result.isEmpty()){
                try {
                    JSONObject alumno = new JSONObject(result);
                    nombre.setText(alumno.get("nombre").toString());
                    direccion.setText(alumno.get("direccion").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            accion=titulo.getText().toString();

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

