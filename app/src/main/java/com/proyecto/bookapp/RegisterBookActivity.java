package com.proyecto.bookapp;

// Importaciones
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RegisterBookActivity extends AppCompatActivity {
    // Variables de instancia
    EditText editTitle, editAuthor;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_book);

        // Inicialización de variables
        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Listener del botón
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de los valores de los campos de texto
                String title = editTitle.getText().toString().trim();
                String author = editAuthor.getText().toString().trim();

                // Comprobación de campos vacíos y envío de los datos del libro
                if (title.isEmpty() || author.isEmpty()) {
                    Toast.makeText(RegisterBookActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    submitBookData(title, author);
                }
            }
        });
    }

    // Enviar los datos del libro al servidor
    private void submitBookData(final String title, final String author) {
        // Crear una cola de solicitudes y establecer la URL del servidor
        RequestQueue queue = Volley.newRequestQueue(this, new CustomHurlStack());  // Usar CustomHurlStack para gestionar las cookies
        String url = Constants.API_URL + "books";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Respuesta exitosa del servidor
                        Toast.makeText(RegisterBookActivity.this, "Libro registrado exitosamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Respuesta de error del servidor
                        Toast.makeText(RegisterBookActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    // Convertir los datos del libro a JSON
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("title", title);
                    jsonBody.put("author", author);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    Log.e("getBody", "Failed to convert JSON to bytes", e);
                    return null;
                }
            }

            @Override
            // Sirve para personalizar los headers de una solicitud HTTP que se va a enviar a algún servidor
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Crea un nuevo mapa HashMap vacío. Este mapa tiene claves y valores String. Se va a usar para almacenar las cabeceras.
                Map<String, String> headers = new HashMap<>();
                // Accede a un almacenamiento de preferencias compartidas llamado "MyPref" que se va a usar para almacenar datos
                SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
                // Obtiene una cadena llamada "cookie" de las preferencias compartidas
                String cookie = sharedPreferences.getString("cookie", "");
                // Añade una cabecera a la solicitud
                headers.put("Cookie", cookie);
                // Añade una cabecera a la solicitud, la solicitud va a contener datos JSON y que esos datos están codificados en UTF-8
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Agregar la solicitud a la cola de solicitudes
        queue.add(postRequest);
    }
}
