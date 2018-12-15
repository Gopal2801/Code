package app.jobsearch.com.jobsearch.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.activity.JSSignupActivity;
import app.jobsearch.com.jobsearch.activity.SampleActivity;
import app.jobsearch.com.jobsearch.adapter.JSListAdapter;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSProfileEditPage extends JSFragment implements ConstantValues {

    public static final String TAG = "JSProfileEditPage";

    private TextInputEditText myUserNameEDT, myEmailEDT, myMobileNoEDT, myAddressEDT, myDOBEDT,
            myNationalEDT, myBirthPlaceEDT, myAadharNoEDT, myInterestEDT, myHobbyEDT;

    private String myUserNameSTR = "", myEmailSTR = "", myMobileSTR = "", myAddressSTR = "", myDOBSTR = "",
            myNationalitySTR = "", myBirthPlaceSTR = "",
            myAadhaarNoSTR = "", myInterestSTR = "", myHobbySTR = "";

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM, myBackIM;

    private UserInfo myUSerInfo;

    private ApiInterface myAPIInterface;

    private Button mySaveBTN;

    private ProgressDialog myDialog;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private TextView myHeaderTXT;


    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_basic_edit,
                    aContainer, false);

            classInitialization(aView);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // return the view
        return aView;
    }

    private void classInitialization(View aView) {

        myFragmentManager = new JSFragmentManager(myContext);

        myDialog = new ProgressDialog(myContext);

        myPref = new Preference(myContext);

        myUserNameEDT = (TextInputEditText) aView.findViewById(R.id.profileNameEDT);

        myEmailEDT = (TextInputEditText) aView.findViewById(R.id.profileEmailEDT);

        myMobileNoEDT = (TextInputEditText) aView.findViewById(R.id.profileMobileEDT);

        myDOBEDT = (TextInputEditText) aView.findViewById(R.id.profileDOBEDT);

        myAddressEDT = (TextInputEditText) aView.findViewById(R.id.profileAddressEDT);

        myNationalEDT = (TextInputEditText) aView.findViewById(R.id.profileNationalityEDT);

        myBirthPlaceEDT = (TextInputEditText) aView.findViewById(R.id.profileBirthPlace);

        myAadharNoEDT = (TextInputEditText) aView.findViewById(R.id.profileAadharNoTXT);

        myInterestEDT = (TextInputEditText) aView.findViewById(R.id.profileInterestEDT);

        myHobbyEDT = (TextInputEditText) aView.findViewById(R.id.profileHobbyEDT);


        mySaveBTN = (Button) aView.findViewById(R.id.basic_edit_saveBtn);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myUSerInfo = new UserInfo();

        setHeader();

        getBundleValues();

        clickListener();

        getCurrentDate();

    }

    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.label_edit_profile));
    }

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void clickListener() {
        myBackIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContext.onBackPressed();
                //  myContext.onBackPressed();
            }
        });

        mySaveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationForUpdate()) {

                    if (JSHelper.CheckInternet(myContext)) {

                        getValues();

                        callUpdateProfileDetails();

                    } else {

                        JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                    }
                }

            }
        });

        myDOBEDT.setFocusableInTouchMode(false);
        myDOBEDT.setFocusable(false);
        myDOBEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(myContext,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        myDOBEDT.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();


    }

    private void getValues() {

        myUserNameSTR = getEditTextValue(myUserNameEDT);

        myEmailSTR = getEditTextValue(myEmailEDT);

        myMobileSTR = getEditTextValue(myMobileNoEDT);

        myAddressSTR = getEditTextValue(myAddressEDT);

        myDOBSTR = getEditTextValue(myDOBEDT);

        myDOBSTR = myDOBSTR.replace("/", "-");

        myNationalitySTR = getEditTextValue(myNationalEDT);

        myBirthPlaceSTR = getEditTextValue(myBirthPlaceEDT);

        myAadhaarNoSTR = getEditTextValue(myAadharNoEDT);

        myInterestSTR = getEditTextValue(myInterestEDT);

        myHobbySTR = getEditTextValue(myHobbyEDT);

    }

    private boolean validationForUpdate() {
        if (getEditTextValue(myUserNameEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_NAME);
            return false;
        } else if (getEditTextValue(myEmailEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EMAIL);
            return false;
        } else if (!JSHelper.isValidEmail(getEditTextValue(myEmailEDT))) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_VALID_EMAIL);
            return false;
        } else if (getEditTextValue(myMobileNoEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_MOBILE_NO);
            return false;
        } else if (getEditTextValue(myMobileNoEDT).length() > 10) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_VALID_MOBILE_NO);
            return false;
        } else if (getEditTextValue(myDOBEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_DOB);
            return false;
        } else {
            return true;
        }
    }

    private void getBundleValues() {

        Bundle bundle = getArguments();

        if (bundle != null) {

            String aUserInfo = bundle.getString("USERINFO");

            myUSerInfo = new Gson().fromJson(aUserInfo, UserInfo.class);

            myUserNameEDT.setText(myUSerInfo.getName());

            myEmailEDT.setText(myUSerInfo.getMailId());

            myMobileNoEDT.setText(myUSerInfo.getMobileNo());

            myDOBEDT.setText(myUSerInfo.getDOB());

            myAddressEDT.setText("" + myUSerInfo.getAddress());

            myNationalEDT.setText("" + myUSerInfo.getNationality());

            myBirthPlaceEDT.setText("" + myUSerInfo.getBirthPlace());

            myAadharNoEDT.setText("" + myUSerInfo.getAadhar());

            myInterestEDT.setText("" + myUSerInfo.getIntrest());

            myHobbyEDT.setText("" + myUSerInfo.getHobby());

            // myUserID = "" + myUSerInfo.getId();


        }


    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }

    public void callUpdateProfileDetails() {

        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.signUpService(myPref.getUserID(), myUserNameSTR, myMobileSTR, "",
                myEmailSTR, "", myDOBSTR,
                "", "", "",
                "", myAddressSTR, "false", "false", myNationalitySTR,
                myInterestSTR, myBirthPlaceSTR, myAadhaarNoSTR, myHobbySTR, BASIC_PROFILE_UPDATE, UPDATE, "");

        //Call<ProfileData> call = myAPIInterface.updateBasicInfo(myQualificationSTR, myExperienceSTR, mySkillSTR, myPortfolioSTR);

        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                ProfileData aProfile = response.body();

                String aResponseCode = aProfile.getResponseCode();

                String aResponseMsg = aProfile.getResponseMessage();

                switch (aResponseCode) {

                    case RESPONSE_CODE_SUCCESS:

                        toDismissDialog();

                        myPref.putUserInfo(aProfile.getUserInfo());

                        myContext.onBackPressed();

                        // callMainPage();

                        break;

                    case RESPONSE_CODE_FAILURE:

                        toDismissDialog();

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;

                    default:

                        toDismissDialog();

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    private void toDismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }
}
