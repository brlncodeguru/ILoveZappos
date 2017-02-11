package com.example.lakshminarayanabr.ilovezappos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.apptik.multiview.scrollers.FlexiSmoothScroller;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.security.AccessController.getContext;

public class SearchResultsActivity extends AppCompatActivity {

    RecyclerView rvProduct;
    ArrayList<Product> productArrayList;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        rvProduct.setItemAnimator(new SlideInLeftAnimator());
        rvProduct.getItemAnimator().setAddDuration(1000);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        textView=(TextView)findViewById(R.id.txtError);
        textView.setVisibility(View.INVISIBLE);


        handleIntent(getIntent());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusettings, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MenuItem item = menu.findItem(R.id.action_cart);

        item.setVisible(true);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Log.d("Demo",query);
            fetchProducts(query);


        }
    }
    public void fetchProducts(String query)
    {
        productArrayList=new ArrayList<>();
        String key="b743e26728e16b81da139182bb2094357c31d331";
        RestAdapter adapter=new RestAdapter.Builder().setEndpoint("https://api.zappos.com").build();
        GetProductsAPI getProductsAPI=adapter.create(GetProductsAPI.class);
        getProductsAPI.getProductsFromAPI(query, key, new Callback<ProductData>() {
            @Override
            public void success(ProductData productData, Response response) {
                Log.d("Search",productData.toString());

                    productArrayList.addAll(productData.getResults());

                if(productArrayList.isEmpty())
                {
                    rvProduct.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                else
                {
                    ProductAdapter adapter = new ProductAdapter(productArrayList, SearchResultsActivity.this,rvProduct);
                    adapter.isCart=false;
                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                    alphaAdapter.setFirstOnly(false);
                    alphaAdapter.setDuration(1000);


                    rvProduct.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));


                }



            }





            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),
                        "Unexpected error occured",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_cart:
                ArrayList<Product> cartItems= (ArrayList<Product>) getCartItems(SearchResultsActivity.this);
                if(cartItems.size()>0)
                {
                    ProductAdapter adapter = new ProductAdapter(cartItems, SearchResultsActivity.this,rvProduct);
                    adapter.isCart=false;

                    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
                    alphaAdapter.setFirstOnly(false);
                    alphaAdapter.setDuration(1000);

                    rvProduct.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));


                }
                else
                {
                    Toast.makeText(SearchResultsActivity.this,"No Items in cart",Toast.LENGTH_LONG).show();
                }
                return true;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
      Intent  intent = new Intent(SearchResultsActivity.this , MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    public void addCartItems(Product item, Context context) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("ZAPP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Product> products=new ArrayList<>();
        products= (ArrayList<Product>) getCartItems(context);

        products.add(item);
        Gson gson = new Gson();
        ArrayList<String> stringArrayList=new ArrayList<>();

        for(Product p:products)
        {
            String convert=gson.toJson(p);
            stringArrayList.add(convert);
        }
        String convert=gson.toJson(stringArrayList);

        editor.putString("CART",convert);





        if(editor.commit())
        {
            Toast.makeText(SearchResultsActivity.this,"Cart Items saved",Toast.LENGTH_LONG).show();

        }




    }
    public void saveCartItems(List<Product> cartItems, Context context) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("ZAPP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        ArrayList<String> stringArrayList=new ArrayList<>();

        for(Product p:cartItems)
        {
            String convert=gson.toJson(p);
            stringArrayList.add(convert);
        }
        String convert=gson.toJson(stringArrayList);

        editor.putString("CART",convert);





        if(editor.commit())
        {
            Toast.makeText(SearchResultsActivity.this,"Cart Items saved",Toast.LENGTH_LONG).show();
        }


    }


    public List<Product> getCartItems(Context context) {
        ArrayList<Product> products = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ZAPP", MODE_PRIVATE);
        String edited = sharedPreferences.getString("CART", "No Items Available");
        Gson gson = new Gson();
        String convertedAgain = edited;
        ArrayList<String> strings;


        if (convertedAgain.equalsIgnoreCase("No Items Available")) {
            strings = new ArrayList<>();
        } else {
            strings = gson.fromJson(convertedAgain, new TypeToken<ArrayList<String>>() {

            }.getType());

        }
        for (int i = 0; i < strings.size(); i++) {
            Product product = gson.fromJson(strings.get(i), Product.class);
            products.add(product);


        }
        return products;
    }
}
