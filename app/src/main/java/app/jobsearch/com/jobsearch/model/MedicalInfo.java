package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalInfo {

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("EyePower")
    @Expose
    private String EyePower = "";

    @SerializedName("BloodGroup")
    @Expose
    private String BloodGroup = "";

    @SerializedName("Weight")
    @Expose
    private String Weight = "";

    @SerializedName("Height")
    @Expose
    private String Height = "";

    @SerializedName("EyeColor")
    @Expose
    private String EyeColor = "";

    @SerializedName("NailSymptom")
    @Expose
    private String NailSymptom = "";

    @SerializedName("BloodPressure")
    @Expose
    private String BloodPressure = "";

    @SerializedName("Sugar")
    @Expose
    private String Sugar = "";

    @SerializedName("Cancer")
    @Expose
    private String Cancer = "";

    @SerializedName("Hearing")
    @Expose
    private String Hearing = "";

    @SerializedName("Thyroid")
    @Expose
    private String Thyroid = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEyePower() {
        return EyePower;
    }

    public void setEyePower(String eyePower) {
        EyePower = eyePower;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getEyeColor() {
        return EyeColor;
    }

    public void setEyeColor(String eyeColor) {
        EyeColor = eyeColor;
    }

    public String getNailSymptom() {
        return NailSymptom;
    }

    public void setNailSymptom(String nailSymptom) {
        NailSymptom = nailSymptom;
    }

    public String getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        BloodPressure = bloodPressure;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getCancer() {
        return Cancer;
    }

    public void setCancer(String cancer) {
        Cancer = cancer;
    }

    public String getHearing() {
        return Hearing;
    }

    public void setHearing(String hearing) {
        Hearing = hearing;
    }

    public String getThyroid() {
        return Thyroid;
    }

    public void setThyroid(String thyroid) {
        Thyroid = thyroid;
    }
}
