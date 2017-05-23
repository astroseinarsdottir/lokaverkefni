package com.astrosei.lokaverkefni;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.astrosei.lokaverkefni.persistence.entities.Discount;
import com.astrosei.lokaverkefni.persistence.repositories.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    // For navigation drawer.
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btn_categories;

    // For expandable list view
    private ExpandableListView expandableListView;
    private DiscountExpandableListAdapter adapter;
    private List<Discount> discountList;

    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        // Initialize variables.
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        btn_categories = (Button)findViewById(R.id.btn_categories);
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);

        // Set the category as the chosen category, otherwise default.
        if(getIntent().getStringExtra("category")!=null){
            category = getIntent().getStringExtra("category");
        }
        else{
            category = getResources().getString(R.string.starting_category);
        }

        // Get list of discounts.
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Discount> discounts = databaseHelper.getDiscounts(category);

        // Get discounts and put then into the view
        initListData(discounts);

        // Put the category into view
        int id = getCategoryImage(category);
        btn_categories.setBackgroundResource(id);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.discount_activity);

        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Set Discount as checked, so it is more obvious where you are.
        navigationView.getMenu().getItem(0).setChecked(true);

        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.discounts:
                        // Are already in this view.
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.about:
                        // Collapse all groups
                        collapseAllGroups();
                        // Go to AboutActivity
                        intent = new Intent(DiscountActivity.this, AboutActivity.class);
                        intent.putExtra("category",category);
                        startActivity(intent);
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
                collapseAllGroups();
                Intent intent = new Intent(DiscountActivity.this,CategoriesActivity.class);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });

        // Make so that only one list can be open at once.
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // Keep track of previous expanded parent
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                // Collapse previous parent if expanded.
                if((previousGroup!=-1)&&(groupPosition!=previousGroup)){
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
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
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    // For the expandableListView.
    public void initListData(List<Discount> discounts){
        discountList = new ArrayList<>();
        // Dummy data
        for(int i = 0; i < discounts.size(); i++){
            discountList.add(discounts.get(i));
        }

        adapter = new DiscountExpandableListAdapter(this,discountList);
        expandableListView.setAdapter(adapter);
    }

    // Collapse all groups when user goes to another activity.
    public void collapseAllGroups(){
        for(int i=0; i < adapter.getGroupCount(); i++ ){
            expandableListView.collapseGroup(i);
        }

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
