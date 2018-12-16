package app.jobsearch.com.jobsearch.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.utils.NetworkUtil;

public class JSHelper implements ConstantValues {

    /**
     * @param aMessage
     */
    public static void showAlertDialog(final Context aContext, String aMessage) {
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

    /**
     * @param aMessage
     */
    public static void showToast(final Context aContext, String aMessage) {
        Toast.makeText(aContext, aMessage, Toast.LENGTH_SHORT).show();

    }

    public final static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean CheckInternet(FragmentActivity aContext) {

        boolean aStatus = false;

        if (NetworkUtil.isInternetOn(aContext)) {
            aStatus = true;
        } else {
            //   CUMIHelper.showAlert(aContext, "Network unavailable", ALERT_FAILURE);
            aStatus = false;
        }
        return aStatus;
    }

    public static void loadImageView(FragmentActivity aContext,
                                     final ImageView aImageView, String aImageUrlStr, int aDefaultImage,
                                     int aErrorImage) {
        try {

            if (aImageUrlStr.length() > 0) {
                Log.i("IMAGE URL", aImageUrlStr);
                Picasso.with(aContext)
                        .load(aImageUrlStr.replace(" ", "%20")).networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .error(aContext.getResources().getDrawable(aErrorImage))
                        .placeholder(aContext.getResources().getDrawable(aDefaultImage))
                        .into(aImageView);


            } else {
                aImageView.setBackgroundResource(aErrorImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void loadImageViewList(FragmentActivity aContext,
                                         final ImageView aImageView, String aImageUrlStr, int aDefaultImage,
                                         int aErrorImage) {
        try {

            if (aImageUrlStr.length() > 0) {
                Log.i("IMAGE URL", aImageUrlStr);
                Picasso.with(aContext)
                        .load(aImageUrlStr.replace(" ", "%20"))
                        .error(aContext.getResources().getDrawable(aErrorImage))
                        .placeholder(aContext.getResources().getDrawable(aDefaultImage))
                        .into(aImageView);


            } else {
                aImageView.setBackgroundResource(aErrorImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void loadCompanyImage(FragmentActivity aContext,
                                        final ImageView aImageView, String aImageUrlStr, int aDefaultImage,
                                        int aErrorImage) {
        try {

            if (aImageUrlStr.length() > 0) {
                Log.i("IMAGE URL", aImageUrlStr);
                Picasso.with(aContext)
                        .load(aImageUrlStr.replace(" ", "%20"))
                        .error(aContext.getResources().getDrawable(aErrorImage))
                        .placeholder(aContext.getResources().getDrawable(aDefaultImage))
                        .into(aImageView);

                aImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {

                aImageView.setBackgroundResource(aErrorImage);

                aImageView.setColorFilter(aContext.getResources().getColor(R.color.blue));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String stringIsNotNull(String aValue) {

        String aResult;

        if (aValue != null && aValue.equals("null")) {
            aResult = aValue;
        } else {

            if (!aValue.equals("")) {
                aResult = aValue;

            } else {
                aResult = "";
            }

        }
        return aResult;
    }


    public static Boolean stringIsNotNullCheck(String aValue) {

        Boolean aResult = false;

        if (aValue != null && aValue.equals("null")) {
            aResult = true;
        } else {

            if (!aValue.equals("")) {
                aResult = true;

            } else {
                aResult = false;
            }

        }
        return aResult;
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
