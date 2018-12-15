package app.jobsearch.com.jobsearch.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.master.permissionhelper.PermissionHelper;
import com.ramotion.foldingcell.FoldingCell;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.adapter.FoldingCellListAdapter;
import app.jobsearch.com.jobsearch.adapter.FoldingMainAdapter;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Item;
import app.jobsearch.com.jobsearch.model.JobList;
import app.jobsearch.com.jobsearch.model.JobList_;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSDummyPage extends JSFragment implements ConstantValues {

    public static final String TAG = JSDummyPage.class.getSimpleName().toString();

    private TextView myUserNameTXT, myEmailTXT, myMobileNoTXT, myAddressTXT, myDOBTXT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM;

    private ListView myProfileLV, myCurrentUserLV;

    private ArrayList<JobList_> myJobList;

    private CardView myCardFistView;

    private ApiInterface myAPIInterface;

    private ArrayList<JobList_> myCurrentJobList;

    private ProgressDialog myDialog;

    private RelativeLayout myNoDataLAY;

    private TextView myAlertTXT;

    private FloatingActionButton myAddBTN;

    private Boolean myCheck = false;

    private FoldingCell myFoldingCell;

    private FrameLayout myLayout, myFoldingContent;


    //First Cut lay
    TextView myFirstNameTXT, myFirstQualificationTXT, myDesignationTXT, myYearOfExpTXT,
            myLookingStatusTXT, myMainDesginTXT, myMainSillTXT, myMainNameTXT, myMainEmpStatusTXT;

    //Second Cut lay
    TextView myNameTXT, myFEmailTXT, myFMobileNoTXT, myDobTXT,
            myFAddressTXT, myNationalityTXT, myBirthPlaceTXT, myAadharNoTXT, myInterestTXT, myHobbyTXT, myEmpStatusTXT;

    //Third Cut lay
    TextView myQualificationTXT, mySkillTXT, myExperienceTXT, myPortfolioTXT;

    ImageView myProfileIM, myProfileChooseIM, myEditPersonalIM, myEditProfessionalIM, myFirstCutProfileIM, mySecondProfileIM;

    RelativeLayout mySubProfileLAY;

    private PermissionHelper myPermissionHelper;


    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_profile_alpha,
                    aContainer, false);

            classInitialization(aView);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // return the view
        return aView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserDetails();
        showContentView();

    }

    private void classInitialization(View aView) {

        myDialog = new ProgressDialog(myContext);

        myFragmentManager = new JSFragmentManager(myContext);

        myPref = new Preference(myContext);

        myProfileLV = (ListView) aView.findViewById(R.id.profile_rc);

        myCurrentUserLV = (ListView) aView.findViewById(R.id.current_user_LV);

        myNoDataLAY = (RelativeLayout) aView.findViewById(R.id.no_record_LAY);

        myAlertTXT = (TextView) aView.findViewById(R.id.alert_txt);

        myAddBTN = (FloatingActionButton) aView.findViewById(R.id.no_data_BTN);

        myFoldingCell = (FoldingCell) aView.findViewById(R.id.folding_cell);

        myFoldingContent = (FrameLayout) aView.findViewById(R.id.cell_content_view);

        myLayout = (FrameLayout) aView.findViewById(R.id.cell_title_view);


        mySubProfileLAY = (RelativeLayout) aView.findViewById(R.id.profile_main_lay);
        myMainNameTXT = (TextView) aView.findViewById(R.id.profile_name_TXT);
        myMainDesginTXT = (TextView) aView.findViewById(R.id.profile_qual1_TXT);
        myMainSillTXT = (TextView) aView.findViewById(R.id.profile_sill_TXT);
        myMainEmpStatusTXT = (TextView) aView.findViewById(R.id.employee_status_TXT);


        //..................................First Cut...........................................//
        myFirstNameTXT = (TextView) aView.findViewById(R.id.first_cut_profile_name_TXT);
        myDesignationTXT = (TextView) aView.findViewById(R.id.first_cut_profile_designation_TXT);
        myFirstQualificationTXT = (TextView) aView.findViewById(R.id.first_cut_profile_qual_TXT);
        myYearOfExpTXT = (TextView) aView.findViewById(R.id.first_cut_profile_experience_TXT);
        myLookingStatusTXT = (TextView) aView.findViewById(R.id.first_cut_profile_luking_status_TXT);
        myProfileIM = (ImageView) aView.findViewById(R.id.profileChooseIM);
        myFirstCutProfileIM = (ImageView) aView.findViewById(R.id.first_cut_profileIM);
        myEmpStatusTXT = (TextView) aView.findViewById(R.id.first_cut_profile_employee_status_TXT);

        //..................................First Cut...........................................//

        //..................................Second Cut...........................................//
        myNameTXT = (TextView) aView.findViewById(R.id.second_cut_profile_name_TXT);
        myFEmailTXT = (TextView) aView.findViewById(R.id.second_cut_email_TXT);
        myFMobileNoTXT = (TextView) aView.findViewById(R.id.second_cut_mobile_no_TXT);
        myDobTXT = (TextView) aView.findViewById(R.id.second_cut_dob_TXT);
        myFAddressTXT = (TextView) aView.findViewById(R.id.second_cut_address_TXT);
        myProfileChooseIM = (ImageView) aView.findViewById(R.id.profilesubChooseIM);
        myEditPersonalIM = (ImageView) aView.findViewById(R.id.personal_edit_im);
        mySecondProfileIM = (ImageView) aView.findViewById(R.id.second_cut_profileIM);
        myNationalityTXT = (TextView) aView.findViewById(R.id.second_cut_nationality_TXT);
        myBirthPlaceTXT = (TextView) aView.findViewById(R.id.second_cut_birth_place_TXT);
        myAadharNoTXT = (TextView) aView.findViewById(R.id.second_cut_aadhar_no_TXT);
        myInterestTXT = (TextView) aView.findViewById(R.id.second_cut_interest_TXT);
        myHobbyTXT = (TextView) aView.findViewById(R.id.second_cut_hobby_TXT);
        //..................................Second Cut...........................................//

        //..................................Third Cut...........................................//
        myQualificationTXT = (TextView) aView.findViewById(R.id.second_cut_qualification_TXT);
        mySkillTXT = (TextView) aView.findViewById(R.id.second_cut_skill_TXT);
        myExperienceTXT = (TextView) aView.findViewById(R.id.second_cut_experience_TXT);
        myPortfolioTXT = (TextView) aView.findViewById(R.id.second_cut_portfolio_TXT);
        myEditProfessionalIM = (ImageView) aView.findViewById(R.id.editprofessional_im);

        //..................................Third Cut...........................................//
        showContentView();
        clickListener();


    }

    private void showContentView() {

        myUserInfo = myPref.getUserInfo();


        if (myUserInfo.getCurrentStatus() != null) {

            if (myUserInfo.getCurrentStatus().equals("0")) {
                myMainEmpStatusTXT.setText("Un Employee");
                myEmpStatusTXT.setText("Un Employee");
                myMainEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));
                myEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));


            } else {
                myMainEmpStatusTXT.setText("Employee");
                myEmpStatusTXT.setText("Employee");
                myEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.btnRequest));
                myMainEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.btnRequest));

            }

        } else {
            myMainEmpStatusTXT.setText("Un Employee");
            myEmpStatusTXT.setText("Un Employee");
            myMainEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));
            myEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));

        }

        if (!myPref.getProfileLocalImage().equals("")) {

            byte[] aByte = Base64.decode(myPref.getProfileLocalImage(), Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(aByte, 0, aByte.length);

            myFirstCutProfileIM.setImageBitmap(bitmap);

        } else {

        }
        //......................................................................................//

        myMainNameTXT.setText(myUserInfo.getName());

        myMainDesginTXT.setText(myUserInfo.getQualicationId());

        myMainSillTXT.setText(myUserInfo.getSKILLS());

        //.....................................................................................//


        //...................First Cut set value .....................................//


        myFirstNameTXT.setText(myUserInfo.getName());

        myDesignationTXT.setText(myUserInfo.getSKILLS());

        myFirstQualificationTXT.setText(myUserInfo.getQualicationId());

        //   myDesignationTXT.setText(myUserInfo.getd());

        myYearOfExpTXT.setText(myUserInfo.getExperienceId());

        // myLookingStatusTXT.setText(myUserInfo.());


        //...................Second Cut set value .....................................//

        myNameTXT.setText(myUserInfo.getName());

        myFEmailTXT.setText(myUserInfo.getMailId());

        myFMobileNoTXT.setText(myUserInfo.getMobileNo());

        myDobTXT.setText(myUserInfo.getDOB());

        myFAddressTXT.setText("" + myUserInfo.getAddress());

        myNationalityTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getNationality()));

        myBirthPlaceTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getBirthPlace()));

        myAadharNoTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getAadhar()));

        myInterestTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getIntrest()));

        myHobbyTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getHobby()));


        //...................Second Cut set value .....................................//


        //...................Third Cut set value .....................................//

        myQualificationTXT.setText(myUserInfo.getQualicationId());

        mySkillTXT.setText(myUserInfo.getSKILLS());

        myExperienceTXT.setText(myUserInfo.getExperienceId());

        myPortfolioTXT.setText(myUserInfo.getPortFolio());

        //    myAddressTXT.setText(myUserInfo.getAddress());

        //...................Third Cut set value .....................................//


        JSHelper.loadImageView(myContext, myFirstCutProfileIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        JSHelper.loadImageView(myContext, mySecondProfileIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        myEditPersonalIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.updateContent(new JSProfileEditPage(), JSProfileEditPage.TAG, aBundle);

            }
        });

        myEditProfessionalIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.clearOneFragment();

                myFragmentManager.updateContent(new JSProfileProfessionalEditPage(), JSProfileProfessionalEditPage.TAG, aBundle);

            }
        });

        mySecondProfileIM.setOnClickListener(new View.OnClickListener() {
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
    }

    private void clickListener() {

      /*  myNoDataLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle aBundle = new Bundle();
                aBundle.putString("KEY", "BACK1");
                myFragmentManager.updateContent(new JSJobCreateFragment(), JSJobCreateFragment.TAG, aBundle);

            }
        });*/

        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFoldingCell.toggle(false);

            }
        });

        myFoldingContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFoldingCell.unfold(false);
            }
        });
    }

    private void setUserDetails() {

        myUserInfo = myPref.getUserInfo();

        myPref.putUserID("" + myUserInfo.getId());

        myJobList = new ArrayList<>();

        JobList_ aJob = new JobList_();

        aJob.setId(1);

        myJobList.add(aJob);

        toSearchItem("");

        //toLoadValue(myJobList);
    }

    @Override
    public boolean onResumeFragment() {
        return false;
    }


    private void toLoadValue(ArrayList<JobList_> aList) {

        if (aList != null) {

            Collections.reverse(aList);

            if (aList.size() > 0) {


                myProfileLV.setVisibility(View.VISIBLE);

                // prepare elements to display
                final ArrayList<Item> items = Item.getTestingList();


                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FoldingMainAdapter adapter = new FoldingMainAdapter(myContext, aList);


                // set elements to adapter
                myProfileLV.setAdapter(adapter);
                // set on click event listener to list view
                myProfileLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);

                        if (myCheck) {

                            myCheck = false;

                        } else {

                        }
                        // register in adapter that state for selected cell is toggled
                        // Toast.makeText(myContext, "HIFOLDING", Toast.LENGTH_SHORT).show();

                        myCheck = true;

                        adapter.registerToggle(pos);
                    }
                });

            } else {
                myProfileLV.setVisibility(View.GONE);

            }
        } else {
            myProfileLV.setVisibility(View.GONE);

        }
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
                //      onPhotosReturned(imageFile);
