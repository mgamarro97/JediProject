package projectjedi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int ACTIVITY_1 = 1;
    public static final int ACTIVITY_2 = 2;
    public static final int ACTIVITY_3 = 3;
    public static final int ACTIVITY_4 = 4;
    public static final String PREFS_NAME = "Settings";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        name = settings.getString("username", "User Not Found!!");
        setView();
    }

    protected void setView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
   public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.activity1:
                startActivity(new Intent(getApplicationContext(),Memory.class));
                finish();
                break;
            case R.id.activity2:
                startActivity(new Intent(getApplicationContext(),Calc.class));
                finish();
                break;
            case R.id.activity3:
                startActivity(new Intent(getApplicationContext(),Media.class));
                finish();
                break;
            case R.id.activity4:
                startActivity(new Intent(getApplicationContext(),Profile.class));
                finish();
                break;
            case R.id.logout:
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("userlogged", false);

                editor.commit();

                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {

        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) fullLayout.findViewById(R.id.frame_layout_base);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(fullLayout);
        setView();
    }

    public void checkMenuItem(int activity) {
        if (activity == ACTIVITY_1) navigationView.setCheckedItem(R.id.activity1);
        if (activity == ACTIVITY_2) navigationView.setCheckedItem(R.id.activity2);
        if (activity == ACTIVITY_3) navigationView.setCheckedItem(R.id.activity3);
        if (activity == ACTIVITY_4) navigationView.setCheckedItem(R.id.activity4);
    }
}
