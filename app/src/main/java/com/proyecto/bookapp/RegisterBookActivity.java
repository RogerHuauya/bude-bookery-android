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
        String url = "http://www.rogerhuauya.com/server/books";

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Configurar las cabeceras para la solicitud
                Map<String, String> headers = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
                String cookie = sharedPreferences.getString("cookie", "");
                headers.put("Cookie", cookie);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Agregar la solicitud a la cola de solicitudes
        queue.add(postRequest);
    }
}
