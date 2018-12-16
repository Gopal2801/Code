package app.jobsearch.com.jobsearch.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.model.Common;
import app.jobsearch.com.jobsearch.model.Common;

public class JSCommonAdapter extends BaseAdapter implements ConstantValues {

    private FragmentActivity myContext;

    private ArrayList<Common> myMenuList;

    private ClickListener myListener;

    private String myCommon = "", myCommonID = "";

    private ListPopupWindow myWindow;

    private String myType = "";

    public JSCommonAdapter(FragmentActivity aContext, ArrayList<Common> aList, ListPopupWindow myLPWindow, String aType) {
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

            final Common aCommon = myMenuList.get(position);

            LayoutInflater inflater = myContext.getLayoutInflater();

            convertView = inflater.inflate(R.layout.inflate_list_item, null);

            TextView aTitleTXT = (TextView) convertView.findViewById(R.id.titleTxt);

            RelativeLayout aSubHeadLAY = (RelativeLayout) convertView.findViewById(R.id.mainLay);

            aTitleTXT.setText(aCommon.getName());

            aSubHeadLAY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myCommon = aCommon.getName();

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
        myCommon = myMenuList.get(aPos).getName();
        myCommonID = "" + myMenuList.get(aPos).getId();

    }

    public String getCommon() {
        return myCommon;
    }

    public String getCommonID() {
        return myCommonID;
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
