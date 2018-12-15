package app.jobsearch.com.jobsearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.model.Experience;


public class SampleExpAdapter extends
        RecyclerView.Adapter<SampleExpAdapter.MyViewHolder> {

    private ArrayList<Experience> myList;

    private Context myContext;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView myTitleTXT, mySecondTXT, myThirdTXT, myPeriodTXT;

        public MyViewHolder(View view) {
            super(view);
            myTitleTXT = (TextView) view.findViewById(R.id.title_TXT);
            mySecondTXT = (TextView) view.findViewById(R.id.second_TXT);
            myThirdTXT = (TextView) view.findViewById(R.id.third_XT);
            myPeriodTXT = (TextView) view.findViewById(R.id.period_TXT);

        }
    }

    public SampleExpAdapter(Context aContext, ArrayList<Experience> countryList) {
        myContext = aContext;
        this.myList = countryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Experience aAttend = myList.get(position);
        holder.myTitleTXT.setText(aAttend.getJob());
        if (!aAttend.getCity().equals("")) {
            holder.mySecondTXT.setText(aAttend.getCompany() + "-" + aAttend.getCity());
        } else {

            if (!aAttend.getCompany().equals("")) {
                holder.mySecondTXT.setText(aAttend.getCompany());

            } else {
                holder.mySecondTXT.setVisibility(View.GONE);
            }
        }

        if (!aAttend.getTo().equals("")) {
            holder.myPeriodTXT.setText(aAttend.getFrom() + " to " + aAttend.getTo());
        } else {
            if (!aAttend.getFrom().equals("")) {
                holder.myPeriodTXT.setText(aAttend.getFrom());

            } else {
                holder.myPeriodTXT.setVisibility(View.GONE);
            }
        }

      /*  if (!aAttend.getDescription().equals("")) {
            holder.myThirdTXT.setText(aAttend.getDescription());

        } else {
            holder.myThirdTXT.setVisibility(View.GONE);

        }
*/

        holder.myThirdTXT.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_education_details, parent, false);
        return new MyViewHolder(v);
    }
}
