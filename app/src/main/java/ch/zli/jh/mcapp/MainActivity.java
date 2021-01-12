package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView hotTrendTV;
    private TextView hotTrendTV2;
    private TextView hotTrendTV3;
    private TextView hotTrendTV4;
    private TextView hotTrendTV5;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private RequestQueue mQueue;
    private Button goToProfile;
    private static final String CHANNEL_ID = "xyzChannel";
    private static final String CHANNEL_Name = "xyz Channel";

    private NotificationManager nManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToProfile = findViewById(R.id.goToProfile);
        goToProfile.setOnClickListener(view -> {
            Intent goToProfileIntent = new Intent(this, ProfileActivity.class);
            startActivity(goToProfileIntent);
            showNotification();
        });

        this.nManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
        nManager.createNotificationChannel(channel);

        hotTrendTV = findViewById(R.id.hotTrend);
        hotTrendTV2 = findViewById(R.id.hotTrend2);
        hotTrendTV3 = findViewById(R.id.hotTrend3);
        hotTrendTV4 = findViewById(R.id.hotTrend4);
        hotTrendTV5 = findViewById(R.id.hotTrend5);
        imageView1 = findViewById(R.id.iv1);
        imageView2 = findViewById(R.id.iv2);
        imageView3 = findViewById(R.id.iv3);
        imageView4 = findViewById(R.id.iv4);
        imageView5 = findViewById(R.id.iv5);
        mQueue = Volley.newRequestQueue(this);
        loadHotTrend();
    }



    // ApiKey = 523532
    public void loadHotTrend(){
        String url = "https://theaudiodb.com/api/v1/json/523532/trending.php?country=us&type=itunes&format=singles";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("trending");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hotTrend = jsonArray.getJSONObject(i);
                            String artist = hotTrend.getString("strArtist");
                            String song = hotTrend.getString("strTrack");
                            String imageUrl = hotTrend.getString("strTrackThumb");

                            if (i == 0){
                                hotTrendTV.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(imageView1);                            }
                            if (i == 1){
                                hotTrendTV2.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(imageView2);
                            }
                            if (i == 3){
                                hotTrendTV3.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(imageView3);
                            }
                            if (i == 4){
                                hotTrendTV4.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(imageView4);
                            }
                            if (i == 5){
                                hotTrendTV5.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(25, 25).
                                        centerCrop().into(imageView5);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }

    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_fire)
                .setContentTitle("Check out the News!!!")
                .setContentText("Check out the new tweets and post from your favourite Artist.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        nManager.notify(0, builder.build());



    }
}
