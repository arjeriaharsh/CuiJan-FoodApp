package com.harsharjeria.cuijan.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.harsharjeria.cuijan.Fragments.fragment_home;
import com.harsharjeria.cuijan.Fragments.fragment_profile;
import com.harsharjeria.cuijan.Fragments.fragment_search;
import com.harsharjeria.cuijan.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import maes.tech.intentanim.CustomIntent;

public class HomePage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private Fragment firstFragment = new fragment_home();
    private Fragment secondFragment= new fragment_search();
    private Fragment thirdFragment = new fragment_profile();
    private Fragment active = firstFragment;
    private BottomNavigationView navigation;


    final FragmentManager fragment = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(this);

        fragment.beginTransaction().add(R.id.fragment_container, thirdFragment, "3")
                .hide(thirdFragment).commit();
        fragment.beginTransaction().add(R.id.fragment_container, secondFragment, "2")
                .hide(secondFragment).commit();
        fragment.beginTransaction().add(R.id.fragment_container, firstFragment, "1").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_search) {
            fragment.beginTransaction().hide(active).show(secondFragment).commit();
            active = secondFragment;
            navigation.getMenu().findItem(R.id.menu_search).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment.beginTransaction().hide(active).show(firstFragment).commit();
                active = firstFragment;
                break;
            case R.id.menu_profile:
                fragment.beginTransaction().hide(active).show(thirdFragment).commit();
                active = thirdFragment;
                break;
            case R.id.menu_search:
                fragment.beginTransaction().hide(active).show(secondFragment).commit();
                active = secondFragment;
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

}
