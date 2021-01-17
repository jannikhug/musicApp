package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import ch.zli.jh.mcapp.model.Profile;

public class CreateActivity extends AppCompatActivity implements Serializable {

    private EditText fullName;
    private EditText username;
    private EditText favArtist;
    private EditText favAlbum;
    private RequestQueue mQueue;
    private boolean foundArtist;
    private boolean foundAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().hide();

        fullName = findViewById(R.id.etFullName);
        username = findViewById(R.id.etUsername);
        favArtist = findViewById(R.id.etFavArtist);
        favAlbum = findViewById(R.id.etFavAlbum);
        mQueue = Volley.newRequestQueue(this);




            Button goToProfile = findViewById(R.id.createProfile);
            goToProfile.setOnClickListener(view -> {
                if (checkArtist(favArtist.getText().toString()) && checkAlbum(favAlbum.getText().toString())) {
                    Profile profile = new Profile(fullName.getText().toString(), username.getText().toString(), favArtist.getText().toString(), favAlbum.getText().toString());
                    Intent goToProfileIntent = new Intent(this, ProfileActivity.class);
                    goToProfileIntent.putExtra("MyProfile", profile);
                    startActivity(goToProfileIntent);
                } else {
                    Toast.makeText(getApplicationContext(),"Click three times or Album and/or Artist not found.", Toast.LENGTH_SHORT).show();
                }
            });
    }


    private boolean checkArtist(String artist) {
        String url = "https://theaudiodb.com/api/v1/json/1/search.php?s=" + artist;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("artists");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject artists = jsonArray.getJSONObject(i);
                            String artistJson = artists.getString("strArtist");
                            System.out.println(artistJson);

                            foundArtist = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        foundArtist = false;

                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
        return foundArtist;
    }

    private boolean checkAlbum(String album) {
        String url = "https://theaudiodb.com/api/v1/json/1/searchalbum.php?a=" + album;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("album");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject albums = jsonArray.getJSONObject(i);
                            String albumJson = albums.getString("strAlbum");
                            System.out.println(albumJson);
                            foundAlbum = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        foundAlbum = false;
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
        return foundAlbum;
    }
}