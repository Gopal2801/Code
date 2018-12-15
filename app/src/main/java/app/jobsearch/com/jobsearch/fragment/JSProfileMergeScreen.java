package app.jobsearch.com.jobsearch.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
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


public class JSProfileMergeScreen extends JSFragment implements ConstantValues, JSListAdapter.ClickListener {

    public static final String TAG = "JSProfileMergeScreen";

    private TextInputEditText myUserNameEDT, myMobileNoEDT, myAddressEDT, myDOBEDT,
            myQualificationEDT, myExperienceEDT, mySkillEDT, myPortFolioEDT, myProfilePwdEDT, myProfileEmailEDT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM, myBackIM;

    private UserInfo myUSerInfo;

    private ApiInterface myAPIInterface;

    private ProgressDialog myDialog;

    private Button mySubmitBTN;

    private String myNameSTR = "", myDobSTR = "", myMobileSTR = "", mySkillSTR = "", myQualificationSTR = "",
            myExperienceSTR = "", myAddressSTR = "", myPortfolioSTR = "", myEmailSTR = "", myPwdSTR = "";

    private int mYear, mMonth, mDay, mHour, mMinute;

    private ArrayList<Qualification> myQualification;

    private JSListAdapter myAdapter;

    private ListPopupWindow myLPWindow;

    private ArrayList<Qualification> myExperienceList;

    private TextView myHeaderTXT;

    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_merge_profile,
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

        myExperienceList = new ArrayList<>();

        myQualification = new ArrayList<>();

        myQualification = myPref.getQualificaiton();

        myUserNameEDT = (TextInputEditText) aView.findViewById(R.id.profilemergeNameEDT);

        myMobileNoEDT = (TextInputEditText) aView.findViewById(R.id.profilemergeMobileEDT);

        myDOBEDT = (TextInputEditText) aView.findViewById(R.id.profilemergeDOBEDT);

        myQualificationEDT = (TextInputEditText) aView.findViewById(R.id.profilemergeQualificationEDT);

        myProfilePwdEDT = (TextInputEditText) aView.findViewById(R.id.profilepwdEDT);

        myProfileEmailEDT = (TextInputEditText) aView.findViewById(R.id.profileEmailEDT);

        myExperienceEDT = (TextInputEditText) aView.findViewById(R.id.profileMergeExpEDT);

        mySkillEDT = (TextInputEditText) aView.findViewById(R.id.profileMergeSkillEDT);

        myAddressEDT = (TextInputEditText) aView.findViewById(R.id.profilemergeAddressEDT);

        myPortFolioEDT = (TextInputEditText) aView.findViewById(R.id.profilemergePortfolioEDT);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        mySubmitBTN = (Button) aView.findViewById(R.id.profileMergeSaveBTN);

        myUSerInfo = new UserInfo();

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myLPWindow = new ListPopupWindow(myContext);

        getExperienceDetails();

        ((SampleActivity) myContext).hideActionBar();

        getCurrentDate();

        clickListener();

