
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List1 {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Venue")
    @Expose
    private String venue;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Place")
    @Expose
    private String place;
    @SerializedName("TicketAvailable")
    @Expose
    private String ticketAvailable;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("ImageURL")
    @Expose
    private String imageURL;
    @SerializedName("TotalViews")
    @Expose
    private int totalViews;
    @SerializedName("IsViewed")
    @Expose
    private String isViewed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTicketAvailable() {
        return ticketAvailable;
    }

    public void setTicketAvailable(String ticketAvailable) {
        this.ticketAvailable = ticketAvailable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    public String getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(String isViewed) {
        this.isViewed = isViewed;
    }

}
