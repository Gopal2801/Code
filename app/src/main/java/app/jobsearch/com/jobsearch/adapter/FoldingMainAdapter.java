package app.jobsearch.com.jobsearch.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.master.permissionhelper.PermissionHelper;
import com.ramotion.foldingcell.FoldingCell;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.fragment.JSDummyPage;
import app.jobsearch.com.jobsearch.fragment.JSProfileEditPage;
import app.jobsearch.com.jobsearch.fragment.JSProfileFragment;
import app.jobsearch.com.jobsearch.fragment.JSProfileProfessionalEditPage;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.JobList_;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingMainAdapter extends ArrayAdapter<JobList_> implements ConstantValues {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private FragmentActivity myContext;
    private PermissionHelper myPermissionHelper;
    private ProgressDialog myDialog;
    private ApiInterface myAPIInterface;
    private Preference myPref;
    private UserInfo myUserInfo;
    private JSFragmentManager myFragmentManager;

    public FoldingMainAdapter(FragmentActivity context, ArrayList<JobList_> objects) {
        super(context, 0, objects);
        myContext = context;
        myPref = new Preference(context);
        myDialog = new ProgressDialog(myContext);
        myFragmentManager = new JSFragmentManager(myContext);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        final JobList_ item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.profile_cell, parent, false);
            viewHolder.mySubProfileLAY = cell.findViewById(R.id.profile_main_lay);
            viewHolder.myMainNameTXT = cell.findViewById(R.id.profile_name_TXT);
            viewHolder.myMainDesginTXT = cell.findViewById(R.id.profile_qual1_TXT);
            viewHolder.myMainSillTXT = cell.findViewById(R.id.profile_sill_TXT);
            viewHolder.myMainEmpStatusTXT = cell.findViewById(R.id.employee_status_TXT);

            //..................................First Cut...........................................//
            viewHolder.myFirstNameTXT = cell.findViewById(R.id.first_cut_profile_name_TXT);
            viewHolder.myDesignationTXT = cell.findViewById(R.id.first_cut_profile_designation_TXT);
            viewHolder.myFirstQualificationTXT = cell.findViewById(R.id.first_cut_profile_qual_TXT);
            viewHolder.myYearOfExpTXT = cell.findViewById(R.id.first_cut_profile_experience_TXT);
            viewHolder.myLookingStatusTXT = cell.findViewById(R.id.first_cut_profile_luking_status_TXT);
            viewHolder.myProfileIM = cell.findViewById(R.id.profileChooseIM);
            viewHolder.myFirstCutProfileIM = cell.findViewById(R.id.first_cut_profileIM);
            viewHolder.myEmpStatusTXT = cell.findViewById(R.id.first_cut_profile_employee_status_TXT);

            //..................................First Cut...........................................//

            //..................................Second Cut...........................................//
            viewHolder.myNameTXT = cell.findViewById(R.id.second_cut_profile_name_TXT);
            viewHolder.myEmailTXT = cell.findViewById(R.id.second_cut_email_TXT);
            viewHolder.myMobileNoTXT = cell.findViewById(R.id.second_cut_mobile_no_TXT);
            viewHolder.myDobTXT = cell.findViewById(R.id.second_cut_dob_TXT);
            viewHolder.myAddressTXT = cell.findViewById(R.id.second_cut_address_TXT);
            viewHolder.myProfileChooseIM = cell.findViewById(R.id.profilesubChooseIM);
            viewHolder.myEditPersonalIM = cell.findViewById(R.id.personal_edit_im);
            viewHolder.mySecondProfileIM = cell.findViewById(R.id.second_cut_profileIM);
            viewHolder.myNationalityTXT = cell.findViewById(R.id.second_cut_nationality_TXT);
            viewHolder.myBirthPlaceTXT = cell.findViewById(R.id.second_cut_birth_place_TXT);
            viewHolder.myAadharNoTXT = cell.findViewById(R.id.second_cut_aadhar_no_TXT);
            viewHolder.myInterestTXT = cell.findViewById(R.id.second_cut_interest_TXT);
            viewHolder.myHobbyTXT = cell.findViewById(R.id.second_cut_hobby_TXT);
            //..................................Second Cut...........................................//

            //..................................Third Cut...........................................//
            viewHolder.myQualificationTXT = cell.findViewById(R.id.second_cut_qualification_TXT);
            viewHolder.mySkillTXT = cell.findViewById(R.id.second_cut_skill_TXT);
            viewHolder.myExperienceTXT = cell.findViewById(R.id.second_cut_experience_TXT);
            viewHolder.myPortfolioTXT = cell.findViewById(R.id.second_cut_portfolio_TXT);
            viewHolder.myEditProfessionalIM = cell.findViewById(R.id.editprofessional_im);

            //..................................Third Cut...........................................//


            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        final FoldingCell finalCell = cell;

        viewHolder.mySubProfileLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCell.fold(false);

            }
        });

        myUserInfo = myPref.getUserInfo();

        myPref.putUserID("" + myUserInfo.getId());

       /* if (myUserInfo.getProfileImage() != null) {

            String aImage = myUserInfo.getProfileImage();

            if (!aImage.equals("")) {

                myPref.putProfileImage(IMAGE_URL + aImage);

            } else {

                myPref.putProfileImage("");

            }
        }*/


        if (myUserInfo.getCurrentStatus().equals("0")) {
            viewHolder.myMainEmpStatusTXT.setText("Un Employee");
            viewHolder.myEmpStatusTXT.setText("Un Employee");
            viewHolder.myMainEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));
            viewHolder.myEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.red));


        } else {
            viewHolder.myMainEmpStatusTXT.setText("Employee");
            viewHolder.myEmpStatusTXT.setText("Employee");
            viewHolder.myEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.btnRequest));
            viewHolder.myMainEmpStatusTXT.setBackgroundColor(myContext.getResources().getColor(R.color.btnRequest));

        }

        if (!myPref.getProfileLocalImage().equals("")) {

            byte[] aByte = Base64.decode(myPref.getProfileLocalImage(), Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(aByte, 0, aByte.length);

            viewHolder.myFirstCutProfileIM.setImageBitmap(bitmap);

        } else {

        }

        //......................................................................................//

        viewHolder.myMainNameTXT.setText(myUserInfo.getName());

        viewHolder.myMainDesginTXT.setText(myUserInfo.getQualicationId());

        viewHolder.myMainSillTXT.setText(myUserInfo.getSKILLS());

        //.....................................................................................//


        //...................First Cut set value .....................................//


        viewHolder.myFirstNameTXT.setText(myUserInfo.getName());

        viewHolder.myDesignationTXT.setText(myUserInfo.getSKILLS());

        viewHolder.myFirstQualificationTXT.setText(myUserInfo.getQualicationId());

        //   viewHolder.myDesignationTXT.setText(myUserInfo.getd());

        viewHolder.myYearOfExpTXT.setText(myUserInfo.getExperienceId());

        // viewHolder.myLookingStatusTXT.setText(myUserInfo.());


        //...................Second Cut set value .....................................//

        viewHolder.myNameTXT.setText(myUserInfo.getName());

        viewHolder.myEmailTXT.setText(myUserInfo.getMailId());

        viewHolder.myMobileNoTXT.setText(myUserInfo.getMobileNo());

        viewHolder.myDobTXT.setText(myUserInfo.getDOB());

        viewHolder.myAddressTXT.setText("" + myUserInfo.getAddress());

        viewHolder.myNationalityTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getNationality()));

        viewHolder.myBirthPlaceTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getBirthPlace()));

        viewHolder.myAadharNoTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getAadhar()));

        viewHolder.myInterestTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getIntrest()));

        viewHolder.myHobbyTXT.setText(JSHelper.stringIsNotNull("" + myUserInfo.getHobby()));


        //...................Second Cut set value .....................................//


        //...................Third Cut set value .....................................//

        viewHolder.myQualificationTXT.setText(myUserInfo.getQualicationId());

        viewHolder.mySkillTXT.setText(myUserInfo.getSKILLS());

        viewHolder.myExperienceTXT.setText(myUserInfo.getExperienceId());

        viewHolder.myPortfolioTXT.setText(myUserInfo.getPortFolio());

        //    viewHolder.myAddressTXT.setText(myUserInfo.getAddress());

        //...................Third Cut set value .....................................//


        JSHelper.loadImageView(myContext, viewHolder.myFirstCutProfileIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        JSHelper.loadImageView(myContext, viewHolder.mySecondProfileIM, myPref.getProfileImage(),
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        viewHolder.myEditPersonalIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.updateContent(new JSProfileEditPage(), JSProfileEditPage.TAG, aBundle);

            }
        });

        viewHolder.myEditProfessionalIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle aBundle = new Bundle();

                aBundle.putString("USERINFO", new Gson().toJson(myUserInfo));

                myFragmentManager.clearOneFragment();

                myFragmentManager.updateContent(new JSProfileProfessionalEditPage(), JSProfileProfessionalEditPage.TAG, aBundle);

            }
        });

        viewHolder.mySecondProfileIM.setOnClickListener(new View.OnClickListener() {
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


      /*  viewHolder.myJobTitleTxt.setText(item.getName());
        viewHolder.myJobDesTXT.setText(item.getDescription());
        viewHolder.myJobBudgetTXT.setText(item.getBudget());

        viewHolder.myContentNameTXT.setText(item.getName());
        viewHolder.myContentBudgetTXT.setText(item.getBudget());
        viewHolder.myContentDESTXT.setText(item.getDescription());
        viewHolder.myContentUserNameTXT.setText(item.getCreatedBy().getName());
        JSHelper.loadImageViewList(myContext, viewHolder.myProfileIM, IMAGE_URL + item.getCreatedBy().getProfileImage(),
                R.drawable.ic_action_user, R.drawable.ic_action_user);

        if (item.getRequirement() != null) {
            viewHolder.myRequirementTXT.setText("" + item.getRequirement());
            viewHolder.myContentReqTXT.setText("" + item.getRequirement());

        }

        if (item.getNoOfVacancy() != null && !item.getNoOfVacancy().equals("")) {
            viewHolder.myNoofvacancyTXT.setText(item.getNoOfVacancy());
        }*/
        //  if (item.getCreatedBy().getIsExperienced())

      /*  String aExperienceSTR = item.getCreatedBy().getExperienceId();

        if (!aExperienceSTR.equals("") && aExperienceSTR != null) {
            viewHolder.myContentExperienceTXT.setText(aExperienceSTR);

        } else {
            viewHolder.myContentExperienceTXT.setText("Nill");

        }
        String aDesignSTR = item.getDesignation();
        if (!aDesignSTR.equals("") && aDesignSTR != null) {
            viewHolder.myContentDesignationTXT.setText(aDesignSTR);

        } else {
            viewHolder.myContentExperienceTXT.setText("Nill");

        }

        if (myPref.getUserID().equals("" + item.getCreatedBy().getId())) {
            viewHolder.contentRequestBtn.setVisibility(View.INVISIBLE);
            viewHolder.myRemarksLay.setEnabled(false);
            viewHolder.profileLAY.setEnabled(false);

        } else {
            viewHolder.contentRequestBtn.setVisibility(View.VISIBLE);
            viewHolder.myRemarksLay.setEnabled(true);
            viewHolder.profileLAY.setEnabled(true);

        }
*/
      /*  viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    askPermission();
                } else {

                    alertForCall(item.getCreatedBy().getMobileNo(), item.getCreatedBy().getMailId());

                   *//* if (!item.getCreatedBy().getMobileNo().equals("")) {
                        call(item.getCreatedBy().getMobileNo());

                    }*//*
                }

            }
        });*/
        //viewHolder.myContentExperienceTXT.setText(item.getCreatedBy().getName());

       /* if (item.getRequirement().toString() != null && !item.getRequirement().toString().equals("")) {
            viewHolder.myRequirementTXT.setText("" + item.getRequirement());

        }*/
        //   viewHolder.myNoofvacancyTXT.setText(item.getJobList().get(position).getN());


        // bind data from selected element to view through view holder
        // viewHolder.price.setText(item.getPrice());
        // viewHolder.time.setText(item.getTime());
        //  viewHolder.date.setText(item.getDate());
        //  viewHolder.fromAddress.setText(item.getFromAddress());
        //  viewHolder.toAddress.setText(item.getToAddress());
//        viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));
        //  viewHolder.ratingBarProfile.setRating((float) 4);
        //  viewHolder.pledgePrice.setText(item.getPledgePrice());

        /*// set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }*/


      /*  viewHolder.profileLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertForRating(item.getCreatedBy().getName(), "" + item.getCreatedBy().getId(), item.getCreatedBy().getProfileImage());
            }
        });

        viewHolder.myRemarksLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertForRemars("" + item.getCreatedBy().getId(), item.getCreatedBy().getProfileImage());
            }
        });*/
        return cell;
    }

    public void call(String aMobileNo) {

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + aMobileNo));

        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myContext.startActivity(intent);
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {


        //First Cut lay
        TextView myFirstNameTXT, myFirstQualificationTXT, myDesignationTXT, myYearOfExpTXT,
                myLookingStatusTXT, myMainDesginTXT, myMainSillTXT, myMainNameTXT, myMainEmpStatusTXT;

        //Second Cut lay
        TextView myNameTXT, myEmailTXT, myMobileNoTXT, myDobTXT,
                myAddressTXT, myNationalityTXT, myBirthPlaceTXT, myAadharNoTXT, myInterestTXT, myHobbyTXT, myEmpStatusTXT;

        //Third Cut lay
        TextView myQualificationTXT, mySkillTXT, myExperienceTXT, myPortfolioTXT;

        ImageView myProfileIM, myProfileChooseIM, myEditPersonalIM, myEditProfessionalIM, myFirstCutProfileIM, mySecondProfileIM;

        RelativeLayout mySubProfileLAY;
    }

    public void alertForRating(String aNameSTR, final String aUserID, String aProfileImageSTR) {

        final Dialog aDlg = new Dialog(myContext);

        aDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        aDlg.setContentView(R.layout.dialog_for_alert);

        aDlg.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final RatingBar aRating = (RatingBar) aDlg.findViewById(R.id.dialog_for_rating);

        Button aOkBTN = (Button) aDlg.findViewById(R.id.dialog_for_alert_ok_BTN);

        TextView aNameTXT = (TextView) aDlg.findViewById(R.id.dialog_name_TXT);

        CircleImageView aProfileIM = (CircleImageView) aDlg.findViewById(R.id.alert_profile_image);


        JSHelper.loadImageViewList(myContext, aProfileIM, IMAGE_URL + aProfileImageSTR,
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        aNameTXT.setText(aNameSTR);

        aRating.setRating((float) 2.0);

        aOkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aRatingSTR = String.valueOf(aRating.getRating());

                aDlg.dismiss();

               /* if (JSHelper.CheckInternet(myContext)) {


                    if (!aRatingSTR.equals("")) {

                        updateJobDetails(aUserID, aRatingSTR, "", aDlg);

                    } else {

                        aDlg.dismiss();
                    }

                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }*/
            }
        });


        aDlg.show();


    }


    public void alertForRemars(final String aUserID, String aProfileImageSTR) {

        final Dialog aDlg = new Dialog(myContext);

        aDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        aDlg.setContentView(R.layout.dialog_for_remarks);

        aDlg.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final EditText aRemarksEDT = (EditText) aDlg.findViewById(R.id.remarks_EDT);

        Button aOkBTN = (Button) aDlg.findViewById(R.id.dialog_for_alert_ok_BTN);


        CircleImageView aProfileIM = (CircleImageView) aDlg.findViewById(R.id.alert_profile_image);


        //TextView aNameTXT = (TextView) aDlg.findViewById(R.id.dialog_name_TXT);

        JSHelper.loadImageViewList(myContext, aProfileIM, IMAGE_URL + aProfileImageSTR,
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        aOkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (JSHelper.CheckInternet(myContext)) {

                    String aRemarsSTR = aRemarksEDT.getText().toString();

                    if (!aRemarsSTR.equals("")) {

                        updateJobDetails(aUserID, "", aRemarsSTR, aDlg);

                    } else {
                        aDlg.dismiss();
                    }

                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }*/

                aDlg.dismiss();

            }
        });

        aDlg.show();


    }


    public void alertForCall(final String aPhNo, final String aEmail) {

        final Dialog aDlg = new Dialog(myContext);

        aDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        aDlg.setContentView(R.layout.dialog_for_contact);

        aDlg.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final ImageView aEmailIM = (ImageView) aDlg.findViewById(R.id.email_IM);

        final ImageView aCallIM = (ImageView) aDlg.findViewById(R.id.call_IM);

        final ImageView aCloseIM = (ImageView) aDlg.findViewById(R.id.close_IM);


        aCallIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!aPhNo.equals("")) {
                    call(aPhNo);

                }
            }
        });

        aEmailIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!aEmail.equals("")) {
                    callEmail(aEmail);

                }
            }
        });

        aCloseIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aDlg.dismiss();

            }
        });


        aDlg.show();


    }

    private void callEmail(String aEmail) {
        Intent aEmailIntent = new Intent(Intent.ACTION_SEND);
        aEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{aEmail});
        aEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        //need this to prompts email client only
        aEmailIntent.setType("message/rfc822");
        myContext.startActivity(Intent.createChooser(aEmailIntent, "Choose an Email client :"));

    }

    private void askPermission() {

        myPermissionHelper = new PermissionHelper(myContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
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


    private void toDismissDialog(Dialog aDialog) {

        if (aDialog != null) {
            aDialog.dismiss();
        }
        if (myDialog != null) {
            myDialog.dismiss();
        }
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

    public interface ClickListener {
        void setData();
    }


}
