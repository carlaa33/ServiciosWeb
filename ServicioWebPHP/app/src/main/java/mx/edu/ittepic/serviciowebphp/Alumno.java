package mx.edu.ittepic.serviciowebphp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Alumno extends AppCompatActivity {
    String accion;
    TextView titulo;
    Button buscar, aceptar, volver;
    EditText id,nombre,direccion;
    URL url;
    String socket;

    LinearLayout layin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        socket="w.x.y.z:8888";

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

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accion();
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
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("JSON",cadena);
    }

    private void accion() {
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
        }

    }

    private void editar() {
        HttpURLConnection conn = null;
        try {
            String URL="http://"+socket+"/datos1/actualizar_alumno.php";

            // Objeto url que recibe como parametro la cadena al webservice de Insertar
            url = new URL(URL);

            // Cast para manejar la conexión como un objeto HttpURLConnection
            conn = (HttpURLConnection) url.openConnection();

            // Establecimiento de un tipo de conexión POST
            conn.setRequestMethod("POST");

            // Propiedades de la conexión
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Creación de Objetos JSON y carga de datos
            JSONObject jsonn = new JSONObject();
            jsonn.put("idalumno", id.getText().toString());
            jsonn.put("nombre", nombre.getText().toString());
            jsonn.put("direccion", direccion.getText().toString());

            // Tambien puede usarse utilizando OutputStream
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonn.toString());

            os.flush();
            os.close();

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void eliminar() {
        HttpURLConnection conn = null;
        try {
            String URL="http://"+socket+"/datos1/borrar_alumno.php";

            // Objeto url que recibe como parametro la cadena al webservice de Insertar
            url = new URL(URL);

            // Cast para manejar la conexión como un objeto HttpURLConnection
            conn = (HttpURLConnection) url.openConnection();

            // Establecimiento de un tipo de conexión POST
            conn.setRequestMethod("POST");

            // Propiedades de la conexión
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Creación de Objetos JSON y carga de datos
            JSONObject jsonn = new JSONObject();
            jsonn.put("idalumno", id.getText().toString());

            // Tambien puede usarse utilizando OutputStream
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonn.toString());

            os.flush();
            os.close();

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void nuevo() {
        HttpURLConnection conn = null;
        try {
            String URL="http://localhost/api/alumnos";

            // Objeto url que recibe como parametro la cadena al webservice de Insertar
            url = new URL(URL);

            // Cast para manejar la conexión como un objeto HttpURLConnection
            conn = (HttpURLConnection) url.openConnection();

            // Establecimiento de un tipo de conexión POST
            conn.setRequestMethod("POST");

            // Propiedades de la conexión
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Creación de Objetos JSON y carga de datos
            JSONObject jsonn = new JSONObject();
            jsonn.put("_id", id.getText().toString());
            jsonn.put("nombre", nombre.getText().toString());
            jsonn.put("direccion", direccion.getText().toString());

            // Tambien puede usarse utilizando OutputStream
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonn.toString());

            os.flush();
            os.close();

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void inicializar(String accion) {
        titulo.setText(accion);
        switch(accion) {
            case "Nuevo":
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
}
