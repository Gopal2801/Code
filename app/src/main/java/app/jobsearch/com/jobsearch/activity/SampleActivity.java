package app.jobsearch.com.jobsearch.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.adapter.DrawerAdapter;
import app.jobsearch.com.jobsearch.adapter.DrawerItem;
import app.jobsearch.com.jobsearch.adapter.SimpleItem;
import app.jobsearch.com.jobsearch.adapter.SpaceItem;
import app.jobsearch.com.jobsearch.fragment.JSDummyPage;
import app.jobsearch.com.jobsearch.fragment.JSEventListFragment;
import app.jobsearch.com.jobsearch.fragment.JSEventPageFragment;
import app.jobsearch.com.jobsearch.fragment.JSJobCreateFragment;
import app.jobsearch.com.jobsearch.fragment.JSJobListFragment;
import app.jobsearch.com.jobsearch.fragment.JSJobSearchFragment;
import app.jobsearch.com.jobsearch.fragment.JSProfileFragment;
import app.jobsearch.com.jobsearch.fragment.JSProfileMedicalScreen;
import app.jobsearch.com.jobsearch.fragment.JSProfileMergeScreen;
import app.jobsearch.com.jobsearch.fragment.SampleFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Education;
import app.jobsearch.com.jobsearch.model.Experience;
import app.jobsearch.com.jobsearch.model.ProfileData;
import app.jobsearch.com.jobsearch.model.Schools;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */

public class SampleActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, ConstantValues {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_MERGE = 1;
    private static final int POS_JOB = 2;
    private static final int POS_SEARCH = 3;
    private static final int POS_MEDICAL = 4;
    private static final int POS_EVENT = 5;

    private static final int POS_LOGOUT = 7;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private FragmentActivity myContext;
    private Preference myPref;
    private JSFragmentManager myFragmentManager;
    private int FIRST_BACK_STACK_FRAGMENT = 1;
    private static long back_pressed;

    private ProgressDialog myDialog;

    private ApiInterface myAPIInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_root);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContext = SampleActivity.this;
        myFragmentManager = new JSFragmentManager(myContext);
        myDialog = new ProgressDialog(myContext);

        myPref = new Preference(myContext);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MERGE),
                createItemFor(POS_JOB),
                createItemFor(POS_SEARCH),
                createItemFor(POS_MEDICAL),
                createItemFor(POS_EVENT),
                new SpaceItem(20),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }


    public void hideActionBar() {
        getSupportActionBar().hide();
    }

    public void showActionBar() {
        getSupportActionBar().show();
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            showAlertDialog();

        } else {
            slidingRootNav.closeMenu();

        }
