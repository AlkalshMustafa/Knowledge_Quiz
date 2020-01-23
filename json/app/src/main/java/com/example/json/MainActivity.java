package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    /**             BBBBBBBBBB
     *  making the request Queue
     *  instantiated inside onCreate().
     */
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this);

        // =====================  Creating JsonObjectRequest  =====================
        /**         AAAAAAAAAA
         * ======== DON'T FORGET THE INTERNET PERMISSION ==========
         * to be able to see the response we have to make queue (as its a protocol
         * for Json requests),
         * and add inside this queue our request.
         * ======= creating the request =======
         * 1- method of request.
         * 2- the Url that we request from it.
         * 3- json request "null".
         * 4- Object response listener.
         * 5- error response listener.
         */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos/1", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // here we can get the response.
                        Log.d("JSON", "onResponse" + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("ERROR", "onErrorRequest" + error);

            }
        });

        // ===============  adding JsonObjectRequest to the Queue ===============
        requestQueue.add(jsonObjectRequest);

    }
}
