package com.example.cse110project.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;

@Entity(tableName = "users")
public class User {
    // User UIDs are going to likely be the public_code we use for our primary key
    @PrimaryKey
    @SerializedName("public_code")
    @NonNull
    public String public_code;


    // The Users private code used when we need to update their own location
    // Might actually not want to include this in all Users since the api never
    // returns a private code
    @SerializedName("private_code")
    public String private_code;

    // A label to add when we PUT locations
    @SerializedName("label")
    @NonNull
    public String label;

    // The latitude of the User
    @SerializedName("latitude")
    @NonNull
    public float latitude;

    // The longitude of the User
    @SerializedName("longitude")
    @NonNull
    public float longitude;

    // Initial date and time of when User location first created
    @SerializedName("created_at")
    @NonNull
    public String created_at;

    // date and time of when User location updated
    @SerializedName("updated_at")
    @NonNull
    public String updated_at;

    // General POJO constructor for the User
    public User(@NonNull String public_code, String private_code, @NonNull String label,
                @NonNull float latitude, @NonNull float longitude) {
        this.public_code = public_code;
        this.private_code = private_code;
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = Instant.now().toString();
        this.updated_at = Instant.now().toString();
    }

    public static User fromJSON(String json) { return new Gson().fromJson(json, User.class); }

    public String toJSON() { return new Gson().toJson(this); }
}
