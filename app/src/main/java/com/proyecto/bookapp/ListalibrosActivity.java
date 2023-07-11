package com.proyecto.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ListalibrosActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalibros);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

    }
    /*
    private void getAllBooks(){
        progressDialog.setMessage("Getting book list");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this,
                new CustomHurlStack());
        String url = Constants.API_URL + "books/all";

        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        if (response.isEmpty()){
                            progressDialog.dismiss();
                            Toast.makeText(ListalibrosActivity.this,
                                    "Getting all books failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Manejar los errores de la solicitud
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

        }
    }
    */
}