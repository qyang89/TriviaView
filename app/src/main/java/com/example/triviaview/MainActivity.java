package com.example.triviaview;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triviaview.retrofit.RetrofitClientInstance;
import com.example.triviaview.retrofit.TriviaService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TriviaAdapter triviaAdapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.rv_list);
       // recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        Toast.makeText(this, "oncreate toast", Toast.LENGTH_SHORT).show();
        retrofitRequest(10);
    }

    public void retrofitRequest(int count){
        //1. declare triviaservice and init it using retrofitclientintance

        TriviaService triviaService = RetrofitClientInstance
                .getRetrofit().create(TriviaService.class);

        Call<List<String>> triviaCall  = triviaService.loadTrivia(count);

        triviaCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Success");
                    loadRecyclerView(response.body());

                }else{
                    Log.d(TAG, "onResponse: Failure");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +t.getLocalizedMessage());

            }
        });


    }

    private void loadRecyclerView(List<String> strings) {
        triviaAdapter = new TriviaAdapter(strings);
        recyclerView.setAdapter(triviaAdapter);

    }

    class TriviaTask extends AsyncTask<String, Void, List<String>> {


        @Override
        protected List<String> doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;

            //declare and init vars
            String baseUrl = "https://opentdb.com/api?";
            String query = "count=" + strings[0];
            String count = strings[0];
            //this will hold all the json
            StringBuilder result = new StringBuilder();  //this is immutable, cannot change.

            try {
                //create a URL object, passing the url string into the constructor
                URL url = new URL(baseUrl + query);


                //use the url object instance to create an internet connection
                httpURLConnection = (HttpURLConnection) url.openConnection();

                //create an InputStream instance and init it with a BufferedInputStream
                //then pass the stream from the httpURLConnection instance
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                //declare inputstreamreader and init with inputstream
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                //declare  bufferedreader object and init it with the inputstreamreader
                BufferedReader reader = new BufferedReader(inputStreamReader);


                //variable to hold each line from the reader
                String line;

                //read each line from the bufferedreader object and append it into our result(StringBuilder)
                while ((line = reader.readLine()) != null) {
                    //if line is not null, append to result
                    result.append(line);
                }
                // String count = strings[0];  //index on arraylist
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //important to close the connection when done
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
            }

            Log.d(TAG, "doInBackground: " + result);

            //   Log.d(TAG, "testing background");


            //Convert string(json) into List<String>
            String removeBrackets = result.substring(1, result.length() - 1);
            String removeQuotes = removeBrackets.replace("\"", "");
            String[] urls = removeQuotes.split(",");

            List<String> url = Arrays.asList(urls);
            // for (int i =0; i < urls.length;i++) {

            //  }

            Log.d(TAG, "doInBackground: urls count =" + url.size());
            return url;

        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            loadRecyclerView(strings);

        }


    }


}

