package com.example.triviaview.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //delcare and init our base url

    private static final String BASE_URL="http://jservice.io/";


    //declare retrofit object (create single instance of retrofit
    private  static Retrofit retrofit;

    //create a private constructor
    private RetrofitClientInstance(){  }


    //create public static method to get instance of the retrofit object
    public static Retrofit getRetrofit(){

        //this statement creates a new instance of retrofit if the current instance is null
        if (retrofit == null) {
            retrofit = new  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
