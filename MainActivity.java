package com.newsapp.samuel.newsblog;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newsapp.samuel.newsblog.Controller.QueryUtilsClass;
import com.newsapp.samuel.newsblog.Model.NewsBlogAdapterClass;
import com.newsapp.samuel.newsblog.Model.NewsBlogAdapterListClass;
import com.newsapp.samuel.newsblog.Model.NewsBlogObjectClass;

import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private ConnectivityManager connMgr;
    ProgressBar progressBar;
    TextView textView;
    NetworkInfo networkInfo;

     LinearLayoutManager layoutManager;

   ListView lView;
    Context context = MainActivity.this;
    ArrayList<NewsBlogObjectClass> newsBlogList ;
    WebView webView;

    NewsBlogAdapterListClass newsBlogAdapterListClass;
WebSettings webSettings;
//this class is used to load other url links inside a new activity on your webview
WebViewClient webViewClient;
Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);





        newsBlogList = new ArrayList<>();
progressBar = findViewById(R.id.pBar);
textView = findViewById(R.id.tview_error_message);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //the NetworkInfo class gets the current state of the device network connection
        networkInfo = connMgr.getActiveNetworkInfo();
        progressBar.setVisibility(View.INVISIBLE);
        lView = findViewById(R.id.lview);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isConnectedOrConnecting()) {
            Toast.makeText(MainActivity.this, "Stable internet connection", Toast.LENGTH_SHORT).show();

            new NewsAsyncTask().execute(QueryUtilsClass.JSON_RESPONSE);
            progressBar.setVisibility(View.VISIBLE);

        }
        else{
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Oops ! ! No Internet connection");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   /** @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && webView.canGoBack() ){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment;
        int id = item.getItemId();
        switch(id){
            case R.id.nav_home:

                Toast.makeText(context, "Home",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_about:
                Toast.makeText(context, "About",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_contact:
                Toast.makeText(context, "Contact",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_hospital:
                Toast.makeText(context, "Hospital",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Toast.makeText(context, "Share",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_rate:
                Toast.makeText(context, "Rate",Toast.LENGTH_SHORT).show();
                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
    @Override
    public void onFragmentInteraction(Uri uri) {

    }*/

    /** @NonNull
    @Override
    public Loader<ArrayList<NewsBlogObjectClass>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsBlogLoader(MainActivity.this,QueryUtilsClass.JSON_RESPONSE);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<NewsBlogObjectClass>> loader, ArrayList<NewsBlogObjectClass> data) {
        newsBlogAdapterListClass = new NewsBlogAdapterListClass(context,data);
        Toast.makeText(MainActivity.this, "Load Finished", Toast.LENGTH_SHORT).show();
lView.setAdapter(newsBlogAdapterListClass);
       // newsBlogAdapterListClass.notifyDataSetChanged();
       progressBar.setVisibility(View.GONE);



    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<NewsBlogObjectClass>> loader) {
newsBlogAdapterListClass.clear();

    }*/

    /**
     * This class is used for running the  network call on a background thread
     * to avoid ANR(Android Not Responding error)
     * after which the result is passed to the UI thread
     */
   public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<NewsBlogObjectClass>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Load Started", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<NewsBlogObjectClass> newsBlogObjectClasses) {
            super.onPostExecute(newsBlogObjectClasses);
            newsBlogAdapterListClass = new NewsBlogAdapterListClass(context,newsBlogObjectClasses);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final NewsBlogObjectClass newsBlogObjectClass = newsBlogAdapterListClass.getItem(position);
                    webView = findViewById(R.id.wview);
                   // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


                    /**
                     * This code helps to load all our external links inside a
                     * new activity in our WebView
                     * It first checks to see that the url been parsed is not a
                     * local in the webview
                     */
                    webView.setWebViewClient(new WebViewClient(){
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String request) {
        if(Uri.parse(request).getHost().equals(newsBlogObjectClass.getNewsUrlLink())){

        return false;
        }
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(request));
            startActivity(intent);


        return true;
    }
});

                    webView.getTitle();
                    //webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(newsBlogObjectClass.getNewsUrlLink());



                }
            });
            Toast.makeText(MainActivity.this, "Load Finished", Toast.LENGTH_SHORT).show();
            lView.setAdapter(newsBlogAdapterListClass);

           //toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            progressBar.setVisibility(View.GONE);
            //newsBlogAdapterListClass.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<NewsBlogObjectClass> doInBackground(String... strings) {
            ArrayList<NewsBlogObjectClass> newsBlogObjectClasses = null;
            if (QueryUtilsClass.JSON_RESPONSE == null) {
                return null;
            }
            newsBlogObjectClasses = QueryUtilsClass.fetchNewsData(QueryUtilsClass.JSON_RESPONSE);


            return newsBlogObjectClasses;
        }
    }




}
