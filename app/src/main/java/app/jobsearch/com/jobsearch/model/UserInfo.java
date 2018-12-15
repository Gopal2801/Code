
package app.jobsearch.com.jobsearch.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name = "";
    @SerializedName("DOB")
    @Expose
    private String dOB = "";
    @SerializedName("MailId")
    @Expose
    private String mailId = "";
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo = "";
    @SerializedName("MobileNo1")
    @Expose
    private String mobileNo1 = "";
    @SerializedName("QualicationId")
    @Expose
    private String qualicationId = "";
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
    private Object address = "";
    @SerializedName("Password")
    @Expose
    private Object password = "";
    @SerializedName("IsPublic")
    @Expose
    private Object isPublic = "";
    @SerializedName("IsExperienced")
    @Expose
    private Object isExperienced = "";
    @SerializedName("IsActive")
    @Expose
    private Object isActive = "";
    @SerializedName("Isdeleted")
    @Expose
    private Object isdeleted = "";
    @SerializedName("ProfileImage")
    @Expose
    private String profileImage = "";
    @SerializedName("Nationality")
    @Expose
    private Object nationality = "";
    @SerializedName("School")
    @Expose
    private Object school = "";
    @SerializedName("Intrest")
    @Expose
    private Object intrest = "";
    @SerializedName("BirthPlace")
    @Expose
    private Object birthPlace = "";
    @SerializedName("College")
    @Expose
    private Object college = "";
    @SerializedName("Aadhar")
    @Expose
    private Object aadhar = "";
    @SerializedName("Hobby")
    @Expose
    private Object hobby = "";
    @SerializedName("CurrentStatus")
    @Expose
    private String currentStatus = "";
    @SerializedName("MedicalHistory")
    @Expose
    private MedicalHistory medicalHistory;
    @SerializedName("Education")
    @Expose
    private ArrayList<Education> education = null;
    @SerializedName("Experience")
    @Expose
    private ArrayList<Experience> experience = null;

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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Object getNationality() {
        return nationality;
    }

    public void setNationality(Object nationality) {
        this.nationality = nationality;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(Object school) {
        this.school = school;
    }

    public Object getIntrest() {
        return intrest;
    }

    public void setIntrest(Object intrest) {
        this.intrest = intrest;
    }

    public Object getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(Object birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Object getCollege() {
        return college;
    }

    public void setCollege(Object college) {
        this.college = college;
    }

    public Object getAadhar() {
        return aadhar;
    }

    public void setAadhar(Object aadhar) {
        this.aadhar = aadhar;
    }

    public Object getHobby() {
        return hobby;
    }

    public void setHobby(Object hobby) {
        this.hobby = hobby;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public ArrayList<Education> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<Education> education) {
        this.education = education;
    }

    public ArrayList<Experience> getExperience() {
        return experience;
    }

    public void setExperience(ArrayList<Experience> experience) {
        this.experience = experience;
    }

}
