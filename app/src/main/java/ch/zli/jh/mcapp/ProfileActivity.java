package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ch.zli.jh.mcapp.model.Profile;

public class ProfileActivity extends AppCompatActivity implements Serializable {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    TextView fullname;
    TextView username;
    TextView artistName;
    TextView artistSongs;
    TextView albumName;
    TextView twitter;
    TextView facebook;
    ImageView profilePic;
    ImageView artistPic;
    ImageView albumPic;
    Button takephoto;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        fullname = findViewById(R.id.fullName);
        username = findViewById(R.id.username);
        artistName = findViewById(R.id.artistName);
        artistSongs = findViewById(R.id.artistSongs);
        albumName = findViewById(R.id.albumName);
        twitter = findViewById(R.id.twitter);
        facebook = findViewById(R.id.facebook);
        profilePic = findViewById(R.id.profileImage);
        artistPic = findViewById(R.id.artistImage);
        albumPic = findViewById(R.id.albumImage);
        mQueue = Volley.newRequestQueue(this);


        Button goToCreate = findViewById(R.id.goToCreate);
        goToCreate.setOnClickListener(view -> {
            Intent goToProfileIntent = new Intent(this, CreateActivity.class);
            startActivity(goToProfileIntent);
        });

        takephoto = findViewById(R.id.takePhoto);
        takephoto.setOnClickListener(view -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Kamera konnte nicht geöffnet werden.", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i = getIntent();
        Profile profile = (Profile) i.getSerializableExtra("MyProfile");
        if (profile == null) {
            goToCreate.setText("Create Profile");
        } else {
            goToCreate.setText("Edit Profile");
            takephoto.setVisibility(View.VISIBLE);
            showData(profile);
        }
    }

    public void showData(Profile profile) {

        fullname.setText(profile.getFullName());
        username.setText(profile.getUsername());
        artistName.setText(profile.getFavArtist());
        albumName.setText(profile.getFavAlbum());
        loadArtistData(profile);
        loadAlbumData(profile);
        loadSongData(profile);
    }

    public void loadArtistData(Profile profile) {
        String url = "https://theaudiodb.com/api/v1/json/1/search.php?s=" + profile.getFavArtist();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("artists");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject artists = jsonArray.getJSONObject(i);
                            String twitterLink = artists.getString("strTwitter");
                            String facebookLink = artists.getString("strFacebook");
                            String imageUrl = artists.getString("strArtistThumb");

                            twitter.setText(twitterLink);
                            facebook.setText(facebookLink);
                            if (i == 0) {
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(artistPic);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    private void loadAlbumData(Profile profile) {
        String url = "https://theaudiodb.com/api/v1/json/1/searchalbum.php?a=" + profile.getFavAlbum();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("album");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject albums = jsonArray.getJSONObject(i);
                            String imageUrl = albums.getString("strAlbumThumb");

                            if (i == 0) {
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(albumPic);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    private void loadSongData(Profile profile) {
        String url = "https://theaudiodb.com/api/v1/json/523532/track-top10.php?s=" + profile.getFavArtist();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("track");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject albums = jsonArray.getJSONObject(i);
                            String songName = albums.getString("strTrack");

                            artistSongs.append("- " + songName + "\n ");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePic.setImageBitmap(imageBitmap);
        }
    }
}