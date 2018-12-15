
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Education {

    @SerializedName("Degree")
    @Expose
    private String degree = "";
    @SerializedName("College")
    @Expose
    private String college = "";
    @SerializedName("Department")
    @Expose
    private Object department = "";
    @SerializedName("City")
    @Expose
    private String city = "";
    @SerializedName("From")
    @Expose
    private String from = "";
    @SerializedName("To")
    @Expose
    private String to = "";

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
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

}
