package com.example.triviaview.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TriviaService {

    //https://opentdb.com/api/
    //@GET("api") -> api  //path
    //@Query("count") -> ?count=10

    @GET("api")    //getting the path
    Call<List<String>> loadTrivia(@Query("count") int count);
    // RETURN TYPE     METHOD-NAME        METHOD-PARAMETERS

}


