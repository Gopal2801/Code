package app.jobsearch.com.jobsearch.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.jobsearch.com.jobsearch.R;


public class ProgressDialog extends Dialog {

    private FragmentActivity myContext;
    private TextView myLoadingTxt;
    private ProgressBar myProgressWheel;

    public ProgressDialog(FragmentActivity context) {
        super(context);
        myContext = context;
        try {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setContentView(R.layout.custom_dialog_box);
            this.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            myLoadingTxt = (TextView) this
                    .findViewById(R.id.custom_dialog_box_TXT_loading);
            myProgressWheel = (ProgressBar) this
                    .findViewById(R.id.custom_dialog_box_PB_loading);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setMessage(String aMessage) {
        myLoadingTxt.setText(aMessage);
        myLoadingTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