// Do image process
            }


        });
    }


    public void setDummyValues() {

        myJobList = new ArrayList<>();

        JobList_ aJob = new JobList_();

        aJob.setId(1);

        myJobList.add(aJob);

        //toLoadValue(myJobList);

    }

    private void toSearchItem(String aItem) {

        if (JSHelper.CheckInternet(myContext)) {

            getSearchItem(aItem);

        } else {

            JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

        }
    }

    public void getSearchItem(String aSearchValue) {

        myDialog.show();

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<JobList> call = myAPIInterface.getJobSearch(aSearchValue, myPref.getUserID(), "1");

        call.enqueue(new Callback<JobList>() {
            @Override
            public void onResponse(Call<JobList> call, Response<JobList> response) {

                Log.e("RESPONSE", response.message());

                JobList aJob = response.body();

                if (aJob != null) {

                    myCurrentJobList = aJob.getJobList();

                    if (myCurrentJobList != null) {
                        toloadValue(myCurrentJobList);
                    }

                    if (myDialog != null) {
                        myDialog.dismiss();

                    }

                } else {

                    myCurrentJobList = new ArrayList<>();

                    if (myCurrentJobList != null) {
                        toloadValue(myCurrentJobList);
                    }

                    if (myDialog != null) {
                        myDialog.dismiss();

                    }

                }


                // myExperienceList = response.body();

            }

            @Override
            public void onFailure(Call<JobList> call, Throwable t) {
                Log.e("RESPONSE", "FAIL");

            }
        });
    }

    private void toloadValue(ArrayList<JobList_> aList) {

        if (aList != null) {


            if (aList.size() > 0) {

                Collections.reverse(aList);


                myNoDataLAY.setVisibility(View.GONE);

                myCurrentUserLV.setVisibility(View.VISIBLE);

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FoldingCellListAdapter adapter = new FoldingCellListAdapter(myContext, aList);


                // set elements to adapter
                myCurrentUserLV.setAdapter(adapter);
                // set on click event listener to list view
                myCurrentUserLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });
            } else {

                myAlertTXT.setText("No Job's found");
                myNoDataLAY.setVisibility(View.VISIBLE);
                myAddBTN.setVisibility(View.GONE);
                myCurrentUserLV.setVisibility(View.GONE);

            }
        } else {
            myAlertTXT.setText("No Job's found.");
            myNoDataLAY.setVisibility(View.VISIBLE);
            myAddBTN.setVisibility(View.GONE);
            myCurrentUserLV.setVisibility(View.GONE);

        }
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
}
