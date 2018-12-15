package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    public String getCurrentRating() {
        return CurrentRating;
    }

    public void setCurrentRating(String currentRating) {
        CurrentRating = currentRating;
    }

    public String getOverallRating() {
        return OverallRating;
    }

    public void setOverallRating(String overallRating) {
        OverallRating = overallRating;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    @SerializedName("CurrentRating")
    @Expose
    private String CurrentRating = "";

    @SerializedName("OverallRating")
    @Expose
    private String OverallRating = "";

    @SerializedName("Remarks")
    @Expose
    private String Remarks = "";
}
