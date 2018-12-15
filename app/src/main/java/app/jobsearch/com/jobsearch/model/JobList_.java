
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobList_ {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Budget")
    @Expose
    private String budget;
    @SerializedName("Requirement")
    @Expose
    private Object requirement;
    @SerializedName("IsCompleted")
    @Expose
    private Object isCompleted;
    @SerializedName("CreatedBy")
    @Expose
    private CreatedBy createdBy;


    @SerializedName("Rating")
    @Expose
    private Rating Rating;


    public String getNoOfVacancy() {
        return NoOfVacancy;
    }

    public void setNoOfVacancy(String noOfVacancy) {
        NoOfVacancy = noOfVacancy;
    }

    @SerializedName("NoOfVacancy")
    @Expose
    private String NoOfVacancy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Object getRequirement() {
        return requirement;
    }

    public void setRequirement(Object requirement) {
        this.requirement = requirement;
    }

    public Object getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Object isCompleted) {
        this.isCompleted = isCompleted;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }


    //.....................New Fields................................//

    private Object DeadLine = "";

    private Object CompanyName = "";

    private Object CompanyProfile = "";

    private Object JobType = "";

    public Object getDeadLine() {
        return DeadLine;
    }

    public void setDeadLine(Object deadLine) {
        DeadLine = deadLine;
    }

    public Object getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(Object companyName) {
        CompanyName = companyName;
    }

    public Object getCompanyProfile() {
        return CompanyProfile;
    }

    public void setCompanyProfile(Object companyProfile) {
        CompanyProfile = companyProfile;
    }

    public Object getJobType() {
        return JobType;
    }

    public void setJobType(Object jobType) {
        JobType = jobType;
    }



    public Rating getRating() {
        return Rating;
    }

    public void setRating(Rating rating) {
        Rating = rating;
    }

}
