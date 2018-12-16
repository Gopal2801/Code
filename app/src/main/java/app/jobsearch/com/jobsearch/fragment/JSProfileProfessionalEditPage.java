package app.jobsearch.com.jobsearch.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.activity.SampleActivity;
import app.jobsearch.com.jobsearch.adapter.JSListAdapter;
import app.jobsearch.com.jobsearch.adapter.SampleAdapter;
import app.jobsearch.com.jobsearch.adapter.SampleExpAdapter;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Education;
import app.jobsearch.com.jobsearch.model.Experience;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSProfileProfessionalEditPage extends JSFragment implements ConstantValues, JSListAdapter.ClickListener {

    public static final String TAG = JSProfileProfessionalEditPage.class.getSimpleName().toString();

    private TextInputEditText mySkillEDT, myQualificationEDT, myPortfolioEDT, myExperienceEDT;

    private UserInfo myUserInfo;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myBackIM;

    private UserInfo myUSerInfo;

    private Button mySaveBTN;

    private String mySkillSTR = "", myQualificationSTR = "", myPortfolioSTR = "", myExperienceSTR = "", myUserId = "";

    private ProgressDialog myDialog;

    private ApiInterface myAPIInterface;

    private Preference myPrefs;

    private ArrayList<Qualification> myQualification;

    private JSListAdapter myAdapter;

    private ListPopupWindow myLPWindow;

    private ArrayList<Qualification> myExperienceList;

    private TextView myHeaderTXT;

    private RadioButton myFullTimeRB, myContractRB;

    private String myJobTypeSTR = "", myJobValueSTR = "";

    private TextView myEductionTXT, myExperienceTXT;

    private RecyclerView myEducationRC, myExperienceRC;

    private ArrayList<Education> myEducationList;

    private ArrayList<Experience> myExpList;

    private SampleAdapter myEduAdapter;

    private SampleExpAdapter myExpAdpter;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {

        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_professional_edit,
                    aContainer, false);

            classInitialization(aView);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // return the view
        return aView;
    }

    private void classInitialization(View aView) {

        myPrefs = new Preference(myContext);

        myDialog = new ProgressDialog(myContext);

        myFragmentManager = new JSFragmentManager(myContext);

        myEducationList = new ArrayList<>();

        myExperienceList = new ArrayList<>();

        myQualification = new ArrayList<>();

        myExpList = new ArrayList<>();

        myQualification = myPrefs.getQualificaiton();

        mySkillEDT = (TextInputEditText) aView.findViewById(R.id.profileSkillEDT);

        myQualificationEDT = (TextInputEditText) aView.findViewById(R.id.profileQualificaitonEDT);

        myPortfolioEDT = (TextInputEditText) aView.findViewById(R.id.profilePortfolioEDT);

        myExperienceEDT = (TextInputEditText) aView.findViewById(R.id.profileExperienceEDT);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        mySaveBTN = (Button) aView.findViewById(R.id.profileProfSaveBTN);

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myFullTimeRB = (RadioButton) aView.findViewById(R.id.unemployee_RB);

        myContractRB = (RadioButton) aView.findViewById(R.id.employee_RB);

        myEductionTXT = (TextView) aView.findViewById(R.id.add_education_TXT);

        myExperienceTXT = (TextView) aView.findViewById(R.id.add_experience_TXT);

        myEducationRC = (RecyclerView) aView.findViewById(R.id.education_RC);

        myExperienceRC = (RecyclerView) aView.findViewById(R.id.experience_RC);

        myJobTypeSTR = "0";

        myFullTimeRB.setChecked(true);

        myLPWindow = new ListPopupWindow(myContext);

        myUSerInfo = new UserInfo();

        setHeader();

        getBundleValues();

        clickListener();

        toLoadExp();

        toLoadList();

        toLoadExperiencList();

        getCurrentDate();

    }

    private void toLoadExp() {
        if (JSHelper.CheckInternet(myContext)) {
            getExperienceDetails();
        }
    }

    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.label_edit_profile));
    }

    public void getExperienceDetails() {

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Qualification>> call = myAPIInterface.getExperience();

        call.enqueue(new Callback<ArrayList<Qualification>>() {
            @Override
            public void onResponse(Call<ArrayList<Qualification>> call, Response<ArrayList<Qualification>> response) {

                Log.e("RESPONSE", response.message());

                myExperienceList = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<Qualification>> call, Throwable t) {

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

    @Override
    public void setData() {

        if (myAdapter != null) {

            String aType = myAdapter.getMyType();

            if (!aType.equals("")) {

                if (aType.equals(QUALI)) {

                    myQualificationEDT.setText(myAdapter.getQualification());

                    myPrefs.putQualificatonID(myAdapter.getQualificationID());


                } else if (aType.equals(EXPER)) {

                    myExperienceEDT.setText(myAdapter.getQualification());

                    myPrefs.putExpID(myAdapter.getQualificationID());

                }
            }


        } else {

            myQualificationEDT.setHint(myContext.getResources().getString(R.string.qualification));

        }

    }


    private void clickListener() {

        myEductionTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (JSHelper.CheckInternet(myContext)) {
                    showEducationDialog();

                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }
            }
        });

        myExperienceTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (JSHelper.CheckInternet(myContext)) {
                    showExperienceDialog();
                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }

            }
        });

        myBackIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContext.onBackPressed();
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

        myFullTimeRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myJobTypeSTR = "0";
                }


            }
        });


        myContractRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    myJobTypeSTR = "1";
                }
            }
        });

    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    private String getEditTextValueEDT(EditText aEditText) {
        return aEditText.getText().toString().trim();
    }


    private String getTextViewValue(TextView aEditText) {
        return aEditText.getText().toString().trim();
    }

    private void getValues() {

        mySkillSTR = getEditTextValue(mySkillEDT);

        myExperienceSTR = myPrefs.getExpID();

        myQualificationSTR = myPrefs.getQualificationID();

        myPortfolioSTR = getEditTextValue(myPortfolioEDT);

        myJobValueSTR = myJobTypeSTR;


    }

    private boolean validationForUpdate() {
        if (getEditTextValue(myQualificationEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_QUALIFICATION);
            return false;
        } else if (getEditTextValue(mySkillEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_SKILL);
            return false;
        } else if (getEditTextValue(myExperienceEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_CHOOSE_EXPE);
            return false;
        } else {
            return true;
        }
    }

    private void getBundleValues() {

        Bundle bundle = getArguments();

        if (bundle != null) {

            Gson aGson = new Gson();

            String aUserInfo = bundle.getString("USERINFO");

            myUSerInfo = new Gson().fromJson(aUserInfo, UserInfo.class);

            mySkillEDT.setText(myUSerInfo.getSKILLS());

            myQualificationEDT.setText(myUSerInfo.getQualicationId());

            myExperienceEDT.setText(myUSerInfo.getExperienceId());

            myPortfolioEDT.setText(myUSerInfo.getPortFolio());

            if (myUSerInfo.getCurrentStatus().equals("1")) {
                myContractRB.setChecked(true);
            } else if (myUSerInfo.getCurrentStatus().equals("0")) {
                myFullTimeRB.setChecked(true);
            } else {
                myFullTimeRB.setChecked(true);

            }

            // myUserId = "" + myUserInfo.getId();

        }


    }

    public void callUpdateProfileDetails() {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileData> call = myAPIInterface.signUpService(myPrefs.getUserID(), "", "", "",
                "", "", "",
                myQualificationSTR, mySkillSTR, myExperienceSTR,
                myPortfolioSTR, "", "false",
                "false", "", "", "",
                "", "", PROFESSIONAL_PROFILE_UPDATE, UPDATE, myJobValueSTR);

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

                        myPrefs.putUserInfo(aProfile.getUserInfo());

                        myContext.onBackPressed();

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


    private void showEducationDialog() {

        final ArrayList<Education> aEducationList = new ArrayList<>();

        // custom dialog
        final Dialog aDialog = new Dialog(myContext);
        aDialog.setContentView(R.layout.inflate_education_lay);
        //  aDialog.setTitle("Title...");
        aDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // set the custom dialog components - text, image and button
        final EditText aDegreeEDT = (EditText) aDialog.findViewById(R.id.degree_EDT);
        final EditText aCollegeEDT = (EditText) aDialog.findViewById(R.id.clg_EDT);
        final EditText aFieldEDT = (EditText) aDialog.findViewById(R.id.field_EDT);
        final EditText aCityEDT = (EditText) aDialog.findViewById(R.id.city_EDT);
        final TextView aFromMonthEDT = (TextView) aDialog.findViewById(R.id.from_mnt_EDT);
        final TextView aToMonthEDT = (TextView) aDialog.findViewById(R.id.to_year_EDT);
        TextView aCancelTXT = (TextView) aDialog.findViewById(R.id.cancel_TXT);
        final TextView aSaveTXT = (TextView) aDialog.findViewById(R.id.save_TXT);


        aFromMonthEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSHelper.hideSoftKeyboard(myContext, aFromMonthEDT);
                showDatePicker(aFromMonthEDT);
            }
        });

        aToMonthEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSHelper.hideSoftKeyboard(myContext, aFromMonthEDT);
                showDatePicker(aToMonthEDT);
            }
        });

        aCancelTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();

            }
        });
        aSaveTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Education aEdu = new Education();

                if (!getEditTextValueEDT(aDegreeEDT).equals("")) {
                    aEdu.setDegree(getEditTextValueEDT(aDegreeEDT));
                }
                if (!getEditTextValueEDT(aCollegeEDT).equals("")) {
                    aEdu.setCollege(getEditTextValueEDT(aCollegeEDT));
                }
                if (!getEditTextValueEDT(aFieldEDT).equals("")) {
                    aEdu.setDepartment(getEditTextValueEDT(aFieldEDT));
                }
                if (!getEditTextValueEDT(aCityEDT).equals("")) {
                    aEdu.setCity(getEditTextValueEDT(aCityEDT));
                }
                if (!getTextViewValue(aFromMonthEDT).equals("")) {
                    aEdu.setFrom(getTextViewValue(aFromMonthEDT));
                }
                if (!getTextViewValue(aToMonthEDT).equals("")) {
                    aEdu.setTo(getTextViewValue(aToMonthEDT));
                }

                aEducationList.add(aEdu);

                myEducationList.addAll(aEducationList);

                myPrefs.putEducationList(myEducationList);

                toLoadList();

                aDialog.dismiss();

                convertJSONEducation();
            }
        });

        aDegreeEDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    aSaveTXT.setVisibility(View.VISIBLE);
                } else {
                    aSaveTXT.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aDialog.show();
    }


    private void showExperienceDialog() {

        final ArrayList<Experience> aExpList = new ArrayList<>();


        final Boolean[] aValue = {false};
        // custom dialog
        final Dialog aDialog = new Dialog(myContext);
        aDialog.setContentView(R.layout.inflate_experience_lay);
        //  aDialog.setTitle("Title...");
        aDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // set the custom dialog components - text, image and button
        final EditText aDegreeEDT = (EditText) aDialog.findViewById(R.id.degree_EDT);
        final EditText aCollegeEDT = (EditText) aDialog.findViewById(R.id.clg_EDT);
        final EditText aFieldEDT = (EditText) aDialog.findViewById(R.id.desc_EDT);
        final EditText aCityEDT = (EditText) aDialog.findViewById(R.id.city_EDT);
        final TextView aFromMonthEDT = (TextView) aDialog.findViewById(R.id.from_mnt_EDT);
        final TextView aToMonthEDT = (TextView) aDialog.findViewById(R.id.to_year_EDT);
        TextView aCancelTXT = (TextView) aDialog.findViewById(R.id.cancel_TXT);
        final TextView aSaveTXT = (TextView) aDialog.findViewById(R.id.save_TXT);
        CheckBox aCurrentWrkCH = (CheckBox) aDialog.findViewById(R.id.currently_wr_CH);

        aCurrentWrkCH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    aValue[0] = isChecked;
                    aToMonthEDT.setText("Present");
                    aToMonthEDT.setEnabled(false);
                } else {
                    aValue[0] = isChecked;
                    aToMonthEDT.setHint("To");
                    aToMonthEDT.setText("");
                    aToMonthEDT.setEnabled(true);

                }
            }
        });
        aFromMonthEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSHelper.hideSoftKeyboard(myContext, aFromMonthEDT);

                showDatePicker(aFromMonthEDT);
            }
        });

        aToMonthEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSHelper.hideSoftKeyboard(myContext, aFromMonthEDT);
                showDatePicker(aToMonthEDT);
            }
        });

        aCancelTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aDialog.dismiss();

            }
        });
        aSaveTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Experience aEdu = new Experience();

                if (!getEditTextValueEDT(aDegreeEDT).equals("")) {
                    aEdu.setJob(getEditTextValueEDT(aDegreeEDT));
                }
                if (!getEditTextValueEDT(aCollegeEDT).equals("")) {
                    aEdu.setCompany(getEditTextValueEDT(aCollegeEDT));
                }
              /*  if (!getEditTextValueEDT(aFieldEDT).equals("")) {
                    aEdu.setDescription(getEditTextValueEDT(aFieldEDT));
                }*/
                if (!getEditTextValueEDT(aCityEDT).equals("")) {
                    aEdu.setCity(getEditTextValueEDT(aCityEDT));
                }
                if (!getTextViewValue(aFromMonthEDT).equals("")) {
                    aEdu.setFrom(getTextViewValue(aFromMonthEDT));
                }
                if (!getTextViewValue(aToMonthEDT).equals("")) {
                    aEdu.setTo(getTextViewValue(aToMonthEDT));
                }
                if (aValue[0]) {
                    aEdu.setCurrentCompany("1");

                } else {
                    aEdu.setCurrentCompany("0");

                }

                aExpList.add(aEdu);

                myExpList.addAll(aExpList);

                myPrefs.putExperienceList(myExpList);

                toLoadExperiencList();

                aDialog.dismiss();

                convertJSONExperience();
            }
        });

        aDegreeEDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    aSaveTXT.setVisibility(View.VISIBLE);
                } else {
                    aSaveTXT.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        aDialog.show();
    }

    private void toLoadList() {


        if (myPrefs.getEducationList() != null) {
            myEducationList = myPrefs.getEducationList();

        } else {

            myEducationList = new ArrayList<>();

        }


        if (myEducationList != null)

        {
            if (myEducationList.size() > 0) {
                myEduAdapter = new SampleAdapter(myContext, myEducationList);
                myEducationRC.setAdapter(myEduAdapter);
                LinearLayoutManager llm = new LinearLayoutManager(myContext);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                myEducationRC.setLayoutManager(llm);

            }
        }

    }

    private void toLoadExperiencList() {

        if (myPrefs.getExperienceList() != null) {
            myExpList = myPrefs.getExperienceList();

        } else {

            myExpList = new ArrayList<>();

        }

        if (myExpList != null) {
            if (myExpList.size() > 0) {
                myExpAdpter = new SampleExpAdapter(myContext, myExpList);
                myExperienceRC.setAdapter(myExpAdpter);
                LinearLayoutManager llm = new LinearLayoutManager(myContext);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                myExperienceRC.setLayoutManager(llm);

            }
        }

    }

    private void convertJSONEducation() {

        try {

            JSONObject aMainJsonObj = new JSONObject();

            JSONArray aJsonMainArray = new JSONArray();

            if (myEducationList.size() > 0) {

                for (int k = 0; k < myEducationList.size(); k++) {

                    JSONObject aJsonObj = new JSONObject();

                    Education aEdu = myEducationList.get(k);

                    aJsonObj.put("Degree", aEdu.getDegree());

                    aJsonObj.put("College", aEdu.getCollege());

                    aJsonObj.put("Department", aEdu.getDepartment());

                    aJsonObj.put("From", aEdu.getFrom());

                    aJsonObj.put("City", aEdu.getCity());

                    aJsonObj.put("To", aEdu.getTo());

                    aJsonMainArray.put(aJsonObj);
                }


                aMainJsonObj.put("EDUCATION", aJsonMainArray);

                Log.e("VALUEEDC", aMainJsonObj.toString());
            }

            saveEducationExperience("EDU", aMainJsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void convertJSONExperience() {

        try {

            JSONObject aMainJsonObj = new JSONObject();

            JSONArray aJsonMainArray = new JSONArray();

            if (myExpList.size() > 0) {

                for (int k = 0; k < myExpList.size(); k++) {

                    JSONObject aJsonObj = new JSONObject();

                    Experience aExp = myExpList.get(k);

                    aJsonObj.put("Job", aExp.getJob());

                    aJsonObj.put("Company", aExp.getCompany());

                    aJsonObj.put("City", aExp.getCity());

                    aJsonObj.put("From", aExp.getFrom());

                    aJsonObj.put("To", aExp.getTo());

                    aJsonObj.put("CurrentCompany", aExp.getCurrentCompany());

                    aJsonMainArray.put(aJsonObj);
                }


                aMainJsonObj.put("EXPERIENCE", aJsonMainArray);

                Log.e("VALUEEDC", aMainJsonObj.toString());
            }

            //String aExpValue = aMainJsonObj.toString().replaceAll(" ", "%20");

            saveEducationExperience("EXP", aMainJsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void showDatePicker(final TextView aEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(myContext,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        aEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();


    }

    public void saveEducationExperience(String aType, String aJson) {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = null;

        if (aType.equals("EDU")) {
            call = myAPIInterface.saveEducationDetails(myPrefs.getUserID(), aJson);
        } else if (aType.equals("EXP")) {
            call = myAPIInterface.saveExperienceDetails(myPrefs.getUserID(), aJson);
        }
        //Call<ProfileData> call = myAPIInterface.updateBasicInfo(myQualificationSTR, myExperienceSTR, mySkillSTR, myPortfolioSTR);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                toDismissDialog();

              /*
                switch (aResponseCode) {

                    case RESPONSE_CODE_SUCCESS:

                        toDismissDialog();

                        myPrefs.putUserInfo(aProfile.getUserInfo());

                        myContext.onBackPressed();

                        break;

                    case RESPONSE_CODE_FAILURE:

                        toDismissDialog();

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;

                    default:

                        toDismissDialog();

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }
}
