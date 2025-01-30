package com.example.camlynplayfordproject;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.camlynplayfordproject.databinding.ActivityMainBinding;
//import com.example.camlynplayfordproject.ui.home.Article;
//import com.example.camlynplayfordproject.ui.home.ArticleAdapter;
//import com.example.camlynplayfordproject.ui.home.RssFeedParser;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