        setHeader();
    }

    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.lable_merge_profile));
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
                ((SampleActivity) myContext).openMenu();
            }
        });

        mySubmitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationForMerge()) {

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
        myQualificationEDT.setFocusableInTouchMode(false);
        myQualificationEDT.setFocusable(false);
        myQualificationEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(myQualification, QUALI);

            }
        });
        myExperienceEDT.setFocusableInTouchMode(false);
        myExperienceEDT.setFocusable(false);
        myExperienceEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(myExperienceList, EXPER);

            }
        });
    }

    private void showWindow(ArrayList<Qualification> aQualification, String aType) {

        if (aQualification != null) {

            myAdapter = new JSListAdapter(myContext, aQualification, myLPWindow, aType);

            myAdapter.setData(this);

            myLPWindow.setAdapter(myAdapter);

            myLPWindow.setAnchorView(myQualificationEDT);

            myLPWindow.setModal(true);

            myLPWindow.show();
        }
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


        myNameSTR = getEditTextValue(myUserNameEDT);

        myMobileSTR = getEditTextValue(myMobileNoEDT);

        myEmailSTR = getEditTextValue(myProfileEmailEDT);

        myPwdSTR = getEditTextValue(myProfilePwdEDT);

        myDobSTR = getEditTextValue(myDOBEDT);

        myDobSTR = myDobSTR.replace("/", "-");


        // myQualificationSTR = getEditTextValue(myQualificationEDT);

        //myExperienceSTR = getEditTextValue(myExperienceEDT);

        mySkillSTR = getEditTextValue(mySkillEDT);

        myAddressSTR = getEditTextValue(myAddressEDT);

        myPortfolioSTR = getEditTextValue(myPortFolioEDT);


    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    private boolean validationForMerge() {
        if (getEditTextValue(myUserNameEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_NAME);
            return false;
        } else if (getEditTextValue(myProfileEmailEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EMAIL);
            return false;
        } else if (!JSHelper.isValidEmail(getEditTextValue(myProfileEmailEDT))) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_VALID_EMAIL);
            return false;
        } else if (getEditTextValue(myProfilePwdEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_ENTER_PASSWORD);
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
        } else if (getEditTextValue(myQualificationEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_QUALIFICATION);
            return false;
        } else if (getEditTextValue(myExperienceEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_CHOOSE_EXPE);
            return false;
        } else if (getEditTextValue(mySkillEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_SKILL);
            return false;
        } else {
            return true;
        }
    }

    public void callUpdateProfileDetails() {

        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.signUpService(myPref.getUserID(), myNameSTR, myMobileSTR, "",
                myEmailSTR, myPwdSTR, myDobSTR,
                myQualificationSTR, "", myExperienceSTR,
                myPortfolioSTR, myAddressSTR, "false", "false", "",
                "", "", "", "", "", MERGE, "0");

        //Call<ProfileData> call = myAPIInterface.updateBasicInfo(myQualificationSTR, myExperienceSTR, mySkillSTR, myPortfolioSTR);

        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());


                ProfileData aProfile = response.body();

                if (aProfile != null) {

                    String aResponseCode = aProfile.getResponseCode();

                    String aResponseMsg = aProfile.getResponseMessage();

                    switch (aResponseCode) {

                        case RESPONSE_CODE_SUCCESS:

                            showAlertDialog(SUCESS_ALERT_FOR_MERGE + " " + myNameSTR);

                            toEmptyEditText();

                            // toDismissDialog();

                            //myPref.putUserInfo(aProfile.getUserInfo());

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
                } else {

                    toDismissDialog();

                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    private void toEmptyEditText() {

        myUserNameEDT.setText("");

        myMobileNoEDT.setText("");

        myDOBEDT.setText("");

        mySkillEDT.setText("");

        mySkillEDT.setText("");

        myPortFolioEDT.setText("");
    }

    private void toDismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }

    @Override
    public void setData() {
        if (myAdapter != null) {

            String aType = myAdapter.getMyType();

            if (!aType.equals("")) {

                if (aType.equals(QUALI)) {

                    myQualificationEDT.setText(myAdapter.getQualification());

                    myQualificationSTR = myAdapter.getQualificationID();

                } else if (aType.equals(EXPER)) {

                    myExperienceEDT.setText(myAdapter.getQualification());

                    myExperienceSTR = myAdapter.getQualificationID();
                }
            }


        } else {

            myQualificationEDT.setHint(myContext.getResources().getString(R.string.qualification));

        }
    }

    /**
     * @param aMessage
     */
    public void showAlertDialog(String aMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
            builder.setMessage(aMessage)
                    .setTitle(APP_NAME)
                    .setCancelable(false)
                    .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            // Change the buttons color in dialog
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getExperienceDetails() {

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Qualification>> call = myAPIInterface.getExperience();

        call.enqueue(new Callback<ArrayList<Qualification>>() {
            @Override
            public void onResponse(Call<ArrayList<Qualification>> call, Response<ArrayList<Qualification>> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                myExperienceList = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<Qualification>> call, Throwable t) {

            }
        });
    }

}
