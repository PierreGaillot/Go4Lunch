package com.levelupcluster.go4lunch.ui;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.levelupcluster.go4lunch.domain.models.User;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MainActivityViewModel viewModel;

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int RC_SIGN_IN = 123;
    ImageView drawerProfileImageView;
    TextView drawerProfileUserNameTextView;
    TextView drawerProfileUserMailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startSignInActivity();

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setUpToolbar();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        configureDrawerLayout();
        configureNavigationView();
        configureDrawerHeader();
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            }else{
                Glide.with(this)
                        .load(drawerHeaderUiModel.getImageURL())
                        .apply(RequestOptions.circleCropTransform())
                        .into(drawerProfileImageView);
                drawerProfileUserNameTextView.setText(drawerHeaderUiModel.getUserName());
                drawerProfileUserMailTextView.setText(drawerHeaderUiModel.getUserEmail());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.reloadCurrentUser();
    }

    private void configureNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.main_act_nav_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_imageView);
        drawerProfileImageView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_profile_imageView);
        drawerProfileUserNameTextView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_userName_textView);
        drawerProfileUserMailTextView = navigationView.getHeaderView(0).findViewById(R.id.drawer_header_userMail_textView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imageView.setRenderEffect(RenderEffect.createBlurEffect(20, 20, Shader.TileMode.MIRROR));
        }
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