package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class ProfileActivity extends AppCompatActivity {
    private boolean showData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        if (showData = false){
            showButton();
        } else {
            showData();
        }
    }

    public void showButton() {


    }

    public void showData() {

    }
}