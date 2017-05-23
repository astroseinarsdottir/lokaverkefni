package com.astrosei.lokaverkefni;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    // For navigation drawer.
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For listView
    private ListView listView;
    private CategoriesAdapter adapter;
    private String[] categories;
    private int[] images;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Initialize variables.
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.categories_toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        listView = (ListView)findViewById(R.id.listView);

        // Add data into listView
        initListData();

        // Set the category as the chosen category, otherwise default.
        if(getIntent().getStringExtra("category")!=null){
            category = getIntent().getStringExtra("category");
        }
        else{
            category = getResources().getString(R.string.starting_category);
        }

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.categories_activity);

        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.discounts:
                        // Go to DiscountActivity
                        intent = new Intent(CategoriesActivity.this,DiscountActivity.class);
                        intent.putExtra("category",category);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.about:
                        // Go to AboutActivity
                        intent = new Intent(CategoriesActivity.this, AboutActivity.class);
                        intent.putExtra("category",category);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // Change the category and redirect to DiscountActivity when user chooses category.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                category = adapter.getItem(position);

                Intent intent = new Intent(CategoriesActivity.this,DiscountActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });

    }
    // For the navigation menu.
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    // Get our discount list and put it into view.
    public void initListData(){
        // Get text.
        Resources resources = getResources();
        categories = resources.getStringArray(R.array.categories);


        // Get images.
        images = new int[]{R.drawable.food,R.drawable.clothes,R.drawable.entertainment,R.drawable.lifestyle
                ,R.drawable.technology,R.drawable.other, R.drawable.everything};

        // Set list into view.
        adapter = new CategoriesAdapter(this,categories,images);
        listView.setAdapter(adapter);

    }
}
