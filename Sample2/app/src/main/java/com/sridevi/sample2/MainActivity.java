package com.sridevi.sample2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sridevi.sample2.customlist.CustomListFragment;
import com.sridevi.sample2.utils.AboutDialog;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = CustomListFragment.newInstance();
        Bundle args = new Bundle();
        args.putString("contentXML", "content.xml");
        fragment.setArguments(args);
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_button:
                break;
            case R.id.about_button:
                AboutDialog.show(this);
                break;
            case R.id.activity_button:
                Intent intent = new Intent(this, com.sridevi.sample2.AnotherActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

}
