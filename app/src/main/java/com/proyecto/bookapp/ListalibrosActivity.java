package com.proyecto.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
//de aquí para abajo le añadí nuevos import'sxd
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListalibrosActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listalibros);

        progressDialog = new ProgressDialog(this);
        // Obtén el LinearLayout principal desde el archivo XML
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        getAllBooks();
    }
    private void getAllBooks() {
        progressDialog.setMessage("Getting book list");
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this, new CustomHurlStack());
        String url = Constants.API_URL+"books/all";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();

                        try {
                            List<Book> bookList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bookObj = response.getJSONObject(i);

                                String title = bookObj.getString("title");
                                String author = bookObj.getString("author");

                                Book book = new Book(title, author);
                                bookList.add(book);
                            }

                            LinearLayout linearLayout = findViewById(R.id.linearLayout);

                            for (Book book : bookList) {
                                String title = book.getTitle();
                                String author = book.getAuthor();

                                LinearLayout linearLayoutItem = new LinearLayout(ListalibrosActivity.this);
                                linearLayoutItem.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                linearLayoutItem.setOrientation(LinearLayout.HORIZONTAL);

                                TextView textView1 = new TextView(ListalibrosActivity.this);
                                textView1.setLayoutParams(new LinearLayout.LayoutParams(
                                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                textView1.setText(title);
                                textView1.setTextSize(17);
                                textView1.setTypeface(null, Typeface.BOLD);

                                TextView textView2 = new TextView(ListalibrosActivity.this);
                                textView2.setLayoutParams(new LinearLayout.LayoutParams(
                                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                                textView2.setText(author);
                                textView2.setTextSize(17);
                                textView2.setTypeface(null, Typeface.BOLD);

                                linearLayoutItem.addView(textView1);
                                linearLayoutItem.addView(textView2);

                                linearLayout.addView(linearLayoutItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListalibrosActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ListalibrosActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(getRequest);
    }
}
