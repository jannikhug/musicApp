package ch.zli.jh.mcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                Picasso.get().load(imageUrl).resize(40, 40).
                                        centerCrop().into(imageView1);                            }
                            if (i == 1){
                                hotTrendTV2.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(40, 40).
                                        centerCrop().into(imageView2);
                            }
                            if (i == 3){
                                hotTrendTV3.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(40, 40).
                                        centerCrop().into(imageView3);
                            }
                            if (i == 4){
                                hotTrendTV4.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(40, 40).
                                        centerCrop().into(imageView4);
                            }
                            if (i == 5){
                                hotTrendTV5.append(song + "\n " + artist);
                                Picasso.get().load(imageUrl).resize(40, 40).
                                        centerCrop().into(imageView5);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        mQueue.add(request);
    }
}
