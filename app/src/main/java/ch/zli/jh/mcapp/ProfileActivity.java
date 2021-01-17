package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import java.io.Serializable;

public class ProfileActivity extends AppCompatActivity implements Serializable {
    private boolean showData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        Button goToCreate = findViewById(R.id.goToCreate);
        goToCreate.setOnClickListener(view -> {
            Intent goToProfileIntent = new Intent(this, CreateActivity.class);
            startActivity(goToProfileIntent);
        });


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

    public void setShowData(boolean showData) {
        this.showData = showData;
    }
}