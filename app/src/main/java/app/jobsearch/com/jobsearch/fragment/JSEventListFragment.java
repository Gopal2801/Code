package app.jobsearch.com.jobsearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.adapter.JSEventListAdapter;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.EventList;
import app.jobsearch.com.jobsearch.model.List1;
import app.jobsearch.com.jobsearch.service.ApiClient;
import app.jobsearch.com.jobsearch.utils.ProgressDialog;
import id.zelory.compressor.Compressor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JSEventListFragment extends Fragment implements ConstantValues, JSEventListAdapter.DataTransferInterface {

    public static String TAG = JSEventListFragment.class.getSimpleName().toString();

    private FragmentActivity myContext;

    private RecyclerView myEventRC;

    private Logger log = null;

    private RelativeLayout myProgressLAY;

    private JSEventListAdapter myEventAdapter;

    private TextView myCreateTXT, myHeaderTXT;

    private LinearLayout myHomeBottomLAY;

    private TextView myEmptyTXT;

    private ApiInterface myAPIInterface;

    private ProgressDialog myDialog;

    private Preference myPrefs;

    private FloatingActionButton myAddEventBTN;

    private JSFragmentManager myFragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample_list, viewGroup, false);

        classInitialization(view);

        return view;
    }

    private void classInitialization(View aView) {

        myPrefs = new Preference(myContext);

        myDialog = new ProgressDialog(myContext);


        myFragmentManager = new JSFragmentManager(myContext);

        myEventRC = (RecyclerView) aView.findViewById(R.id.event_RC);

        myAddEventBTN = (FloatingActionButton) aView.findViewById(R.id.event_add_float_Btn);


        myEmptyTXT = (TextView) aView.findViewById(R.id.no_data_alert_TXT);


        //For header
/*

        myHeaderTXT = (TextView) aView.findViewById(R.id.header_TXT);

        myHeaderTXT.setText("Events");

        myEmptyTXT = (TextView) aView.findViewById(R.id.no_data_alert_TXT);
*/

        toLoadValues();

        clickListener();

    }

    private void clickListener() {

        myAddEventBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myFragmentManager.updateContent(new JSEventCreateFragment(), JSEventCreateFragment.TAG, null);


            }
        });
    }


    private void toLoadValues() {


        if (JSHelper.CheckInternet(myContext)) {

            loadEventList();

        } else {

            JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

        }


    }

    private void loadEventList() {


        myDialog.show();

        myDialog.setCanceledOnTouchOutside(false);

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);


        Call<EventList> call = myAPIInterface.getEventList(myPrefs.getUserID());


        Log.e("URL - ", call.request().url().toString());
        //Log.d(MODULE, TAG + " URL body - " + call.request().body().toString());
        call.enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {
                int statusCode = response.code();
                Log.e(" URL Response", "" + statusCode);
                if (response.body() != null) {

                    EventList pojoResponse = response.body();

                    Log.e("ServiceResponse - ", pojoResponse.toString());

                    loadEventListItem(pojoResponse.getList());

                    dismissProgress();

                } else {
                    //failure notes
                    JSHelper.showAlertDialog(myContext, response.message());
                }
            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {

                dismissProgress();
                JSHelper.showAlertDialog(myContext, COMMON_RESPONSE_NOT_RECEIVE_MSG);
            }
        });
    }

    private void dismissProgress() {
        try {
            if (myDialog != null) {
                myDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgress() {

        myProgressLAY.setVisibility(View.VISIBLE);
    }


    public void loadEventListItem(ArrayList<List1> aCategoryList) {
        myEventRC.setAdapter(null);
        if (aCategoryList != null && aCategoryList.size() > 0) {

            myEventAdapter = new JSEventListAdapter(myContext, aCategoryList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myContext);

            myEventRC.setLayoutManager(mLayoutManager);

            //lv_discover.scrollToPosition(0);
            myEventRC.setAdapter(myEventAdapter);

            myEventRC.setHasFixedSize(true);
            // lv_discover.scrollToPosition(0);
            myEventRC.setVisibility(View.VISIBLE);

            myEmptyTXT.setVisibility(View.GONE);

        } else {
            myEmptyTXT.setText("No dat found");
            myEventRC.setVisibility(View.GONE);
            myEmptyTXT.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setValues(ArrayList<String> al) {

    }
}