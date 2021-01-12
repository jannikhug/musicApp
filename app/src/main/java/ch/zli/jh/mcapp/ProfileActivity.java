package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {
    private boolean showData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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