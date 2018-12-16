package app.jobsearch.com.jobsearch.helper;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.model.JobList;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Qualification;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<ResponseBody> getTopRatedMovies();

    @GET("User/Login?")
    Call<ProfileData> loginService(@Query("email") String aEmail, @Query("password") String aPwd);

    @POST("User/UserRegistraion?")
    Call<ProfileData> signUpService(@Query("userId") String aUserId, @Query("name") String aName, @Query("mobile") String aMobile, @Query("mobile1") String aMobile1, @Query("email") String aEmail,
                                    @Query("password") String aPassword, @Query("dob") String aDob, @Query("qualificationId") String aQualificationId,
                                    @Query("skills") String aSkills, @Query("experienceId") String aExperienceId, @Query("portfolio") String aPortfolio,
                                    @Query("address") String aAddress, @Query("isExperienced") String aIsExperoenced,
                                    @Query("isPublic") String aIsPublic, @Query("nationality") String aNationality,
                                    @Query("intrest") String aInterest, @Query("birthPlace") String aBirthPlace, @Query("aadhar") String aAdhaarNo, @Query("hobby") String aHobby,
                                    @Query("updatePart") String aUpdatePart, @Query("mode") String aMode, @Query("currentStatus") String aCurrentStatus);

    @GET("User/GetQualification?")
    Call<ArrayList<Qualification>> getQualification();


    @GET("User/GetExperience?")
    Call<ArrayList<Qualification>> getExperience();

    @POST("Job/SaveJob?")
    Call<ResponseBody> createJob(@Query("id") String aID, @Query("userId") String aUserId, @Query("jobName") String aJobName, @Query("designation") String aJobDesignation, @Query("description") String aJobDesc,
                                 @Query("budget") String aJobBudget, @Query("isCompleted") String aIsComplete,
                                 @Query("requirement") String aJobRequirement, @Query("noOfVacancy") String aNoOfVacancy, @Query("mode") String aMode);


    @GET("Job/SearchJobs?")
    Call<JobList> getJobSearch(@Query("key") String aKey, @Query("userId") String aUserId, @Query("isCurrentUser") String aUserType);


    @GET("Job/UpdateRatingAndRemarks?")
    Call<ResponseBody> updateRemarksOrRate(@Query("userId") String aUserId, @Query("createdBy")
            String aCreatedBy, @Query("rating") String aRating, @Query("remarks") String aRemars, @Query("jobId") String aJobID);


    @Multipart
    @POST("User/UpdateProfilePicture?")
    Call<ProfileData> uploadProfileImage(
            @Part MultipartBody.Part file, @Query("userId") String aUserId
    );


    @Multipart
    @POST("Job/SaveJob1?")
    Call<ProfileData> createJob(
            @Part MultipartBody.Part file, @Query("userId") String aUserId
    );
    //http://jobportal.ketinfotech.in/api/Job/UpdateRatingAndRemarks?userId=24&createdBy=23&rating=2&remarks=test
    /*
    http://jobportal.perarignarannatrust.in/api/User/UserRegistraion?name=Thiyagu&dob=20-03-1991&mobile=9042840550&
    mobile1&email=k.e.thiyagarajan5@gmail.com&qualificationId=1&skills=Developing,Singing&experienceId=1&portfolio=
    Test&address=113,Mariyamman Koil Street,Erode-4&password=test@123&isExperoenced=false&isPublic=false*/


    @Multipart
    @POST("Job/SaveJob?")
    Call<ResponseBody> createJob(@Part MultipartBody.Part file, @Query("id") String aID, @Query("userId") String aUserId,
                                 @Query("companyName") String aCompanyName,
                                 @Query("jobName") String aJobName, @Query("jobType") String aJobType, @Query("designation") String aJobDesignation, @Query("description") String aJobDesc,
                                 @Query("budget") String aJobBudget, @Query("isCompleted") String aIsComplete,
                                 @Query("requirement") String aJobRequirement, @Query("noOfVacancy") String aNoOfVacancy,
                                 @Query("deadLine") String aDeadLine, @Query("mode") String aMode, @Query("keyword") String aKeyWord);


    /*http://localhost:1583/api/User/SaveMedicalHistory?userId=1&EyePower=0&BloodGroup=A+ve&Weight=84&
    // Height=183&eyeColor=Black&nailSymptom=Yes&bloodPressure=94&sugar=Normal&cancer=Normal&hearing=Normal&thyroid=No
*/
    @POST("User/SaveMedicalHistory?")
    Call<ResponseBody> saveMedicalHistory(@Query("userId") String aID, @Query("EyePower") String aEyePower,
                                          @Query("BloodGroup") String aBloodGroup,
                                          @Query("Weight") String aWeight, @Query("Height") String aHeight,
                                          @Query("eyeColor") String aEyeColor, @Query("nailSymptom") String aNailsymp,
                                          @Query("bloodPressure") String aBloodPressure, @Query("sugar") String aSugar,
                                          @Query("cancer") String aCancer, @Query("hearing") String aHearing,
                                          @Query("thyroid") String aThyroid);


    @GET("User/SaveEducationDetails?")
    Call<ResponseBody> saveEducationDetails(@Query("userId") String aID, @Query("json") String aJson);


    @GET("User/SaveExperienceDetails?")
    Call<ResponseBody> saveExperienceDetails(@Query("userId") String aID, @Query("json") String aJson);

}

