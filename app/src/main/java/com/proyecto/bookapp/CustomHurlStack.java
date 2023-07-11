package com.proyecto.bookapp;

import android.text.TextUtils;
import com.android.volley.toolbox.HurlStack;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomHurlStack extends HurlStack {
    private final CookieManager cookieManager;

    public CustomHurlStack() {
        super();
        // Se crea una instancia de CookieManager para administrar las cookies
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        // Se crea una conexión HTTP utilizando la superclase HurlStack
        HttpURLConnection connection = super.createConnection(url);
        // Se habilita el uso de caché en la conexión
        connection.setUseCaches(true);
        // Se establece la propiedad "Cookie" en el encabezado de la solicitud utilizando las cookies almacenadas en el CookieManager
        connection.setRequestProperty("Cookie", TextUtils.join(";", cookieManager.getCookieStore().getCookies()));
        return connection;
    }
}