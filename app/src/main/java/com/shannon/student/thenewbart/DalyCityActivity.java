package com.shannon.student.thenewbart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DalyCityActivity extends AppCompatActivity {

    TextView firstTrainTextView;
    TextView secondTrainTextView;
    TextView thirdTrainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daly_city);
        firstTrainTextView = findViewById(R.id.first_train);
        secondTrainTextView = findViewById(R.id.Second_train);
        thirdTrainTextView = findViewById(R.id.third_train);
    }

    public void checkTimes(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.bart.gov/api/etd.aspx?cmd=etd&orig=ftvl&json=y&key=MW9S-E7SL-26DU-VV8V";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject root = responseJson.getJSONObject("root");
                            JSONArray station = root.getJSONArray("station");
                            JSONObject fruitvale = station.getJSONObject(0);
                            JSONArray etd = fruitvale.getJSONArray("etd");
                            JSONObject dalyCity = etd.getJSONObject(0);
                            JSONArray estimate = dalyCity.getJSONArray("estimate");

                            JSONObject firstTrain = estimate.getJSONObject(0);
                            String firstTrainMinutes = firstTrain.getString("minutes");
                            String firstTrainPlatform = firstTrain.getString("platform");
                            String firstTrainColor = firstTrain.getString("color");

                            JSONObject secondTrain = estimate.getJSONObject(1);
                            String secondTrainMinutes = secondTrain.getString("minutes");
                            String secondTrainPlatform = secondTrain.getString("platform");
                            String secondTrainColor = secondTrain.getString("color");

                            JSONObject thirdTrain = estimate.getJSONObject(2);
                            String thirdTrainMinutes = thirdTrain.getString("minutes");
                            String thirdTrainplatform = thirdTrain.getString("platform");
                            String thirdTrainColor = thirdTrain.getString("color");


                            firstTrainTextView.setText("First Train: " + firstTrainMinutes + "\n" +
                                                       "Platform: " + firstTrainPlatform + "\n" +
                                                       "Train Color: " + firstTrainColor + "\n");

                            secondTrainTextView.setText("Second Train: " + secondTrainMinutes + "\n" +
                                                        "Platform: " + secondTrainPlatform + "\n" +
                                                        "Train Color: " + secondTrainColor + "\n");

                            thirdTrainTextView.setText("Third Train: " + thirdTrainMinutes + "\n" +
                                                        "Platform: " + thirdTrainplatform + "\n" +
                                                        "Train Color: " + thirdTrainColor + "\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                firstTrainTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }
}
