
package app.jobsearch.com.jobsearch.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobList {

    @SerializedName("JobList")
    @Expose
    private ArrayList<JobList_> jobList = null;

    public ArrayList<JobList_> getJobList() {
        return jobList;
    }

    public void setJobList(ArrayList<JobList_> jobList) {
        this.jobList = jobList;
    }

}
