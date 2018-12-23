
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventList {

    @SerializedName("list")
    @Expose
    private ArrayList<List1> list = null;
    @SerializedName("couponList")
    @Expose
    private ArrayList<String> couponList = null;

    public ArrayList<List1> getList() {
        return list;
    }

    public void setList(ArrayList<List1> list) {
        this.list = list;
    }

    public ArrayList<String> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<String> couponList) {
        this.couponList = couponList;
    }

}
