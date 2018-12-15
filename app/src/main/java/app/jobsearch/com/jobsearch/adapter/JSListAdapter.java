package app.jobsearch.com.jobsearch.adapter;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.model.Qualification;

public class JSListAdapter extends BaseAdapter implements ConstantValues {

    private FragmentActivity myContext;

    private ArrayList<Qualification> myMenuList;

    private ClickListener myListener;

    private String myQualification = "", myQualificationID = "";

    private ListPopupWindow myWindow;

    private String myType = "";

    public JSListAdapter(FragmentActivity aContext, ArrayList<Qualification> aList, ListPopupWindow myLPWindow, String aType) {
        this.myContext = aContext;
        this.myMenuList = aList;
        myWindow = myLPWindow;
        myType = aType;
    }

    @Override
    public int getCount() {
        return myMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {

            final Qualification aQualification = myMenuList.get(position);

            LayoutInflater inflater = myContext.getLayoutInflater();

            convertView = inflater.inflate(R.layout.inflate_list_item, null);

            TextView aTitleTXT = (TextView) convertView.findViewById(R.id.titleTxt);

            RelativeLayout aSubHeadLAY = (RelativeLayout) convertView.findViewById(R.id.mainLay);

            aTitleTXT.setText(aQualification.getText());

            aSubHeadLAY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myQualification = aQualification.getText();

                    updateData(position);

                    myListener.setData();

                    if (myWindow != null) {
                        myWindow.dismiss();

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private void updateData(int aPos) {
        myQualification = myMenuList.get(aPos).getText();
        myQualificationID = "" + myMenuList.get(aPos).getId();

    }

    public String getQualification() {
        return myQualification;
    }

    public String getQualificationID() {
        return myQualificationID;
    }

    public String getMyType() {
        return myType;
    }


    public interface ClickListener {
        void setData();
    }

    public void setData(ClickListener aListener) {

        myListener = aListener;
    }

}
