
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistory {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("EyePower")
    @Expose
    private String eyePower;
    @SerializedName("BloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("Weight")
    @Expose
    private String weight;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("EyeColor")
    @Expose
    private String eyeColor;
    @SerializedName("NailSymptom")
    @Expose
    private String nailSymptom;
    @SerializedName("BloodPressure")
    @Expose
    private String bloodPressure;
    @SerializedName("Sugar")
    @Expose
    private String sugar;
    @SerializedName("Cancer")
    @Expose
    private String cancer;
    @SerializedName("Hearing")
    @Expose
    private String hearing;
    @SerializedName("Thyroid")
    @Expose
    private String thyroid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEyePower() {
        return eyePower;
    }

    public void setEyePower(String eyePower) {
        this.eyePower = eyePower;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getNailSymptom() {
        return nailSymptom;
    }

    public void setNailSymptom(String nailSymptom) {
        this.nailSymptom = nailSymptom;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    public String getHearing() {
        return hearing;
    }

    public void setHearing(String hearing) {
        this.hearing = hearing;
    }

    public String getThyroid() {
        return thyroid;
    }

    public void setThyroid(String thyroid) {
        this.thyroid = thyroid;
    }

}
