package app.jobsearch.com.jobsearch.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragment;
import app.jobsearch.com.jobsearch.fragmentmanager.JSFragmentManager;
import app.jobsearch.com.jobsearch.helper.ApiInterface;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.helper.Preference;
import app.jobsearch.com.jobsearch.model.Qualification;
import app.jobsearch.com.jobsearch.model.UserInfo;
import app.jobsearch.com.jobsearch.service.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JSJobSearchFragment extends JSFragment implements ConstantValues {

    public static final String TAG = JSJobSearchFragment.class.getSimpleName().toString();

    private TextView myUserNameTXT, myEmailTXT, myMobileNoTXT, myAddressTXT, myDOBTXT;

    private UserInfo myUserInfo;

    private Preference myPref;

    private FragmentActivity myContext;

    private JSFragmentManager myFragmentManager;

    private ImageView myEditIM;

    private ApiInterface myAPIInterface;

    private String mySearchValue = "Android";

    private SearchView mySearchView;

    private ListView myJobLV;

    @Override
    public View onCreateView(LayoutInflater aInflater, ViewGroup aContainer, Bundle savedInstanceState) {
        View aView = null;

        try {
            myContext = getActivity();

            // Inflate the layout for this fragment
            aView = aInflater.inflate(R.layout.fragment_job_search,
                    aContainer, false);

            classInitialization(aView);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // return the view
        return aView;
    }

    private void classInitialization(View aView) {

        mySearchView = (SearchView) aView.findViewById(R.id.jobsearchview);

        myJobLV = (ListView) aView.findViewById(R.id.job_search_lv);


        clickListener();

//toSearchItem();
    }

    private void clickListener() {

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {

                if (newText.length() >= 3) {

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            toSearchItem(newText);
                        }
                    }, CALL_REQ);

                }
                return false;
            }
        });
    }

    private void toSearchItem(String aItem) {
        if (JSHelper.CheckInternet(myContext)) {
           // getSearchItem(aItem);

        } else {

            JSHelper.showAlertDialog(myContext, ALERT_NETWORK_NOT_AVAILABLE);

        }
    }

  /*  public void getSearchItem(String aSearchValue) {

        myAPIInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<JobList>> call = myAPIInterface.getJobSearch(aSearchValue);

        call.enqueue(new Callback<ArrayList<JobList>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseBody>> call, Response<ArrayList<ResponseBody>> response) {

                Log.e("RESPONSE", response.message());

                // myExperienceList = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<ResponseBody>> call, Throwable t) {

            }
        });
    }*/

    @Override
    public boolean onResumeFragment() {
        return false;
    }
}
