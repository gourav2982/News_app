package com.example.news_app;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#40916c")));

        progressBar=findViewById(R.id.progressbar);

        Context you = this;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<NewsModels> list = new ArrayList<NewsModels>();

      RequestQueue queue = Volley.newRequestQueue(this);
      String url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";

      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
              (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.VISIBLE);
                  try {
                    JSONArray object = response.getJSONArray("articles");
                    for(int i=0;i<object.length();i++){
                        NewsModels news = new NewsModels();
                        JSONObject obj = object.getJSONObject(i);
                        news.title = obj.getString("title");
                        news.descripton=obj.getString("description");
                        news.image=(obj.getString("urlToImage"));
                        news.published=obj.getString("publishedAt").substring(0,10);
                        news.url=obj.getString("url");

                        list.add(news);
                    }
                      NewsAdapter adapter = new NewsAdapter(you,list);
                      recyclerView.setAdapter(adapter);
                     progressBar.setVisibility(View.GONE);

                  } catch (JSONException e) {
                      progressBar.setVisibility(View.GONE);
                    throw new RuntimeException(e);

                  }


                }
              }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                  Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
              }
        );

// Add the request to the RequestQueue.
      queue.add(jsonObjectRequest);


    }

}