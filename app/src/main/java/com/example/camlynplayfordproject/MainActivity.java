package com.example.camlynplayfordproject;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.camlynplayfordproject.ui.home.Article;
import com.example.camlynplayfordproject.ui.home.ArticleAdapter;
import com.example.camlynplayfordproject.ui.home.RssFeedParser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Add navigation setup here if necessary

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch articles and display RecyclerView
        new FetchArticlesTask().execute("https://www.lemonde.fr/rss/une.xml");
    }

    private class FetchArticlesTask extends AsyncTask<String, Void, List<Article>> {
        @Override
        protected List<Article> doInBackground(String... urls) {
            RssFeedParser parser = new RssFeedParser();
            return parser.fetchAndParse(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            articleAdapter = new ArticleAdapter(articles);
            recyclerView.setAdapter(articleAdapter);
        }
    }
}
