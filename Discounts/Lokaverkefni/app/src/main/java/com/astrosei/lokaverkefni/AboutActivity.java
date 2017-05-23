package com.astrosei.lokaverkefni;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    // For navigation drawer.
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btn_categories;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialize variables.
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        btn_categories = (Button)findViewById(R.id.btn_categories);


        // Set the category as the chosen category, otherwise default.
        if(getIntent().getStringExtra("category")!=null){
            category = getIntent().getStringExtra("category");
        }
        else{
            category = getResources().getString(R.string.starting_category);
        }

        // Put the category into view
        int id = getCategoryImage(category);
        btn_categories.setBackgroundResource(id);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.about);

        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Set About as checked, so it is more obvious where you are.
        navigationView.getMenu().getItem(1).setChecked(true);

        // Set About as checked, so it is more obvious where you are.
        navigationView.getMenu().getItem(1).setChecked(true);

        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.discounts:
                        // Go to DiscountActivity
                        intent = new Intent(AboutActivity.this,DiscountActivity.class);
                        intent.putExtra("category",category);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.about:
                        // Are already in this view.
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // Go to CategoriesActivy
        btn_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,CategoriesActivity.class);
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
    @Override
    protected void onStart() {
        super.onStart();
        navigationView.getMenu().getItem(1).setChecked(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(1).setChecked(true);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.getMenu().getItem(1).setChecked(true);
    }
    // Gets the right category image for the header.
    public int getCategoryImage(String category){
        int id = 0;

        if(category.equals(getResources().getString(R.string.food))){
            id = getResources().getIdentifier("drawable/food",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.clothes))){
            id = getResources().getIdentifier("drawable/clothes",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.entertainment))){
            id = getResources().getIdentifier("drawable/entertainment",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.lifestyle))){
            id = getResources().getIdentifier("drawable/lifestyle",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.technology))){
            id = getResources().getIdentifier("drawable/technology",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.other))){
            id = getResources().getIdentifier("drawable/other",null, getPackageName());
        }
        if(category.equals(getResources().getString(R.string.starting_category))){
            id = getResources().getIdentifier("drawable/everything",null, getPackageName());
        }
        return id;

    }
}
