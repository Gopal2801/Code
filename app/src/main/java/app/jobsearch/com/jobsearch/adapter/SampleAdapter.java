package app.jobsearch.com.jobsearch.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Education;
import app.jobsearch.com.jobsearch.model.Experience;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SampleAdapter extends
        RecyclerView.Adapter<SampleAdapter.MyViewHolder> implements ConstantValues {

    private ArrayList<Education> myList;

    private FragmentActivity myContext;

    private ArrayList<Experience> myExpList;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private ArrayList<Education> myEducationList;

    private ProgressDialog myDialog;

    private ApiInterface myAPIInterface;

    private Preference myPrefs;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView myTitleTXT, mySecondTXT, myThirdTXT, myPeriodTXT;

        public MyViewHolder(View view) {
            super(view);
            myTitleTXT = (TextView) view.findViewById(R.id.title_TXT);
            mySecondTXT = (TextView) view.findViewById(R.id.second_TXT);
            myThirdTXT = (TextView) view.findViewById(R.id.third_XT);
            myPeriodTXT = (TextView) view.findViewById(R.id.period_TXT);

        }
    }

    public SampleAdapter(FragmentActivity aContext, ArrayList<Education> countryList) {
        myContext = aContext;
        this.myList = countryList;
        myEducationList = new ArrayList<>();
        myPrefs = new Preference(myContext);
        getCurrentDate();

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Education aAttend = myList.get(position);
        if (!aAttend.getDepartment().equals("")) {
            holder.myTitleTXT.setText(aAttend.getDegree() + " in " + aAttend.getDepartment());
        } else {
            holder.myTitleTXT.setText(aAttend.getDegree());
        }
        if (!aAttend.getCollege().equals("")) {
            holder.mySecondTXT.setText(aAttend.getCollege());

        } else {
            holder.mySecondTXT.setVisibility(View.GONE);

        }
        if (!aAttend.getTo().equals("")) {
            holder.myPeriodTXT.setText(aAttend.getFrom() + " to " + aAttend.getTo());
        } else {
            holder.myTitleTXT.setText(aAttend.getDegree());
        }

        holder.myThirdTXT.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_education_details, parent, false);
        return new MyViewHolder(v);
    }


    private void showEducationDialog() {
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

                myEducationList.add(aEdu);

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


    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    private String getEditTextValueEDT(EditText aEditText) {
        return aEditText.getText().toString().trim();
    }


    private String getTextViewValue(TextView aEditText) {
        return aEditText.getText().toString().trim();
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

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
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


    public void saveEducationExperience(String aType, String aJson) {

        myDialog = new ProgressDialog(myContext);

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

    private void toDismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }
}
