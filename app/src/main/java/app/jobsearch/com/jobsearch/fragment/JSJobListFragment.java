package app.jobsearch.com.jobsearch.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.Collections;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.activity.SampleActivity;
import app.jobsearch.com.jobsearch.adapter.FoldingCellListAdapter;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSJobListFragment extends JSFragment implements ConstantValues {

    public static final String TAG = "JSJobListFragment";

    private TextView myUserNameTXT, myEmailTXT, myMobileNoTXT, myAddressTXT, myDOBTXT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM;

    private ListView myJobRC;

    private TextView myHeaderTXT;

    private ApiInterface myAPIInterface;

    private ArrayList<JobList_> myJobList;

    private ProgressDialog myDialog;

    private JellyToolbar mySearchBAR;

    private AppCompatEditText myEditText;

    private RelativeLayout myNoDataLAY;

    private ImageView myBackIM;

    private SearchView mySearchView;

    private FloatingActionButton myAddBTN;


    private Handler myHandler;

    private Runnable myRunnable;

    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_job,
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

        myFragmentManager = new JSFragmentManager(myContext);

        myJobRC = (ListView) aView.findViewById(R.id.job_rc);

        myHeaderTXT = (TextView) aView.findViewById(R.id.headerTxt);

        myNoDataLAY = (RelativeLayout) aView.findViewById(R.id.no_record_LAY);

        myBackIM = (ImageView) aView.findViewById(R.id.editbackim);

        mySearchView = (SearchView) aView.findViewById(R.id.jobsearchview);

        myAddBTN = (FloatingActionButton) aView.findViewById(R.id.add_float_Btn);


       /* mySearchBAR = (JellyToolbar) aView.findViewById(R.id.searchBar);

        mySearchBAR.setJellyListener(jellyListener);

        myEditText = (AppCompatEditText) LayoutInflater.from(myContext).inflate(R.layout.edit_text_lay, null);

        myEditText.setBackgroundResource(R.color.colorTransparent);

        mySearchBAR.setContentView(myEditText);
*/
        myDialog = new ProgressDialog(myContext);

        myJobList = new ArrayList<>();

        toSearchItem("");

        //setHeader();

        clickListener();

        // toLoadDummyList();

    }

    private void clickListener() {
        myBackIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SampleActivity) myContext).openMenu();
            }
        });


        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (myHandler != null) {
                    myHandler.removeCallbacks(myRunnable);
                }

                toSearchItem(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                if (newText.length() >= 3) {

                    myHandler = new Handler();

                    myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            toSearchItem(newText);
                        }
                    };
                    myHandler.postDelayed(myRunnable, CALL_REQ);
                }
                                   /*  myHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            toSearchItem(newText);

                        }
                    }, CALL_REQ);*/

                return false;
            }
        });

        myAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // myFragmentManager.clearAllFragments();

                Bundle aBundle = new Bundle();
                aBundle.putString("KEY", "BACK");
                myFragmentManager.updateContent(new JSJobCreateFragment(), JSJobCreateFragment.TAG, aBundle);

            }
        });


    }

    private JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(myEditText.getText())) {
                mySearchBAR.collapse();
            } else {
                myEditText.getText().clear();
            }
        }
    };


    private void setHeader() {

        ((SampleActivity) myContext).hideActionBar();

        myHeaderTXT.setText(myContext.getResources().getString(R.string.label_job_list));
    }

    private void toloadValue(ArrayList<JobList_> aList) {

        if (aList != null) {


            if (aList.size() > 0) {

                Collections.reverse(aList);


                myNoDataLAY.setVisibility(View.GONE);

                myJobRC.setVisibility(View.VISIBLE);

                // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
                final FoldingCellListAdapter adapter = new FoldingCellListAdapter(myContext, aList);

                // add default btn handler for each request btn on each item if custom handler not found
                adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        call();
                        //  Toast.makeText(myContext, "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
                    }
                });

                // set elements to adapter
                myJobRC.setAdapter(adapter);
                // set on click event listener to list view
                myJobRC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                        // toggle clicked cell state
                        ((FoldingCell) view).toggle(false);
                        // register in adapter that state for selected cell is toggled
                        adapter.registerToggle(pos);
                    }
                });
            } else {
                myNoDataLAY.setVisibility(View.VISIBLE);
                myJobRC.setVisibility(View.GONE);

            }
        } else {
            myNoDataLAY.setVisibility(View.VISIBLE);
            myJobRC.setVisibility(View.GONE);

        }
    }

    public void call() {

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + "9597389277"));

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

    @Override
    public boolean onResumeFragment() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

        setHeader();

        //updateListOfProduct();


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

        Call<JobList> call = myAPIInterface.getJobSearch(aSearchValue, myPref.getUserID(), "0");

        call.enqueue(new Callback<JobList>() {
            @Override
            public void onResponse(Call<JobList> call, Response<JobList> response) {

                Log.e("RESPONSE", response.message());

                JobList aJob = response.body();

                if (aJob != null) {

                    myJobList = aJob.getJobList();

                    if (myJobList != null) {
                        toloadValue(myJobList);
                    }

                    if (myDialog != null) {
                        myDialog.dismiss();

                    }

                } else {

                    myJobList = new ArrayList<>();

                    if (myJobList != null) {
                        toloadValue(myJobList);
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
}
