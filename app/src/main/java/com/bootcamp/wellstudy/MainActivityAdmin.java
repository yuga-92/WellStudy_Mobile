package com.bootcamp.wellstudy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bootcamp.wellstudy.api.ServiceGenerator;
import com.bootcamp.wellstudy.fragments.forAdmin.HomeFragment;
import com.bootcamp.wellstudy.fragments.forAdmin.StudentsFragment;

import static com.bootcamp.wellstudy.Constants.USER_PREFERENCES;


public class MainActivityAdmin extends AppCompatActivity {
    private SharedPreferences userPrefs;
    private MenuItem searchItem;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Boolean hideStudentSearch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        setupDrawer();
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("home").commit();
        userPrefs = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_students) {
                    mActivityTitle = "Students";
                    hideStudentSearch = false;
                    fragment = new StudentsFragment();
                } else if (id == R.id.nav_teachers) {
                    hideStudentSearch = true;
                    mActivityTitle = "Teachers";
                } else if (id == R.id.nav_faculties) {
                    hideStudentSearch = true;
                    mActivityTitle = "Faculties";
                } else if (id == R.id.nav_groups) {
                    hideStudentSearch = true;
                    mActivityTitle = "Groups";
                } else if (id == R.id.nav_subjects) {
                    hideStudentSearch = true;
                    mActivityTitle = "Subjects";
                } else if (id == R.id.nav_requests) {
                    hideStudentSearch = true;
                    mActivityTitle = "Requests";
                } else if (id == R.id.nav_schedule) {
                    hideStudentSearch = true;
                    mActivityTitle = "Schedule";
                } else if (id == R.id.nav_home) {
                    hideStudentSearch = true;
                    mActivityTitle = "Home";
                    fragment = new HomeFragment();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).addToBackStack("firstScreen").commit();
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("MainMenu");
                }
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mActivityTitle);
                }
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        if (hideStudentSearch) {
            searchItem.setVisible(false);
        } else {
            searchItem.setVisible(true);
        }
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (count == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return false;
        }
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            userPrefs = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userPrefs.edit();
            editor.remove("Login");
            editor.remove("Password");
            editor.putBoolean("isPrefsSaved", false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            editor.apply();
            ServiceGenerator.setInstanceNull();
        }
        return super.onOptionsItemSelected(item);
    }


}
