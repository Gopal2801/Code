package app.jobsearch.com.jobsearch.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.unstoppable.submitbuttonview.SubmitButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.adapter.JSListAdapter;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Education;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityAlpha extends AppCompatActivity implements View.OnClickListener, ConstantValues, JSListAdapter.ClickListener {

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private LinearLayout llSignup;
    private Button btnSignup;
    private Button btnSignin;
    LinearLayout llsignin, llsignup;
    private SubmitButton mySignUpBTN, mySignInBTN;
    private TextInputEditText mySignEmailEDT, mySignUpEmailEDT, mySignPWdEDT,
            mySignUpPWdEDT, mySingupnameEDT, myMobileEDT, myDobEDT, myQualificationEDT,
            mySkillEDT, myExperienceEDT, myPortfolioEDT, myAddressEDT;
    private FragmentActivity myContext;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ListPopupWindow myLPWindow;
    private JSListAdapter myAdapter;
    private ApiInterface myAPIInterface;
    private ProgressDialog myDialog;
    private Preference myPrefs;
    private String mySignInEmailSTR = "", mySignInPwdSTR = "";
    private String mySignUpEmailSTR = "", mySignUpPwdSTR = "", mySignUpMobileSTR = "",
            mySignUpNameSTR = "", mySignUpDobSTR = "", mySignUpQualificationSTR = "", mySignUpSkillSTR = "", mySignUpEducationSTR = "1";

    private ArrayList<Qualification> myQualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alpha);
        myContext = MainActivityAlpha.this;
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        //LinearLayout singnin =(LinearLayout)findViewById(R.id.signin);
        llsignup = (LinearLayout) findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignin = (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        classWidgets();

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });

        showSigninForm();

      /*  mySignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                if (isSigninScreen)
                    mySignUpBTN.startAnimation(clockwise);
            }
        });*/


    }

    private void classWidgets() {
        myPrefs = new Preference(myContext);
        myQualification = new ArrayList<>();
        mySignUpBTN = (SubmitButton) findViewById(R.id.singup_pg);
        mySignInBTN = (SubmitButton) findViewById(R.id.singin_pg);
        mySignEmailEDT = (TextInputEditText) findViewById(R.id.signemailEDT);
        mySignPWdEDT = (TextInputEditText) findViewById(R.id.signinwdEDT);
        mySignUpEmailEDT = (TextInputEditText) findViewById(R.id.signupemailID);
        mySignUpPWdEDT = (TextInputEditText) findViewById(R.id.signpwdEDT);
        mySingupnameEDT = (TextInputEditText) findViewById(R.id.singupnameEDT);
        myMobileEDT = (TextInputEditText) findViewById(R.id.mobileEDT);
        myDobEDT = (TextInputEditText) findViewById(R.id.dobEDT);
        myQualificationEDT = (TextInputEditText) findViewById(R.id.qualificationEDT);
        mySkillEDT = (TextInputEditText) findViewById(R.id.skillEDT);
        myExperienceEDT = (TextInputEditText) findViewById(R.id.experienceEDT);
        myPortfolioEDT = (TextInputEditText) findViewById(R.id.portfolioEDT);
        myAddressEDT = (TextInputEditText) findViewById(R.id.addressEDT);
        myLPWindow = new ListPopupWindow(myContext);
        myDialog = new ProgressDialog(myContext);
        if (JSHelper.CheckInternet(myContext)) {
            getQualificationDetails();
        } else {
            loadWindowlist();
        }

        getCurrentDate();
        clickListener();
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }

    private void clickListener() {

        myMobileEDT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    JSHelper.hideSoftKeyboard(myContext, myMobileEDT);
                    showDatePicker();
                    //implement stuff here
                }
                return false;
            }
        });


        mySignUpPWdEDT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    myMobileEDT.requestFocus();
                }
                return false;
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationForSignUp()) {

                    if (JSHelper.CheckInternet(myContext)) {

                        getSignUpValues();

                        callSingUpAsync();

                    } else {

                        JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                    }
                }
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //callMainPage();

                if (validationForSignIn()) {

                    if (JSHelper.CheckInternet(myContext)) {

                        getSingInValue();

                        callLoginAsync();

                    } else {

                        JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                    }


                }
            }
        });
        mySignInBTN.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {

            }
        });

        myDobEDT.setFocusableInTouchMode(false);
        myDobEDT.setFocusable(false);
        myDobEDT.setOnClickListener(new View.OnClickListener() {
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
                showWindow();
            }
        });
    }

    private void getSignUpValues() {

        mySignUpNameSTR = getEditTextValue(mySingupnameEDT);

        mySignUpEmailSTR = getEditTextValue(mySignUpEmailEDT);

        mySignUpPwdSTR = getEditTextValue(mySignUpPWdEDT);

        mySignUpDobSTR = getEditTextValue(myDobEDT);

        mySignUpNameSTR = getEditTextValue(mySingupnameEDT);

        mySignUpNameSTR = getEditTextValue(mySingupnameEDT);

        mySignUpMobileSTR = getEditTextValue(myMobileEDT);

        mySignUpSkillSTR = getEditTextValue(mySkillEDT);


    }

    private void getSingInValue() {

        mySignInEmailSTR = getEditTextValue(mySignEmailEDT);

        mySignInPwdSTR = getEditTextValue(mySignPWdEDT);

    }

    private void loadWindowlist() {


        //DummyData
        ArrayList<Qualification> aList = new ArrayList<>();

        Qualification aEducation = new Qualification();

        aEducation.setId(1);

        aEducation.setText("BE");

        myQualification.add(aEducation);

        Qualification aEducation1 = new Qualification();

        aEducation1.setId(2);

        aEducation1.setText("ME");

        myQualification.add(aEducation1);

        Qualification aEducation2 = new Qualification();

        aEducation2.setId(3);

        aEducation2.setText("BA");

        myQualification.add(aEducation2);

        Qualification aEducation3 = new Qualification();

        aEducation3.setId(4);

        aEducation3.setText("MA");

        myQualification.add(aEducation3);

        Qualification aEducation4 = new Qualification();

        aEducation4.setId(5);

        aEducation4.setText("BCA");

        myQualification.add(aEducation4);

        toShowQualification();


    }

    private void toShowQualification() {

        myAdapter = new JSListAdapter(myContext, myQualification, myLPWindow, QUALI);

        myAdapter.setData(this);


    }

    private void showWindow() {

        myLPWindow.setAdapter(myAdapter);

        myLPWindow.setAnchorView(myQualificationEDT);

        myLPWindow.setModal(true);

        myLPWindow.show();
    }

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llSignin || v.getId() == R.id.llSignup) {
            // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            InputMethodManager methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }


    }


    private boolean validationForSignIn() {
        if (getEditTextValue(mySignEmailEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_ENTER_EMAIL_PHONE);
            return false;
        } else if (getEditTextValue(mySignPWdEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_ENTER_PASSWORD);
            return false;
        } else {
            return true;
        }
    }

    private boolean validationForSignUp() {
        if (getEditTextValue(mySingupnameEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_NAME);
            return false;
        } else if (getEditTextValue(mySignUpEmailEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EMAIL);
            return false;
        } else if (!JSHelper.isValidEmail(getEditTextValue(mySignUpEmailEDT))) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_VALID_EMAIL);
            return false;
        } else if (getEditTextValue(mySignUpPWdEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_ENTER_PASSWORD);
            return false;
        } else if (getEditTextValue(myMobileEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_MOBILE_NO);
            return false;
        } else if (getEditTextValue(myMobileEDT).length() > 10) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_VALID_MOBILE_NO);
            return false;
        } else if (getEditTextValue(myMobileEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_MOBILE_NO);
            return false;
        } else if (getEditTextValue(myDobEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_DOB);
            return false;
        } else if (getEditTextValue(myQualificationEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_QUALIFICATION);
            return false;
        } else if (getEditTextValue(mySkillEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_SKILL);
            return false;
        } else if (getEditTextValue(myQualificationEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_QUALIFICATION);
            return false;
        } /*else if (getEditTextValue(myPortfolioEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_PORTFOLIO);
            return false;
        } else if (getEditTextValue(myAddressEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_ADDRESS);
            return false;
        }*/ else {
            return true;
        }
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(myContext,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        myDobEDT.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();


    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    @Override
    public void setData() {

        if (myAdapter != null) {
            if (!myAdapter.getQualification().equals("")) {
                myQualificationEDT.setText(myAdapter.getQualification());
                mySignUpQualificationSTR = myAdapter.getQualificationID();
                myPrefs.putQualificatonID(mySignUpQualificationSTR);

            } else {
                myQualificationEDT.setHint(myContext.getResources().getString(R.string.qualification));

            }

        }

    }

    public void callLoginAsync() {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.loginService(mySignInEmailSTR, mySignInPwdSTR);

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

                        //  toDismissDialog();

                        myPrefs.putUserInfo(aProfile.getUserInfo());

                        if (aProfile.getUserInfo().getMedicalHistory() != null) {

                            myPrefs.putMedicalDetails(aProfile.getUserInfo().getMedicalHistory());

                        }
                        if (aProfile.getUserInfo().getEducation() != null) {

                            if (aProfile.getUserInfo().getEducation().size() > 0) {

                                myPrefs.putEducationList(aProfile.getUserInfo().getEducation());
                            }

                        }
                        if (aProfile.getUserInfo().getExperience() != null) {

                            if (aProfile.getUserInfo().getExperience().size() > 0) {

                                myPrefs.putExperienceList(aProfile.getUserInfo().getExperience());

                            }
                        }


                        if (aProfile.getUserInfo().getSchools() != null) {

                            if (aProfile.getUserInfo().getSchools().size() > 0) {

                                myPrefs.putSchoolingList(aProfile.getUserInfo().getSchools());
                            }

                        }

                        if (aProfile.getUserInfo().getProfileImage() != null) {

                            String aImage = aProfile.getUserInfo().getProfileImage();

                            if (!aImage.equals("")) {

                                myPrefs.putProfileImage(IMAGE_URL + aImage);

                            } else {

                                myPrefs.putProfileImage("");

                            }
                        }


                        callMainPage();

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

    public void callGetQualification() {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.loginService(mySignInEmailSTR, mySignInPwdSTR);

        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {

                Log.e("RESPONSE", response.message());

                ProfileData aProfile = response.body();

                String aResponseCode = aProfile.getResponseCode();

                String aResponseMsg = aProfile.getResponseMessage();

                switch (aResponseCode) {

                    case RESPONSE_CODE_SUCCESS:

                        //  toDismissDialog();

                        myPrefs.putUserInfo(aProfile.getUserInfo());

                        callMainPage();

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

    public void callSingUpAsync() {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.signUpService("", mySignUpNameSTR, mySignUpMobileSTR, "",
                mySignUpEmailSTR, mySignUpPwdSTR, mySignUpDobSTR,
                mySignUpQualificationSTR, mySignUpSkillSTR, mySignUpEducationSTR,
                "", "", "false", "false", "", "", "",
                "", "", "", INSERT, "0");

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

                        //  toDismissDialog();

                        myPrefs.putUserInfo(aProfile.getUserInfo());

                        callMainPage();

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

    public void getQualificationDetails() {


        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Qualification>> call = myAPIInterface.getQualification();

        call.enqueue(new Callback<ArrayList<Qualification>>() {
            @Override
            public void onResponse(Call<ArrayList<Qualification>> call, Response<ArrayList<Qualification>> response) {

                Log.e("RESPONSE", response.message());

                myQualification = response.body();
                myPrefs.putQualificaiton(myQualification);
                toLoQualification();

            }

            @Override
            public void onFailure(Call<ArrayList<Qualification>> call, Throwable t) {
                loadWindowlist();

            }
        });
    }

    private void toLoQualification() {
        toShowQualification();
    }

    private void toDismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

    private void callMainPage() {

        myPrefs.putStatus(true);
        Intent i = new Intent(myContext, SampleActivity.class);
        startActivity(i);
    }


}
