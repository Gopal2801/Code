package app.jobsearch.com.jobsearch.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.master.permissionhelper.PermissionHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.JSFragment;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSEventCreateFragment extends JSFragment implements ConstantValues {

    public static String TAG = JSEventCreateFragment.class.getSimpleName().toString();

    private FragmentActivity myContext;

    private EditText myEventNameEDT, myEventVenueEDT, myEventDateEDT, myEventPlaceEDT;

    private ImageView myEventEditIM, myEvenVenuEditIM, myEventDateEditIM, myEventPlaceEditIM, myEventIM;

    private Button myCreateBTN;

    public String ALERT_FOR_EVENT_NAME = "Please enter the event name";

    public String ALERT_FOR_EVENT_VENUE = "Please enter the event venue";

    public String ALERT_FOR_EVENT_DATE = "Please enter the event date";

    public String ALERT_FOR_EVENT_PLACE = "Please enter the event place";

    public String ALERT_FOR_CHOOSE_IMAGE = "Please choose the event image";

    private RelativeLayout myEventIMLAY;

    private Preference myPref;

    private String myEventNameSTR = "", myEventVenueSTR = "", myEventDateSTR = "", myEventPlaceSTR = "", myTicketSTR = "";

    private SwitchCompat myTicketSwitch;

    private ApiInterface myAPIInterface;

    private ProgressDialog myDialog;

    private File myEventIMFile = null;

    private PermissionHelper myPermissionHelper;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private String myEventID = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fargment_event_page, viewGroup, false);

        classInitialization(view);

        return view;
    }

    private void classInitialization(View aView) {

        myPref = new Preference(myContext);

        myDialog = new ProgressDialog(myContext);

        myEventNameEDT = (EditText) aView.findViewById(R.id.eventName_EDT);

        myEventVenueEDT = (EditText) aView.findViewById(R.id.event_venue_EDT);

        myEventDateEDT = (EditText) aView.findViewById(R.id.event_date_EDT);

        myEventPlaceEDT = (EditText) aView.findViewById(R.id.event_place_EDT);

        myEventEditIM = (ImageView) aView.findViewById(R.id.event_name_edit_IM);

        myEventIM = (ImageView) aView.findViewById(R.id.event_IM);

        myEventIMLAY = (RelativeLayout) aView.findViewById(R.id.event_IM_LAY);


        myEvenVenuEditIM = (ImageView) aView.findViewById(R.id.event_venue_edit_IM);

        myEventDateEditIM = (ImageView) aView.findViewById(R.id.event_date_TXT_edit_IM);

        myEventPlaceEditIM = (ImageView) aView.findViewById(R.id.event_place_TXT_edit_IM);

        myTicketSwitch = (SwitchCompat) aView.findViewById(R.id.ticket_switch);

        myCreateBTN = (Button) aView.findViewById(R.id.event_create_BTN);

        getBundleValue();

        // disableEDT();

        getCurrentDate();

        enableEdit();

        clickListener();

        myTicketSTR = "0";
    }

    private void getBundleValue() {

        Bundle aBundle = getArguments();

        if (aBundle != null) {

            String aValue = aBundle.getString("KEY");


            if (aValue.equals("LIST")) {


            } else {


            }

        }
    }

    private void getCurrentDate() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void enableEdit() {

        myEventDateEDT.setText(JSHelper.getCurrentDate());
        myEventNameEDT.setEnabled(true);
        myEventVenueEDT.setEnabled(true);
        myEventDateEDT.setEnabled(true);
        myEventPlaceEDT.setEnabled(true);
        myEventEditIM.setVisibility(View.GONE);
        myEvenVenuEditIM.setVisibility(View.GONE);
        myEventDateEditIM.setVisibility(View.GONE);
        myEventPlaceEditIM.setVisibility(View.GONE);

    }

    private void disableEDT() {
        myEventNameEDT.setEnabled(false);
        myEventVenueEDT.setEnabled(false);
        myEventDateEDT.setEnabled(false);
        myEventPlaceEDT.setEnabled(false);

    }

    private void clickListener() {
        myEventEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventNameEDT);
            }
        });
        myEvenVenuEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventVenueEDT);
            }
        });
        myEventDateEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventDateEDT);
            }
        });
        myEventPlaceEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventPlaceEDT);
            }
        });
        myCreateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {

                    getValue();

                    if (myEventIMFile != null) {

                        postEventDetails();

                    } else {
                        JSHelper.showAlertDialog(myContext, ALERT_FOR_CHOOSE_IMAGE);

                    }

                }
            }
        });

        myTicketSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    myTicketSTR = "1";

                } else {

                    myTicketSTR = "0";
                }

            }
        });


        myEventIMLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //askPermission();
                if ((ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        askPermission1();
                    }

                } else {

                    EasyImage.openChooserWithGallery(myContext, "Select", 0);

                }
            }
        });

        myEventDateEDT.setFocusableInTouchMode(false);
        myEventDateEDT.setFocusable(false);
        myEventDateEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(myEventDateEDT);

            }
        });
    }

    private void getValue() {

        myEventNameSTR = getEditTextValue(myEventNameEDT);

        myEventVenueSTR = getEditTextValue(myEventVenueEDT);

        myEventDateSTR = getEditTextValue(myEventDateEDT);

        myEventPlaceSTR = getEditTextValue(myEventPlaceEDT);

        myTicketSTR = getEditTextValue(myEventNameEDT);


    }

    private void showDatePicker(final EditText aEditText) {
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

    private boolean validation() {

        if (getEditTextValue(myEventNameEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EVENT_NAME);
            return false;
        } else if (getEditTextValue(myEventVenueEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EVENT_VENUE);

            return false;
        } else if (getEditTextValue(myEventDateEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EVENT_DATE);

            return false;
        } else if (getEditTextValue(myEventPlaceEDT).equals("")) {
            JSHelper.showAlertDialog(myContext, ALERT_FOR_EVENT_PLACE);
            return false;
        } else {
            return true;
        }
    }

    private String getEditTextValue(EditText aEditText) {
        return aEditText.getText().toString().trim();

    }

    private void toEnableEDT(EditText aEditText) {
        aEditText.setEnabled(true);

    }

    //upload profile picture to server
    private void postEventDetails() {

        try {


            myDialog.show();

            myDialog.setCanceledOnTouchOutside(false);

            //ProgressDialog mProgressDialog = Util.showProgress(myContext, "Loading ...");

            //   mProgressDialog.hide();

            myAPIInterface =
                    ApiClient.getClient().create(ApiInterface.class);


            Call<ResponseBody> call;

            MultipartBody.Part body = multipartRequestForImage();

            call = myAPIInterface.addEvent(body, myEventID, myEventNameSTR, myEventVenueSTR, myEventDateSTR, myEventPlaceSTR, myTicketSTR, myPref.getUserID(), "insert");

            Log.e(" MediaUploadResponse - ", call.request().url().toString());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    myDialog.dismiss();

                    ResponseBody aBody = response.body();

                    String aResponseCode = "" + response.code();

                    switch (aResponseCode) {

                        case RESPONSE_CODE_SUCCESS:

                            break;
/*
                        case RESPONSE_CODE_BAD_REQ:


                            break;*/
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    myDialog.dismiss();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();


        }

    }  //set multipart request for uploading  and image

    private MultipartBody.Part multipartRequestForImage() {

      /*  Log.e("File", aFileStr);

        File aFile = new File(aFileStr);
*/
        File compressedImageFile = null;

        try {
            compressedImageFile = new Compressor(myContext).compressToFile(myEventIMFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultipartBody.Part body = null;

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), compressedImageFile);

        body = MultipartBody.Part.createFormData("EventImage", "image", requestFile);


        return body;
    }


    private void askPermission1() {

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

                myEventIMFile = imageFile;

                myEventIM.setImageBitmap(JSHelper.getBitMap(myEventIMFile));

                //      onPhotosReturned(imageFile);
// Do image process
            }


        });
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }
}