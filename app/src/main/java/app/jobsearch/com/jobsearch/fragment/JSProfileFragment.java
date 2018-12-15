package app.jobsearch.com.jobsearch.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.master.permissionhelper.PermissionHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import de.hdodenhof.circleimageview.CircleImageView;
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

import static android.content.Intent.EXTRA_TEXT;


public class JSProfileFragment extends JSFragment implements ConstantValues {

    public static final String TAG = JSProfileFragment.class.getSimpleName().toString();

    private TextView myUserNameTXT, myEmailTXT, myMobileNoTXT, myAddressTXT,
            myDOBTXT, myQulaificaitonTXT, mySkillTXT, myExpTXT, myPortFolioTXT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM, myProfEditIM, myProfileChooseIM;


    private CircleImageView myProfileCIM;

    private PermissionHelper myPermissionHelper;

    private ProgressDialog myDialog;

    private ApiInterface myAPIInterface;


    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragement_profile,
                    aContainer, false);

            classInitialization(aView);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // return the view
        return aView;
    }


    private void classInitialization(View aView) {

        myPref = new Preference(myContext);

        myDialog = new ProgressDialog(myContext);

        myFragmentManager = new JSFragmentManager(myContext);

        myUserNameTXT = (TextView) aView.findViewById(R.id.namevalueTXT);

        myEmailTXT = (TextView) aView.findViewById(R.id.emailvalueTXT);

        myMobileNoTXT = (TextView) aView.findViewById(R.id.mobilenoTxt);

        myDOBTXT = (TextView) aView.findViewById(R.id.dobvalueTxt);

        myAddressTXT = (TextView) aView.findViewById(R.id.addressvalueTxt);

        myQulaificaitonTXT = (TextView) aView.findViewById(R.id.QualificationValueTxt);

        mySkillTXT = (TextView) aView.findViewById(R.id.skillvalueTxt);

        myExpTXT = (TextView) aView.findViewById(R.id.experienceTxt);

        myPortFolioTXT = (TextView) aView.findViewById(R.id.portfolioTxt);

        myProfileChooseIM = (ImageView) aView.findViewById(R.id.editprofile_im);

        myEditIM = (ImageView) aView.findViewById(R.id.edit_im);

        myProfEditIM = (ImageView) aView.findViewById(R.id.editprofessional_im);

        myProfileCIM = (CircleImageView) aView.findViewById(R.id.profile_image);

        //setUserDetails();

        clickListener();

        ((SampleActivity) myContext).showActionBar();


    }

    private void clickListener() {

        myEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.updateContent(new JSProfileEditPage(), JSProfileEditPage.TAG, aBundle);

            }
        });
        myProfEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.clearOneFragment();

                myFragmentManager.updateContent(new JSProfileProfessionalEditPage(), JSProfileProfessionalEditPage.TAG, aBundle);
            }
        });

        myProfileChooseIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //askPermission();
                if ((ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        askPermission();
                    }

                } else {

                    EasyImage.openChooserWithGallery(JSProfileFragment.this, "Select", 0);

                }


            }
        });

        myProfileCIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //askPermission();
                if ((ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        askPermission();
                    }

                } else {

                    EasyImage.openChooserWithGallery(JSProfileFragment.this, "Select", 0);

                }


            }
        });
    }

    private void setUserDetails() {

        myUserInfo = myPref.getUserInfo();

        myPref.putUserID("" + myUserInfo.getId());

        //Basic Details

        myUserNameTXT.setText(myUserInfo.getName());

        myEmailTXT.setText(myUserInfo.getMailId());

        myMobileNoTXT.setText(myUserInfo.getMobileNo());

        myDOBTXT.setText(myUserInfo.getDOB());

        myAddressTXT.setText("" + myUserInfo.getAddress());

        //Professional Details

        mySkillTXT.setText(myUserInfo.getSKILLS());

        myQulaificaitonTXT.setText(myUserInfo.getQualicationId());

        myExpTXT.setText(myUserInfo.getExperienceId());

        myPortFolioTXT.setText(myUserInfo.getPortFolio());

        if (myUserInfo.getProfileImage() != null) {

            String aImage = myUserInfo.getProfileImage();

            if (!aImage.equals("")) {

                myPref.putProfileImage(IMAGE_URL + aImage);

            } else {

                myPref.putProfileImage("");

            }
        }
//Set Profile image
        loadProfileImage();


    }

    private void loadProfileImage() {

        JSHelper.loadImageView(myContext, myProfileCIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);
    }

    @Override
    public void onResume() {
        setUserDetails();
        super.onResume();
    }

    @Override
    public boolean onResumeFragment() {
        //  setUserDetails();
        // ((SampleActivity) myContext).showActionBar();
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

            myProfileCIM.setImageBitmap(null);

            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            myProfileCIM.setImageBitmap(myBitmap);

//For uploading the image

            if (JSHelper.CheckInternet(myContext)) {

                updateJobDetails(imageFile.getAbsolutePath());

            } else {
                JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

            }

        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (myPermissionHelper != null) {
            myPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void updateJobDetails(String aFileStr) {

        Log.e("File", aFileStr);

        File aFile = new File(aFileStr);

        File compressedImageFile = null;

        try {
            compressedImageFile = new Compressor(myContext).compressToFile(aFile);
        } catch (IOException e) {
            e.printStackTrace();
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
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), compressedImageFile);


        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part aBody =
                MultipartBody.Part.createFormData("PImage", "image", requestFile);

        Call<ProfileData> call = myAPIInterface.uploadProfileImage(aBody, myPref.getUserID());


        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {

                Log.e("RESPONSE", response.message());

                Log.e("RESPONSEURL", "" + response.raw().request().url());

                ProfileData aProfile = response.body();

                String aResponseCode = "" + response.code();

                String aResponseMsg = response.message();

                switch (aResponseCode) {

                    case RESPONSE_CODE_CREATE_JOB_SUCCESS:

                        if (aProfile.getUserInfo().getProfileImage() != null) {

                            myPref.putProfileImage(IMAGE_URL + aProfile.getUserInfo().getProfileImage());
                        }

                        loadProfileImage();

                        toDismissDialog();

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
