package app.jobsearch.com.jobsearch.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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

import com.master.permissionhelper.PermissionHelper;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Item;
import app.jobsearch.com.jobsearch.model.JobList;
import app.jobsearch.com.jobsearch.model.JobList_;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<JobList_> implements ConstantValues {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private FragmentActivity myContext;
    private PermissionHelper myPermissionHelper;
    private ProgressDialog myDialog;
    private ApiInterface myAPIInterface;
    private Preference myPref;
    private ArrayList<JobList_> myList;

    public FoldingCellListAdapter(FragmentActivity context, ArrayList<JobList_> objects) {
        super(context, 0, objects);
        myList = objects;
        myContext = context;
        myPref = new Preference(context);
        myDialog = new ProgressDialog(myContext);

    }

    public JobList_ getItem(int position) {
        return myList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        final JobList_ item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.price = cell.findViewById(R.id.title_price);
            //   viewHolder.time = cell.findViewById(R.id.title_time_label);
            viewHolder.date = cell.findViewById(R.id.title_date_label);
            viewHolder.toAddress = cell.findViewById(R.id.job_desc_label_txt);


            //...........................................Need...............................//
            viewHolder.myJobTitleTxt = cell.findViewById(R.id.job_title_txt);
            viewHolder.myJobDesTXT = cell.findViewById(R.id.description_txt);
            viewHolder.myJobBudgetTXT = cell.findViewById(R.id.budget_TXT);
            viewHolder.myRequirementTXT = cell.findViewById(R.id.requirement_TXT);
            viewHolder.myNoofvacancyTXT = cell.findViewById(R.id.noofvacancyTXT);
            viewHolder.myCompanyProfileIM = cell.findViewById(R.id.company_proifle_IM);

            viewHolder.myContentDESTXT = cell.findViewById(R.id.content_description_txt);
            viewHolder.myContentBudgetTXT = cell.findViewById(R.id.content_budget_txt);
            viewHolder.myContentReqTXT = cell.findViewById(R.id.content_req_txt);
            viewHolder.myContentNameTXT = cell.findViewById(R.id.content_name_txt);
            viewHolder.myContentUserNameTXT = cell.findViewById(R.id.content_user_name_txt);
            viewHolder.myContentExperienceTXT = cell.findViewById(R.id.content_experience_txt);
            viewHolder.myContentDesignationTXT = cell.findViewById(R.id.content_designation_txt);
            viewHolder.myProfileIM = cell.findViewById(R.id.content_avatar);


            viewHolder.myRemarksLay = cell.findViewById(R.id.remarks_lay);


            //...........................................Need...............................//

            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            viewHolder.profileLAY = cell.findViewById(R.id.profile_lay);
            viewHolder.ratingBarProfile = cell.findViewById(R.id.ratingBar_profile);

            viewHolder.myContentRemarksTXT = cell.findViewById(R.id.remarks_txt);

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


        viewHolder.myJobTitleTxt.setText(item.getName());
        viewHolder.myJobDesTXT.setText(item.getDescription());
        viewHolder.myJobBudgetTXT.setText(item.getBudget());

        viewHolder.myContentNameTXT.setText(item.getName());
        viewHolder.myContentBudgetTXT.setText(item.getBudget());
        viewHolder.myContentDESTXT.setText(item.getDescription());
        viewHolder.myContentUserNameTXT.setText(item.getCreatedBy().getName());
        JSHelper.loadImageViewList(myContext, viewHolder.myProfileIM, IMAGE_URL + item.getCreatedBy().getProfileImage(),
                R.drawable.ic_action_user, R.drawable.ic_action_user);

        JSHelper.loadCompanyImage(myContext, viewHolder.myCompanyProfileIM, IMAGE_URL + item.getCompanyProfile(),
                R.drawable.ic_app_profile, R.drawable.ic_app_profile);


        if (item.getRequirement() != null) {
            viewHolder.myRequirementTXT.setText("" + item.getRequirement());
            viewHolder.myContentReqTXT.setText("" + item.getRequirement());

        }

        if (item.getNoOfVacancy() != null && !item.getNoOfVacancy().equals("")) {
            viewHolder.myNoofvacancyTXT.setText(item.getNoOfVacancy());
        }
        //  if (item.getCreatedBy().getIsExperienced())

        String aExperienceSTR = item.getCreatedBy().getExperienceId();

        if (!aExperienceSTR.equals("") && aExperienceSTR != null) {
            viewHolder.myContentExperienceTXT.setText(aExperienceSTR);

        } else {
            viewHolder.myContentExperienceTXT.setText("Nill");

        }
        String aDesignSTR = item.getDesignation();
        if (!aDesignSTR.equals("") && aDesignSTR != null) {
            viewHolder.myContentDesignationTXT.setText(aDesignSTR);

        } else {
            viewHolder.myContentDesignationTXT.setText("Nill");

        }

        if (!item.getRating().getCurrentRating().equals("") && item.getRating().getCurrentRating() != null) {
            viewHolder.ratingBarProfile.setRating(Float.parseFloat(item.getRating().getCurrentRating()));

        } else {
            viewHolder.ratingBarProfile.setRating(Float.parseFloat("0"));

        }

        if (!item.getRating().getRemarks().equals("") && item.getRating().getRemarks() != null) {
            viewHolder.myContentRemarksTXT.setText(item.getRating().getRemarks());

        } else {
            viewHolder.myContentRemarksTXT.setText("Nill");

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

        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    askPermission();
                } else {

                    alertForCall(item.getCreatedBy().getMobileNo(), item.getCreatedBy().getMailId());

                }

            }
        });
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
        //  viewHolder.pledgePrice.setText(item.getPledgePrice());

        /*// set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }*/


        viewHolder.profileLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertForRating(position, item.getCreatedBy().getName(), "" + item.getCreatedBy().getId(),
                        item.getCreatedBy().getProfileImage(), item.getRating().getCurrentRating(), "" + item.getId());
            }
        });

        viewHolder.myRemarksLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertForRemars(position, "" + item.getCreatedBy().getId(), item.getCreatedBy().getProfileImage(),
                        item.getRating().getRemarks(), "" + item.getId());
            }
        });
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
        TextView price;
        TextView contentRequestBtn, myNameTXT, myDesginTXT, mySillTXT;
        TextView pledgePrice;
        TextView myJobTitleTxt, myJobDesTXT, myJobBudgetTXT, myRequirementTXT;
        TextView toAddress, myNoofvacancyTXT;
        TextView requestsCount;
        TextView date;
        RelativeLayout profileLAY;
        RatingBar ratingBarProfile;

        TextView myContentDESTXT, myContentBudgetTXT, myContentReqTXT, myContentNameTXT,
                myContentUserNameTXT, myContentExperienceTXT, myContentDesignationTXT, myContentRemarksTXT;

        ImageView myProfileIM, myCompanyProfileIM;

        LinearLayout myRemarksLay;
    }

    public void alertForRating(final int aPos, String aNameSTR, final String aUserID, String aProfileImageSTR, String aRatingSTR, final String aJobId) {

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

        if (!aRatingSTR.equals("") && aRatingSTR != null) {
            aRating.setRating(Float.parseFloat(aRatingSTR));

        } else {
            aRating.setRating(Float.parseFloat("0"));

        }


        JSHelper.loadImageViewList(myContext, aProfileIM, IMAGE_URL + aProfileImageSTR,
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        aNameTXT.setText(aNameSTR);

        //aRating.setRating((float) 2.0);

        aOkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aRatingSTR = String.valueOf(aRating.getRating());

                aDlg.dismiss();

                if (JSHelper.CheckInternet(myContext)) {


                    if (!aRatingSTR.equals("")) {

                        updateJobDetails(aPos, aUserID, aRatingSTR, "", aJobId, aDlg);

                    } else {

                        aDlg.dismiss();
                    }

                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }
            }
        });


        aDlg.show();


    }


    public void alertForRemars(final int aPos, final String aUserID, String aProfileImageSTR, String aRemarksSTR, final String aJobId) {

        final Dialog aDlg = new Dialog(myContext);

        aDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        aDlg.setContentView(R.layout.dialog_for_remarks);

        aDlg.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        final EditText aRemarksEDT = (EditText) aDlg.findViewById(R.id.remarks_EDT);

        Button aOkBTN = (Button) aDlg.findViewById(R.id.dialog_for_alert_ok_BTN);


        CircleImageView aProfileIM = (CircleImageView) aDlg.findViewById(R.id.alert_profile_image);

        if (!aRemarksSTR.equals("") && aRemarksSTR != null) {

            aRemarksEDT.setText(aRemarksSTR);
        }
        //TextView aNameTXT = (TextView) aDlg.findViewById(R.id.dialog_name_TXT);

        JSHelper.loadImageViewList(myContext, aProfileIM, IMAGE_URL + aProfileImageSTR,
                R.drawable.ic_profile_im, R.drawable.ic_profile_im);

        aOkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (JSHelper.CheckInternet(myContext)) {

                    String aRemarsSTR = aRemarksEDT.getText().toString();

                    if (!aRemarsSTR.equals("")) {

                        updateJobDetails(aPos, aUserID, "", aRemarsSTR, aJobId, aDlg);

                    } else {
                        aDlg.dismiss();
                    }

                } else {
                    JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

                }

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

                if (!aEmail.equals("") && aEmail != null) {
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

        if (aEmail != null && !aEmail.equals("null")) {
            Intent aEmailIntent = new Intent(Intent.ACTION_SEND);
            aEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{aEmail});
            aEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "TEST");
            //need this to prompts email client only
            aEmailIntent.setType("message/rfc822");
            myContext.startActivity(Intent.createChooser(aEmailIntent, "Choose an Email client :"));


        }

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


    public void updateJobDetails(final int aPos, String aUserId, final String aRating, final String aRemarks, String aId, final Dialog aDlg) {

        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = myAPIInterface.updateRemarksOrRate(aUserId, myPref.getUserID(), aRating, aRemarks, aId);

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

                        if (!aRating.equals("")) {
                            myList.get(aPos).getRating().setCurrentRating(aRating);

                        }
                        if (!aRemarks.equals("")) {
                            myList.get(aPos).getRating().setRemarks(aRemarks);

                        }
                        updateList(myList);

                        toDismissDialog(aDlg);

                        // showAlertDialog("Job Created Successfully");

                        //  myContext.onBackPressed();

                        break;

                    case RESPONSE_CODE_FAILURE:

                        toDismissDialog(aDlg);

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;

                    default:

                        toDismissDialog(aDlg);

                        JSHelper.showAlertDialog(myContext, aResponseMsg);

                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                toDismissDialog(aDlg);

                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);

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

    public void updateList(ArrayList<JobList_> aList) {

        myList = aList;

        notifyDataSetChanged();
    }
}