/*
        switch (position) {
            case POS_DASHBOARD:
                myFragmentManager.updateContent(new JSProfileFragment(), JSProfileFragment.TAG, null);
                break;
            case POS_MERGE:
                myFragmentManager.updateContent(new JSDummyPage(), JSDummyPage.TAG, null);

                //     myFragmentManager.updateContent(new JSProfileMergeScreen(), JSProfileMergeScreen.TAG, null);
                break;
            case POS_LOGOUT:
                showAlertDialog();
                break;

            default:
                myFragmentManager.updateContent(new JSDummyPage(), JSDummyPage.TAG, null);
                break;
        }
        slidingRootNav.closeMenu();*/

        if (position == POS_DASHBOARD) {
            showActionBar();
         /*   myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSProfileFragment(), JSProfileFragment.TAG, null);
*/
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSDummyPage(), JSDummyPage.TAG, null);
        } else if (position == POS_MERGE) {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSProfileMergeScreen(), JSProfileMergeScreen.TAG, null);

        } else if (position == POS_JOB) {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSJobCreateFragment(), JSJobCreateFragment.TAG, null);

        } else if (position == POS_SEARCH) {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSJobListFragment(), JSJobListFragment.TAG, null);

        } else if (position == POS_MEDICAL) {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSProfileMedicalScreen(), JSProfileMedicalScreen.TAG, null);

        } else if (position == POS_EVENT) {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSEventListFragment(), JSEventListFragment.TAG, null);

        } else {
            myFragmentManager.clearAllFragments();
            myFragmentManager.updateContent(new JSDummyPage(), JSDummyPage.TAG, null);
/*
            Fragment selectedScreen = SampleFragment.createFor(screenTitles[position]);
            showFragment(selectedScreen);*/
        }

    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }


    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    public void showAlertDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
            builder.setMessage(ALRET_FOR_LOG_OUT)
                    .setTitle(APP_NAME)
                    .setCancelable(false)
                    .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            callLoginPage();
                            dialog.dismiss();
                        }
                    }).setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });


            AlertDialog alert = builder.create();
            alert.show();
            // Change the buttons color in dialog
            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(ContextCompat.getColor(myContext, R.color.colorPrimary));
            Button cbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
            cbutton.setTextColor(ContextCompat.getColor(myContext, R.color.colorAccent));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callLoginPage() {

        myPref.putStatus(false);
        myPref.putSchoolingList(new ArrayList<Schools>());
        myPref.putExperienceList(new ArrayList<Experience>());
        myPref.putEducationList(new ArrayList<Education>());
        myPref.putResultStatus(true);
        Intent i = new Intent(myContext, MainActivityAlpha.class);
        startActivity(i);
        finish();

    }

    public void openMenu() {

        if (slidingRootNav != null) {

            slidingRootNav.openMenu();

        }
    }

    @Override
    public void onBackPressed() {

        if (myFragmentManager.getBackstackCount() > 1) {

            if (myFragmentManager.getActiveFragmentTAG().equals("JSProfileEditPage")) {

                showActionBar();

                myFragmentManager.backPress();

            } else {

                showActionBar();

                myFragmentManager.backPress();

            }


        } else {

            if (myFragmentManager.getActiveFragmentTAG().equals("JSProfileEditPage")) {

                showActionBar();
            }

            if (myFragmentManager.getActiveFragmentTAG().equals("JSJobListFragment") ||
                    myFragmentManager.getActiveFragmentTAG().equals("JSProfileMedicalScreen") ||
                    myFragmentManager.getActiveFragmentTAG().equals("JSJobCreateFragment") ||
                    myFragmentManager.getActiveFragmentTAG().equals("JSProfileMergeScreen")) {

                if (slidingRootNav != null) {

                    slidingRootNav.openMenu();

                }

            }
            if (back_pressed + 2000 > System.currentTimeMillis()) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);


            } else {

                Toast.makeText(getBaseContext(),
                        "Press once again to exit!", Toast.LENGTH_SHORT)
                        .show();


            }

            back_pressed = System.currentTimeMillis();


        }
        if (slidingRootNav != null) {

            if (slidingRootNav.isMenuOpened()) {

                if (back_pressed + 2000 > System.currentTimeMillis()) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    System.exit(0);


                } else {

                    Toast.makeText(getBaseContext(),
                            "Press once again to exit!", Toast.LENGTH_SHORT)
                            .show();


                }

                back_pressed = System.currentTimeMillis();
            }

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
                onPhotosReturned(imageFile);
// Do image process
            }


        });
    }


    private void onPhotosReturned(File imageFile) {


        Log.e("PIC", "onPhotosReturned");

        if (imageFile.exists()) {

            //   myProfileCIM.setImageBitmap(null);

          /*  Bitmap aBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            aBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object

            byte[] b = baos.toByteArray();

            String aEncodingSTR = Base64.encodeToString(b, Base64.DEFAULT);

            myPref.putProfileLocalImage(aEncodingSTR);*/

            //   myProfileCIM.setImageBitmap(myBitmap);

//For uploading the image

            if (JSHelper.CheckInternet(myContext)) {

                updateJobDetails(imageFile.getAbsolutePath());

            } else {


                loadList();

                JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

            }


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

                        myPref.putProfileLocalImage("");

                        loadList();

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

    private void loadList() {

        if (myFragmentManager.getActiveFragmentTAG().equals("JSDummyPage")) {

            JSDummyPage aFragment = (JSDummyPage) getSupportFragmentManager().findFragmentByTag("JSDummyPage");

            aFragment.setDummyValues();


        }
    }

    private void toDismissDialog() {


        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

}
