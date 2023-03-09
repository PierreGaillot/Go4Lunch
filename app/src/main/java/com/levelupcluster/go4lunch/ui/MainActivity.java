package com.levelupcluster.go4lunch.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.levelupcluster.go4lunch.R;
import com.levelupcluster.go4lunch.databinding.ActivityMainBinding;
import com.levelupcluster.go4lunch.domain.models.RestaurantDetails;
import com.levelupcluster.go4lunch.ui.listView.ListViewFragmentDirections;
import com.levelupcluster.go4lunch.ui.restaurantView.RestaurantViewFragment;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivityViewModel viewModel;

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int RC_SIGN_IN = 123;
    private ImageView drawerProfileImageView;
    private TextView drawerProfileUserNameTextView;
    private TextView drawerProfileUserMailTextView;
    NavController navController;
    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startSignInActivity();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpNav();
        configureDrawerLayout();
        configureDrawerHeader();
        configureNavigationView();
    }

    public void openRestaurantViewFragment(RestaurantDetails restaurantDetails) {
        Bundle restaurantDetailBundle = new Bundle();
        restaurantDetailBundle.putSerializable("restaurant", (Serializable) restaurantDetails);

        navController.navigate(
                R.id.action_navigation_list_to_navigation_restaurant,
                restaurantDetailBundle,
                new NavOptions.Builder()
                        .setEnterAnim(android.R.animator.fade_in)
                        .setExitAnim(android.R.animator.fade_out)
                        .build());
    }


    private void setUpNav() {
        toolbar = findViewById(R.id.toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder()
                .setOpenableLayout(drawerLayout)
                .build();
        setSupportActionBar(toolbar);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_menu, menu);
        return true;
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.main_act_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_close, R.string.navigation_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureDrawerHeader() {
        viewModel.getHeaderData().observe(this, drawerHeaderUiModel -> {
            if (drawerHeaderUiModel == null) {
                Glide.with(this)
                        .load("")
                        .apply(RequestOptions.circleCropTransform())
                        .into(drawerProfileImageView);
                drawerProfileUserNameTextView.setText("");
                drawerProfileUserMailTextView.setText("");
            } else {
                Glide.with(this)
                        .load(drawerHeaderUiModel.getImageURL())
                        .apply(RequestOptions.circleCropTransform())
                        .into(drawerProfileImageView);
                drawerProfileUserNameTextView.setText(drawerHeaderUiModel.getUserName());
                drawerProfileUserMailTextView.setText(drawerHeaderUiModel.getUserEmail());
            }
        });
    }

    private void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.main_act_nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView drawerBackground = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_imageView);
        drawerBackground.setImageDrawable(getDrawable(R.drawable.go4lunch_bg_square));
        Bitmap backgroundBitmap = ((BitmapDrawable) drawerBackground.getDrawable()).getBitmap();
        Blurry.with(this).from(backgroundBitmap).into(drawerBackground);
        drawerProfileImageView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_profile_imageView);
        drawerProfileUserNameTextView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_userName_textView);
        drawerProfileUserMailTextView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_userMail_textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.reloadCurrentUser();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_menu_restaurant:
                System.out.println("Restaurant");
                break;
            case R.id.drawer_menu_settings:
                System.out.println("Setting");
                break;
            case R.id.drawer_menu_logout:
                System.out.println("Logout");
                viewModel.signOutUser(this);
                startSignInActivity();
                break;
            default:
                break;
        }
        drawerLayout.close();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void startSignInActivity() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers =
                Arrays.asList(
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.FacebookBuilder().build(),
                        new AuthUI.IdpConfig.EmailBuilder().build());

        // Launch the activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.logo_go4lunch)
                        .build(),
                RC_SIGN_IN);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                showToast(getString(R.string.connection_succeed));
                viewModel.createUser();
            } else {
                // ERRORS
                if (response == null) {
                    showToast(getString(R.string.error_authentication_canceled));
                } else if (response.getError() != null) {
                    if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                        showToast(getString(R.string.error_no_internet));
                    } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        showToast(getString(R.string.error_unknown_error));
                    }
                }
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}