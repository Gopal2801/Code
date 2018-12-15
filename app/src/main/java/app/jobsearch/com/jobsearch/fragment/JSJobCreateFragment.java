package app.jobsearch.com.jobsearch.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.master.permissionhelper.PermissionHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.activity.SampleActivity;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSJobCreateFragment extends JSFragment implements ConstantValues {

    public static final String TAG = "JSJobCreateFragment";

    private TextInputEditText myJobNameEDT, myJobDesignationEDT, myJobDescEDT,
            myJobBudgetsEDT, myJobReqEDT, myNoOfVacancyEDT, myCompanyNameEDT, myDeadLineEDT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM;

    private ApiInterface myAPIInterface;

    private ProgressDialog myDialog;

    private String myJobNameSTR = "", myJobDesignationSTR = "", myJobDescSTR = "",
            myJobBudgetsSTR = "", myJobReqSTR = "", myNoVacancySTR = "", myCompanyNameSTR = "",
            myJobTypeSTR = "", myDeadLineSTR = "", myCompanyFileSTR = "";

    private Button myCreateBTN;

    private TextView myHeaderTXT;

    private ImageView myBackIM, myCompanyProifleIM;

    private String myKey = "";

    private PermissionHelper myPermissionHelper;

    private RadioButton myFullTimeRB, myContractRB;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override

    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_create_job,
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

        myPref = new Preference(myContext);

        myDialog = new ProgressDialog(myContext);

        myJobNameEDT = (TextInputEditText) aView.findViewById(R.id.jobnameEDT);

        myCompanyNameEDT = (TextInputEditText) aView.findViewById(R.id.companyNameEDT);

        myCompanyProifleIM = (ImageView) aView.findViewById(R.id.companyProfileIM);

        myJobDesignationEDT = (TextInputEditText) aView.findViewById(R.id.jobdesignationEDT);

        myJobDescEDT = (TextInputEditText) aView.findViewById(R.id.jobdescriptionEDT);

        myJobBudgetsEDT = (TextInputEditText) aView.findViewById(R.id.jobbudgetEDT);

        myJobReqEDT = (TextInputEditText) aView.findViewById(R.id.jobreqEDT);

        myNoOfVacancyEDT = (TextInputEditText) aView.findViewById(R.id.noofvacancyEDT);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        myCreateBTN = (Button) aView.findViewById(R.id.jobcreateBTN);

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myFullTimeRB = (RadioButton) aView.findViewById(R.id.full_time_RB);

        myContractRB = (RadioButton) aView.findViewById(R.id.contract_time_RB);

        myDeadLineEDT = (TextInputEditText) aView.findViewById(R.id.deadLineEDT);

        myJobTypeSTR = "F";

        myFullTimeRB.setChecked(true);

        getCurrentDate();

        setHeader();

        setFocusListener();

        clickListener();
    }

    private void setFocusListener() {


        myNoOfVacancyEDT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    JSHelper.hideSoftKeyboard(myContext, myNoOfVacancyEDT);
                    showDatePicker();
                    //implement stuff here
                }
                return false;
            }
        });


    }

    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.label_create_job));
    }

    private void getBundleValues() {

        Bundle bundle = getArguments();

        if (bundle != null) {

            String aKEY = bundle.getString("KEY");

            if (aKEY.equals("BACK")) {
                myKey = aKEY;
            } else {
                myKey = aKEY;
            }
            // myUserID = "" + myUSerInfo.getId();


        } else {
            myKey = "";
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getBundleValues();

    }

    private void clickListener() {

        myBackIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!myKey.equals("")) {

                    myContext.onBackPressed();

                    // myFragmentManager.updateContent(new JSJobListFragment(), JSJobListFragment.TAG, null);

                } else {
                    ((SampleActivity) myContext).openMenu();

                }
            }
        });


        myCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // myFragmentManager.updateContent(new JSJobListFragment(), JSJobListFragment.TAG, null);

                if (validationForUpdate()) {

                    if (JSHelper.CheckInternet(myContext)) {

                        getValues();

                        updateJobDetails();

                    } else {

                        JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                    }
                }

            }
        });
        myCompanyProifleIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //askPermission();
                if ((ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        askPermission();
                    }

                } else {

                    EasyImage.openChooserWithGallery(JSJobCreateFragment.this, "Select", 0);

                }

            }
        });

        myFullTimeRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    myJobTypeSTR = "F";
                }
            }
        });
        myContractRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    myJobTypeSTR = "C";
                }
            }
        });

        myDeadLineEDT.setFocusableInTouchMode(false);
        myDeadLineEDT.setFocusable(false);
        myDeadLineEDT.setOnClickListener(new View.OnClickListener() {
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
                        myDeadLineEDT.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();


    }

    private void getValues() {

        myCompanyNameSTR = getEditTextValue(myCompanyNameEDT);

        myJobNameSTR = getEditTextValue(myJobNameEDT);

        myJobDesignationSTR = getEditTextValue(myJobDesignationEDT);

        myJobDescSTR = getEditTextValue(myJobDescEDT);

        myJobBudgetsSTR = getEditTextValue(myJobBudgetsEDT);

        myJobReqSTR = getEditTextValue(myJobReqEDT);

        myNoVacancySTR = getEditTextValue(myNoOfVacancyEDT);

        myDeadLineSTR = getEditTextValue(myDeadLineEDT);

        myDeadLineSTR = myDeadLineSTR.replace("/", "-");

    }

    private void askPermission() {

        myPermissionHelper = new PermissionHelper(myContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        myPermissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Log.d("Per", "onPermissionGranted() called");
            }

            @Override
            public void onIndividualPermissionGranted(String[] grantedPermission) {
                Log.d("Per", "onIndividualPermissionGranted() called with: grantedPermission = [" + TextUtils.join(",", grantedPermission) + "]");
            }

            @Override
            public void onPermissionDenied() {
                Log.d("Per", "onPermissionDenied() called");
            }

            @Override
            public void onPermissionDeniedBySystem() {
                Log.d("Per", "onPermissionDeniedBySystem() called");
            }
        });
    }

    private boolean validationForUpdate() {
        if (getEditTextValue(myJobNameEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_NAME);
            return false;
        } else if (getEditTextValue(myJobDesignationEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_DESIGNATION);
            return false;
        } else if (getEditTextValue(myJobDescEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_DESCP);
            return false;
        } else if (getEditTextValue(myJobBudgetsEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_BUDG);
            return false;
        } else if (getEditTextValue(myJobReqEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_REQ);
            return false;
        } else if (getEditTextValue(myNoOfVacancyEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_JOB_VACANCY);
            return false;
        } else {
            return true;
        }
    }

    public void createJob() {

        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = myAPIInterface.createJob("", myPref.getUserID(), myJobNameSTR, myJobDesignationSTR, myJobDescSTR,
                myJobBudgetsSTR, "", myJobReqSTR, myNoVacancySTR, INSERT);

        //Call<ProfileData> call = myAPIInterface.updateBasicInfo(myQualificationSTR, myExperienceSTR, mySkillSTR, myPortfolioSTR);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                ResponseBody aProfile = response.body();

                String aResponseCode = "" + response.code();

                String aResponseMsg = response.message();

                switch (aResponseCode) {

                    case RESPONSE_CODE_CREATE_JOB_SUCCESS:

                        toDismissDialog();

                        showAlertDialog(ALERT_JOB_SUCCESS);

                        //  myContext.onBackPressed();

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
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    public void showAlertDialog(String aMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
            builder.setMessage(aMessage)
                    .setTitle(APP_NAME)
                    .setCancelable(false)
                    .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();

                            if (myKey.equals("BACK1")) {
                                toCallProfileList();
                            } else {
                                toCallJobList();

                            }
                            //   toEmptyField();
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

    private void toCallJobList() {

        // myFragmentManager.clearAllFragments();

        myFragmentManager.removeFragment(1);

        myFragmentManager.updateContent(new JSJobListFragment(), JSJobListFragment.TAG, null);

    }

    private void toCallProfileList() {

        // myFragmentManager.clearAllFragments();

        myFragmentManager.removeFragment(1);

        myFragmentManager.updateContent(new JSDummyPage(), JSDummyPage.TAG, null);

    }

    private void toEmptyField() {

        myJobNameEDT.setText("");

        myJobDesignationEDT.setText("");

        myJobDescEDT.setText("");

        myJobBudgetsEDT.setText("");

        myJobReqEDT.setText("");

        myNoOfVacancyEDT.setText("");
    }

    private void toDismissDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

    private String getEditTextValue(TextInputEditText aEditText) {
        return aEditText.getText().toString().trim();
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, myContext, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFile);
// Do image process
            }


        });
    }

    private void onPhotosReturned(File imageFile) {

        if (imageFile.exists()) {

            myCompanyProifleIM.setImageBitmap(null);

            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            myCompanyProifleIM.setImageBitmap(myBitmap);

            myCompanyFileSTR = imageFile.getAbsolutePath();


        }
    }


    public void updateJobDetails() {

        Log.e("File", myCompanyFileSTR);

        File compressedImageFile = null;

        if (!myCompanyFileSTR.equals("")) {

            File aFile = new File(myCompanyFileSTR);

            try {
                compressedImageFile = new Compressor(myContext).compressToFile(aFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClientUpload().create(ApiInterface.class);

       /* // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(myContext.getContentResolver().getType(Uri.fromFile(aFile)
                        )),
                        aFile
                );*/
        Call<ResponseBody> call;

        if (compressedImageFile != null) {

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), compressedImageFile);


            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part aBody =
                    MultipartBody.Part.createFormData("PImage", "image", requestFile);


            call = myAPIInterface.createJob(aBody, "", myPref.getUserID(), myCompanyNameSTR, myJobNameSTR, myJobTypeSTR, myJobDesignationSTR, myJobDescSTR,
                    myJobBudgetsSTR, "", myJobReqSTR, myNoVacancySTR, myDeadLineSTR, INSERT);

        } else {


            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), "");


            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part aBody =
                    MultipartBody.Part.createFormData("PImage", "image", requestFile);

            call = myAPIInterface.createJob(aBody, "", myPref.getUserID(), myCompanyNameSTR, myJobNameSTR, myJobTypeSTR, myJobDesignationSTR, myJobDescSTR,
                    myJobBudgetsSTR, "", myJobReqSTR, myNoVacancySTR, myDeadLineSTR, INSERT);


          /*  call = myAPIInterface.createJobWithoutImage("", myPref.getUserID(), myCompanyNameSTR, myJobNameSTR, myJobTypeSTR, myJobDesignationSTR, myJobDescSTR,
                    myJobBudgetsSTR, "", myJobReqSTR, myNoVacancySTR, myDeadLineSTR, INSERT);
*/
        }


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                ResponseBody aProfile = response.body();

                String aResponseCode = "" + response.code();

                String aResponseMsg = response.message();

                switch (aResponseCode) {

                    case RESPONSE_CODE_CREATE_JOB_SUCCESS:

                        toDismissDialog();

                        showAlertDialog(ALERT_JOB_SUCCESS);

                        //  myContext.onBackPressed();

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
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toDismissDialog();

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

            }
        });
    }

    private void loadProfileImage() {

        JSHelper.loadImageView(myContext, myCompanyProifleIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);
    }

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

}
