
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Experience {

    @SerializedName("Job")
    @Expose
    private String job = "";
    @SerializedName("Company")
    @Expose
    private String company = "";
    @SerializedName("City")
    @Expose
    private String city = "";
    @SerializedName("From")
    @Expose
    private String from = "";
    @SerializedName("To")
    @Expose
    private String to = "";
    @SerializedName("CurrentCompany")
    @Expose
    private String currentCompany = "";

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {
        this.currentCompany = currentCompany;
    }

}
