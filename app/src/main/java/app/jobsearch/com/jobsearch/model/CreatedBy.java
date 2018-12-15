
package app.jobsearch.com.jobsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("MailId")
    @Expose
    private String mailId;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("MobileNo1")
    @Expose
    private String mobileNo1;
    @SerializedName("QualicationId")
    @Expose
    private String qualicationId;
    @SerializedName("SKILLS")
    @Expose
    private String sKILLS;
    @SerializedName("ExperienceId")
    @Expose
    private String experienceId;
    @SerializedName("PortFolio")
    @Expose
    private String portFolio;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Password")
    @Expose
    private Object password;
    @SerializedName("IsPublic")
    @Expose
    private Object isPublic;
    @SerializedName("IsExperienced")
    @Expose
    private Object isExperienced;
    @SerializedName("IsActive")
    @Expose
    private Object isActive;
    @SerializedName("Isdeleted")
    @Expose
    private Object isdeleted;

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    @SerializedName("ProfileImage")
    @Expose
    private String ProfileImage;


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

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public String getQualicationId() {
        return qualicationId;
    }

    public void setQualicationId(String qualicationId) {
        this.qualicationId = qualicationId;
    }

    public String getSKILLS() {
        return sKILLS;
    }

    public void setSKILLS(String sKILLS) {
        this.sKILLS = sKILLS;
    }

    public String getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(String experienceId) {
        this.experienceId = experienceId;
    }

    public String getPortFolio() {
        return portFolio;
    }

    public void setPortFolio(String portFolio) {
        this.portFolio = portFolio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Object isPublic) {
        this.isPublic = isPublic;
    }

    public Object getIsExperienced() {
        return isExperienced;
    }

    public void setIsExperienced(Object isExperienced) {
        this.isExperienced = isExperienced;
    }

    public Object getIsActive() {
        return isActive;
    }

    public void setIsActive(Object isActive) {
        this.isActive = isActive;
    }

    public Object getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Object isdeleted) {
        this.isdeleted = isdeleted;
    }


    public String getCurrentStatus() {
        return CurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        CurrentStatus = currentStatus;
    }

    @SerializedName("CurrentStatus")
    @Expose
    private String CurrentStatus;
}
