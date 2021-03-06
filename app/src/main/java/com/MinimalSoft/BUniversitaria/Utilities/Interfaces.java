package com.MinimalSoft.BUniversitaria.Utilities;

import com.MinimalSoft.BUniversitaria.Models.AllReviewsResponse;
import com.MinimalSoft.BUniversitaria.Models.DatalessResponse;
import com.MinimalSoft.BUniversitaria.Models.DetailsResponse;
import com.MinimalSoft.BUniversitaria.Models.Ecobici_Stop;
import com.MinimalSoft.BUniversitaria.Models.PlacesResponse;
import com.MinimalSoft.BUniversitaria.Models.ReactionResponse;
import com.MinimalSoft.BUniversitaria.Models.Response_General;
import com.MinimalSoft.BUniversitaria.Models.ReviewsResponse;
import com.MinimalSoft.BUniversitaria.Models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Interfaces {

    @GET("/stations/all")
    Call<List<Ecobici_Stop>> getAllStations();

    @GET("station/{id}")
    Call<List<Ecobici_Stop>> getStationById(@Path("id") String id);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReactionResponse> reaction(@Field("action") String action,
                                    @Field("idUser") String idUser,
                                    @Field("idReview") String idReview,
                                    @Field("idReaction") String idReaction);

    @FormUrlEncoded
    @POST("/controllers/place/place.php")
    Call<PlacesResponse> getPlaces(@Field("action") String action,
                                   @Field("idType") String idType,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude,
                                   @Field("radio") String radio);

    @FormUrlEncoded
    @POST("/controllers/user/user")
    Call<UserResponse> logInUser(@Field("action") String action,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("idFacebook") String idFacebook,
                                 @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @POST("/controllers/user/user")
    Call<UserResponse> registerUser(@Field("action") String action,
                                    @Field("name") String name,
                                    @Field("lastName") String lastName,
                                    @Field("gender") String gender,
                                    @Field("birthday") String birthday,
                                    @Field("phone") String phone,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("fbImage") String urlImage,
                                    @Field("idFacebook") String idFacebook,
                                    @Field("deviceToken") String deviceToken);

    @FormUrlEncoded
    @POST("/controllers/place/place")
    Call<Response_General> registerPlace(@Field("action") String action,
                                         @Field("idType") String idType,
                                         @Field("idUser") String idUser,
                                         @Field("placeName") String placeName,
                                         @Field("street") String street,
                                         @Field("number") String number,
                                         @Field("neighborhood") String neighborhood,
                                         @Field("county") String county,
                                         @Field("state") String state,
                                         @Field("country") String country,
                                         @Field("zip") String zip,
                                         @Field("description") String description,
                                         @Field("latitude") String latitude,
                                         @Field("longitude") String longitude,
                                         @Field("name") String name,
                                         @Field("phone1") String phone1,
                                         @Field("email") String email,
                                         @Field("idPackage") String idPackage);

    @FormUrlEncoded
    @POST("/controllers/reviews/review")
    Call<DatalessResponse> putReview(@Field("action") String action,
                                     @Field("idUser") String idUser,
                                     @Field("idPlace") String idPlace,
                                     @Field("text") String text,
                                     @Field("stars") String stars);

    @FormUrlEncoded
    @POST("/controllers/reviews/review")
    Call<Response_General> writeReview(@Field("action") String action,
                                       @Field("idUser") String idUser,
                                       @Field("idPlace") String idPlace,
                                       @Field("text") String text,
                                       @Field("stars") String stars);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<ReviewsResponse> getReviews(@Field("action") String action,
                                     @Field("idUser") String idUser,
                                     @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/controllers/place/place.php")
    Call<DetailsResponse> getPlaceDetails(@Field("action") String action,
                                          @Field("idPlace") String idPlace);

    @FormUrlEncoded
    @POST("/controllers/reviews/review.php")
    Call<AllReviewsResponse> getAllReviews(@Field("action") String action,
                                           @Field("idUser") String idUser,
                                           @Field("number") String number);

    @FormUrlEncoded
    @POST("/app/controllers/user/user.php")
    Call<Response_General> getPromos(@Field("action") String action,
                                     @Field("idType") String idType);

}