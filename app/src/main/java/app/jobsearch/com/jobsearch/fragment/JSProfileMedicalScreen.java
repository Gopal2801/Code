package app.jobsearch.com.jobsearch.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

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
import app.jobsearch.com.jobsearch.model.MedicalHistory;
import app.jobsearch.com.jobsearch.model.MedicalInfo;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSProfileMedicalScreen extends JSFragment implements ConstantValues {

    public static final String TAG = "JSProfileMedicalScreen";



    /*http://localhost:1583/api/User/SaveMedicalHistory?userId=1&EyePower=0&BloodGroup=A+ve&Weight=84&
    // Height=183&eyeColor=Black&nailSymptom=Yes&bloodPressure=94&sugar=Normal&cancer=Normal&hearing=Normal&thyroid=No*/

    private TextInputEditText myEyePowerEDT, myBloodGroupEDT, myWeightEDT, myHeightEDT,
            myEyeColorEDT, myNailSymEDT, myBloodPressureEDT, mySugarEDT, myCancerEDT, myHearingEDT, myThyroidEDT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM, myBackIM;

    private UserInfo myUSerInfo;

    private ApiInterface myAPIInterface;

    private ProgressDialog myDialog;

    private Button mySubmitBTN;

    private String myEyePowerSTR = "", myBloodGroupSTR = "", myWeightSTR = "", myHeightSTR = "", myEyeColorSTR = "",
            myNailSymSTR = "", myBloodPressureSTR = "", mySugarSTR = "", myCancerSTR = "", myHearingSTR = "", myThyroidSTR = "";

    private int mYear, mMonth, mDay, mHour, mMinute;

    private ArrayList<Qualification> myQualification;

    private JSListAdapter myAdapter;

    private ListPopupWindow myLPWindow;

    private ArrayList<Qualification> myExperienceList;

    private TextView myHeaderTXT;

    private RadioButton mySugarYesRB, mySugarNoRB, myThroidYesRB, myThroidNoRB, myCancerYesRB, myCancerNoRB;


    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_medical_profile,
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

        myEyePowerEDT = (TextInputEditText) aView.findViewById(R.id.eye_power_EDT);

        myBloodGroupEDT = (TextInputEditText) aView.findViewById(R.id.blood_grouo_EDT);

        myWeightEDT = (TextInputEditText) aView.findViewById(R.id.weight_EDT);

        myHeightEDT = (TextInputEditText) aView.findViewById(R.id.height_EDT);

        myEyeColorEDT = (TextInputEditText) aView.findViewById(R.id.eye_color_EDT);

        myNailSymEDT = (TextInputEditText) aView.findViewById(R.id.nailsym_EDT);

        myBloodPressureEDT = (TextInputEditText) aView.findViewById(R.id.blood_pressure_EDT);

        mySugarEDT = (TextInputEditText) aView.findViewById(R.id.sugar_EDT);

        myCancerEDT = (TextInputEditText) aView.findViewById(R.id.cancer_EDT);

        myHearingEDT = (TextInputEditText) aView.findViewById(R.id.hearing_EDT);

        myThyroidEDT = (TextInputEditText) aView.findViewById(R.id.thyroid_EDT);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        mySubmitBTN = (Button) aView.findViewById(R.id.profileMergeSaveBTN);


        mySugarYesRB = (RadioButton) aView.findViewById(R.id.sugar_yes_RB);

        mySugarNoRB = (RadioButton) aView.findViewById(R.id.sugar_no_RB);


        myCancerYesRB = (RadioButton) aView.findViewById(R.id.cancer_yes_RB);

        myCancerNoRB = (RadioButton) aView.findViewById(R.id.cancer_no_RB);

        myThroidYesRB = (RadioButton) aView.findViewById(R.id.thyroid_yes_RB);

        myThroidNoRB = (RadioButton) aView.findViewById(R.id.thyroid_no_RB);


        myUSerInfo = new UserInfo();

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myLPWindow = new ListPopupWindow(myContext);

        getExperienceDetails();

        ((SampleActivity) myContext).hideActionBar();

        getCurrentDate();

        clickListener();

        setHeader();


        myCancerSTR = "0";

        myCancerNoRB.setChecked(true);

        mySugarSTR = "0";

        mySugarNoRB.setChecked(true);

        myThyroidSTR = "0";

        myThroidNoRB.setChecked(true);


        setMedicalField();
    }

    private void setMedicalField() {

        MedicalHistory aMedicalInfo = myPref.getMedicalDetails();

        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getEyePower())) {
            myEyePowerEDT.setText(aMedicalInfo.getEyePower());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getBloodGroup())) {
            myBloodGroupEDT.setText(aMedicalInfo.getBloodGroup());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getWeight())) {
            myWeightEDT.setText(aMedicalInfo.getWeight());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getHeight())) {
            myHeightEDT.setText(aMedicalInfo.getHeight());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getEyeColor())) {
            myEyeColorEDT.setText(aMedicalInfo.getEyeColor());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getNailSymptom())) {
            myNailSymEDT.setText(aMedicalInfo.getNailSymptom());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getBloodPressure())) {
            myBloodPressureEDT.setText(aMedicalInfo.getEyePower());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getSugar())) {

            if (aMedicalInfo.getSugar().equals("1")) {
                mySugarYesRB.setChecked(true);
                mySugarSTR = "1";
            } else {
                mySugarNoRB.setChecked(true);
                mySugarSTR = "0";
            }
            //mySugarEDT.setText(aMedicalInfo.getEyePower());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getCancer())) {
            // myCancerEDT.setText(aMedicalInfo.getCancer());

            if (aMedicalInfo.getCancer().equals("1")) {
                myCancerYesRB.setChecked(true);
                myCancerSTR = "1";
            } else {
                myCancerNoRB.setChecked(true);
                myCancerSTR = "0";
            }

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getHearing())) {
            myHearingEDT.setText(aMedicalInfo.getEyePower());

        }
        if (JSHelper.stringIsNotNullCheck(aMedicalInfo.getThyroid())) {
            //myThyroidEDT.setText(aMedicalInfo.getEyePower());

            if (aMedicalInfo.getThyroid().equals("1")) {
                myThroidYesRB.setChecked(true);
                myThyroidSTR = "1";
            } else {
                myThroidNoRB.setChecked(true);
                myThyroidSTR = "0";
            }

        }

    }

    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.lable_medical));
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


        mySugarNoRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mySugarSTR = "0";
                }


            }
        });


        mySugarYesRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mySugarSTR = "1";
                }
            }
        });


        myCancerNoRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myCancerSTR = "0";
                }


            }
        });


        myCancerYesRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myCancerSTR = "1";
                }
            }
        });


        myThroidYesRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myThyroidSTR = "1";
                }


            }
        });


        myThroidNoRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myThyroidSTR = "0";
                }
            }
        });


    }


    private void getValues() {


        myEyePowerSTR = getEditTextValue(myEyePowerEDT);

        myBloodGroupSTR = getEditTextValue(myBloodGroupEDT);

        myWeightSTR = getEditTextValue(myWeightEDT);

        myHeightSTR = getEditTextValue(myHeightEDT);

        myEyeColorSTR = getEditTextValue(myEyeColorEDT);

        myNailSymSTR = getEditTextValue(myNailSymEDT);

        myBloodPressureSTR = getEditTextValue(myBloodPressureEDT);

        myHearingSTR = getEditTextValue(myHearingEDT);


     /*   mySugarSTR = getEditTextValue(mySugarEDT);

        myCancerSTR = getEditTextValue(myCancerEDT);


        myThyroidSTR = getEditTextValue(myThyroidEDT);*/


    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    private boolean validationForMerge() {
        if (getEditTextValue(myEyePowerEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EYE_POWER);
            return false;
        } else if (getEditTextValue(myBloodGroupEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_BLOOD_GROUP);
            return false;
        } else if (getEditTextValue(myWeightEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_WEIGHT);
            return false;
        } else if (getEditTextValue(myHeightEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_HEIGHT);
            return false;
        } else if (getEditTextValue(myEyeColorEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EYE_COLOR);
            return false;
        } else if (getEditTextValue(myNailSymEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_NAIL_SYMPTOM);
            return false;
        } else if (getEditTextValue(myBloodPressureEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_BLOOD_PRESSURE);
            return false;
        } else if (getEditTextValue(myHearingEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_HEARING);
            return false;
        } /*else if (getEditTextValue(mySugarEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_SUGAR);
            return false;
        } else if (getEditTextValue(myCancerEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_CANCER);
            return false;
        } else if (getEditTextValue(myThyroidEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_THYROID);
            return false;
        } */ else {
            return true;
        }
    }

    public void callUpdateProfileDetails() {

        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = myAPIInterface.saveMedicalHistory(myPref.getUserID(), myEyePowerSTR, myBloodGroupSTR, myWeightSTR
                , myHeightSTR, myEyeColorSTR,
                myNailSymSTR, myBloodPressureSTR, mySugarSTR,
                myCancerSTR, myHearingSTR, myThyroidSTR);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                toDismissDialog();

                showAlertDialog(myContext, ALERT_FOR_SUCCESS);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    public void showAlertDialog(final Context aContext, String aMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(aContext);
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
            pbutton.setTextColor(ContextCompat.getColor(aContext, R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
