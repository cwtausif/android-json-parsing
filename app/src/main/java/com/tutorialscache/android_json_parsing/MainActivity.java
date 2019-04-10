package com.tutorialscache.android_json_parsing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TextView retryTv;
    MoviesJSONAdapter moviesJSONAdapter;
    ArrayList<MoviesModel> moviesJsonData;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);

        initialize();

        if (GlobalClass.isNetowrk()){
            requestData();
            retryTv.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(context,getString(R.string.network_error),Toast.LENGTH_SHORT).show();
            retryTv.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }



    private void initialize() {
        listView = findViewById(R.id.listView);
        retryTv = findViewById(R.id.retryTv);
        progressBar = findViewById(R.id.pBr);
        moviesJsonData=new ArrayList<>();
        moviesJSONAdapter = new MoviesJSONAdapter(context,moviesJsonData);
        listView.setAdapter(moviesJSONAdapter);
    }


    private void requestData() {

        RequestParams requestParams = new RequestParams();
        WebReq.get(context,"movies.json",requestParams,new MainActivity.ResponseHandler());
    }


    public class ResponseHandler extends JsonHttpResponseHandler {

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            // response failure
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            //clear all previous data if any
            moviesJsonData.clear();

            Log.d("response ",response.toString());
            //check status of the response
            try {
                if (response.getBoolean("status")){
                    // get movies array from response object
                    JSONArray movies = response.getJSONArray("movies");

                    //check whether movies array is not empty
                    if (movies.length()>0){

                        //loop through movies array and add into moviesJsonData
                        for (int i = 0; i < movies.length(); i++) {
                            //get each movie object
                            JSONObject movie = movies.getJSONObject(i);

                            //get each movie Object Data

                            MoviesModel moviesModel = new MoviesModel(movie.getInt("id"),movie.getString("name"),movie.getString("image"),movie.getDouble("ratting"));
                            moviesModel.toString();

                            //add object to moviesJsonData array
                            moviesJsonData.add(moviesModel);
                        }

                        //Now notify adapter with new data
                        moviesJSONAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,getString(R.string.no_movies),Toast.LENGTH_SHORT).show();
                        retryTv.setVisibility(View.VISIBLE);
                    }

                }else{
                    Toast.makeText(context,getString(R.string.no_status_false),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            //hide progress bar
            progressBar.setVisibility(View.GONE);
            super.onFinish();
        }
    }
}
