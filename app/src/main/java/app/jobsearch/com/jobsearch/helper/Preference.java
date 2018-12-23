package app.jobsearch.com.jobsearch.helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.model.Education;
import app.jobsearch.com.jobsearch.model.Experience;
import app.jobsearch.com.jobsearch.model.MedicalHistory;
import app.jobsearch.com.jobsearch.model.MedicalInfo;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.model.Schools;
import app.jobsearch.com.jobsearch.model.UserInfo;

public class Preference {

    private SharedPreferences mySharedPreference;

    private SharedPreferences.Editor mySharedEditor;


    public Preference(Context aContext) {

        mySharedPreference = PreferenceManager
                .getDefaultSharedPreferences(aContext);

        mySharedEditor = mySharedPreference.edit();
    }

    public void putUserInfo(UserInfo aUserInfo) {

        Gson gson = new Gson();

        String jsonUsers = gson.toJson(aUserInfo);

        mySharedEditor.putString("USERINFO", jsonUsers);

        mySharedEditor.commit();
    }


    public UserInfo getUserInfo() {

        UserInfo aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("USERINFO", "");

        aItems = aGson.fromJson(aJson, new TypeToken<UserInfo>() {
        }.getType());

        return aItems;
    }

    public void putPos(String aUrl) {
        mySharedEditor.putString("POS", aUrl);
        mySharedEditor.commit();

    }

    public String getPos() {
        return mySharedPreference.getString("POS", "");
    }

    public void putStatus(Boolean aBool) {
        mySharedEditor.putBoolean("STATUS", aBool);
        mySharedEditor.commit();

    }

    public boolean getStatus() {
        return mySharedPreference.getBoolean("STATUS", false);
    }

    public void putQualificaiton(ArrayList<Qualification> aItem) {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(aItem);
        mySharedEditor.putString("EDUC", jsonUsers);
        mySharedEditor.commit();
    }


    public ArrayList<Qualification> getQualificaiton() {

        ArrayList<Qualification> aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("EDUC", "");

        aItems = aGson.fromJson(aJson, new TypeToken<ArrayList<Qualification>>() {
        }.getType());

        return aItems;
    }

    public void putUserID(String aId) {
        mySharedEditor.putString("USERID", aId);
        mySharedEditor.commit();

    }

    public String getUserID() {
        return mySharedPreference.getString("USERID", "");
    }

    public void putQualificatonID(String aId) {
        mySharedEditor.putString("QID", aId);
        mySharedEditor.commit();

    }

    public String getQualificationID() {
        return mySharedPreference.getString("QID", "");
    }

    public void putExpID(String aId) {
        mySharedEditor.putString("EID", aId);
        mySharedEditor.commit();

    }

    public String getExpID() {
        return mySharedPreference.getString("EID", "1");
    }


    public void putProfileImage(String aId) {
        mySharedEditor.putString("ProfileImage", aId);
        mySharedEditor.commit();

    }

    public String getProfileImage() {
        return mySharedPreference.getString("ProfileImage", "");
    }


    public void putProfileLocalImage(String aId) {
        mySharedEditor.putString("putProfileLocalImage", aId);
        mySharedEditor.commit();

    }

    public String getProfileLocalImage() {
        return mySharedPreference.getString("putProfileLocalImage", "");
    }


    public void putMedicalDetails(MedicalHistory aUserInfo) {

        Gson gson = new Gson();

        String jsonUsers = gson.toJson(aUserInfo);

        mySharedEditor.putString("MEDICALINFO", jsonUsers);

        mySharedEditor.commit();
    }


    public MedicalHistory getMedicalDetails() {

        MedicalHistory aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("MEDICALINFO", "");

        aItems = aGson.fromJson(aJson, new TypeToken<MedicalHistory>() {
        }.getType());

        return aItems;
    }

    public void putEducationList(ArrayList<Education> aItem) {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(aItem);
        mySharedEditor.putString("EDUCATIONLIST", jsonUsers);
        mySharedEditor.commit();
    }


    public ArrayList<Education> getEducationList() {

        ArrayList<Education> aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("EDUCATIONLIST", "");

        aItems = aGson.fromJson(aJson, new TypeToken<ArrayList<Education>>() {
        }.getType());

        return aItems;
    }


    public void putExperienceList(ArrayList<Experience> aItem) {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(aItem);
        mySharedEditor.putString("EXPERIENCELIST", jsonUsers);
        mySharedEditor.commit();
    }


    public ArrayList<Experience> getExperienceList() {

        ArrayList<Experience> aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("EXPERIENCELIST", "");

        aItems = aGson.fromJson(aJson, new TypeToken<ArrayList<Experience>>() {
        }.getType());

        return aItems;
    }


    public void putSchoolingList(ArrayList<Schools> aItem) {
        Gson gson = new Gson();
        String jsonUsers = gson.toJson(aItem);
        mySharedEditor.putString("SCHOOLLIST", jsonUsers);
        mySharedEditor.commit();
    }


    public ArrayList<Schools> getSchoolingList() {

        ArrayList<Schools> aItems;

        Gson aGson = new Gson();

        String aJson = mySharedPreference.getString("SCHOOLLIST", "");

        aItems = aGson.fromJson(aJson, new TypeToken<ArrayList<Schools>>() {
        }.getType());

        return aItems;
    }


    public Boolean getResultStatus() {
        return mySharedPreference.getBoolean("RESULT", true);
    }

    public void putResultStatus(Boolean aId) {
        mySharedEditor.putBoolean("RESULT", aId);
        mySharedEditor.commit();

    }
}
