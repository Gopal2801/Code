
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schools {

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

    @SerializedName("Marks")
    @Expose
    private String Marks = "";

    @SerializedName("IsPassed")
    @Expose
    private String IsPassed = "";
    @SerializedName("IsSchool")
    @Expose
    private String IsSchool = "";

    public String getMarks() {
        return Marks;
    }

    public void setMarks(String marks) {
        Marks = marks;
    }

    public String getIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(String isPassed) {
        IsPassed = isPassed;
    }

    public String getIsSchool() {
        return IsSchool;
    }

    public void setIsSchool(String isSchool) {
        IsSchool = isSchool;
    }

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
