package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context you = this;
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<NewsModels> list = new ArrayList<NewsModels>();
      RequestQueue queue = Volley.newRequestQueue(this);
      String url = "https://gnews.io/api/v4/top-headlines?category=general&lang=en&country=us&max=70&apikey=5f898930f6f4260c3c96eaba61e17e1c";

      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
              (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                  try {
                    JSONArray object = response.getJSONArray("articles");
                    for(int i=0;i<object.length();i++){
                        NewsModels news = new NewsModels();
                        JSONObject obj = object.getJSONObject(i);
                        news.title = obj.getString("title");
                        news.descripton=obj.getString("description");
                        news.image=(obj.getString("image"));

                        list.add(news);
                    }
                      NewsAdapter adapter = new NewsAdapter(you,list);
                      recyclerView.setAdapter(adapter);

                  } catch (JSONException e) {
                    throw new RuntimeException(e);
                  }


                }
              }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                  Toast.makeText(MainActivity.this, "Fuck you", Toast.LENGTH_SHORT).show();

                }
              });

// Add the request to the RequestQueue.
      queue.add(jsonObjectRequest);



    }

}