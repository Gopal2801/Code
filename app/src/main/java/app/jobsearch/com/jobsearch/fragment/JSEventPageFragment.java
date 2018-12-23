package app.jobsearch.com.jobsearch.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.jobsearch.com.jobsearch.R;


public class JSEventPageFragment extends Fragment {

    public static String TAG = SampleFragment.class.getSimpleName().toString();

    private FragmentActivity myContext;

    private EditText myEventNameEDT, myEventVenueEDT, myEventDateEDT, myEventPlaceEDT;


    private ImageView myEventEditIM, myEvenVenuEditIM, myEventDateEditIM, myEventPlaceEditIM;

    private TextView myHeaderTXT;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fargment_event_page, viewGroup, false);

        classIntalization(view);

        return view;
    }

    private void classIntalization(View aView) {

        myEventNameEDT = (EditText) aView.findViewById(R.id.eventName_EDT);
        myEventVenueEDT = (EditText) aView.findViewById(R.id.event_venue_EDT);
        myEventDateEDT = (EditText) aView.findViewById(R.id.event_date_EDT);
        myEventPlaceEDT = (EditText) aView.findViewById(R.id.event_place_EDT);
        myEventEditIM = (ImageView) aView.findViewById(R.id.event_name_edit_IM);
        myEvenVenuEditIM = (ImageView) aView.findViewById(R.id.event_venue_edit_IM);
        myEventDateEditIM = (ImageView) aView.findViewById(R.id.event_date_TXT_edit_IM);
        myEventPlaceEditIM = (ImageView) aView.findViewById(R.id.event_place_TXT_edit_IM);
        disableEDT();
        clickListener();

    }

    private void disableEDT() {
        myEventNameEDT.setEnabled(false);
        myEventVenueEDT.setEnabled(false);
        myEventDateEDT.setEnabled(false);
        myEventPlaceEDT.setEnabled(false);

    }

    private void clickListener() {
        myEventEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventNameEDT);
            }
        });
        myEvenVenuEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventVenueEDT);
            }
        });
        myEventDateEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventDateEDT);
            }
        });
        myEventPlaceEditIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEnableEDT(myEventPlaceEDT);
            }
        });

    }

    private void toEnableEDT(EditText aEditText) {
        aEditText.setEnabled(true);

    }
}