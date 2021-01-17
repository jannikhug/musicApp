package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import java.io.Serializable;

import ch.zli.jh.mcapp.model.Profile;

public class ProfileActivity extends AppCompatActivity implements Serializable {
    private boolean showData;

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


        Intent i = getIntent();
        Profile profile = (Profile) i.getSerializableExtra("MyProfile");
        if (profile == null){
            goToCreate.setText("Create Profile");
        } else {
            goToCreate.setText("Edit Profile");
            showData(profile);
        }
    }

    public void showData(Profile profile) {
        System.out.println(profile.getFullName());
        System.out.println(profile.getUsername());
        System.out.println(profile.getFavArtist());
        System.out.println(profile.getFavAlbum());
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }
}