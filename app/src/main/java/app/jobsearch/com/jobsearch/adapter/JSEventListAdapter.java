package app.jobsearch.com.jobsearch.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


import java.util.ArrayList;
import java.util.List;

import app.jobsearch.com.jobsearch.R;
import app.jobsearch.com.jobsearch.helper.ConstantValues;
import app.jobsearch.com.jobsearch.helper.JSHelper;
import app.jobsearch.com.jobsearch.model.List1;
import app.jobsearch.com.jobsearch.model.List1;
import app.jobsearch.com.jobsearch.utils.Autoscrool.AutoScrollViewPager;

public class JSEventListAdapter extends RecyclerView.Adapter<JSEventListAdapter.MyViewHolder> implements ConstantValues {

    private ArrayList<List1> mModelList;

    //private ArrayList<List> selectedItemList;

    private ArrayList<String> selectedItemList = new ArrayList<>();
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
    Context mcontext;
    private String[] paths = {"https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=c493b482b47eca800d053ee7a1229712/8cb1cb1349540923abd671df9658d109b2de49d7.jpg",
            "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=45fbfa5555da81cb51e684cd6267d0a4/2f738bd4b31c8701491ea047237f9e2f0608ffe3.jpg",
            "https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=ae0e95c0fc1986185e47e8847aec2e69/0b46f21fbe096b63eb314ef108338744ebf8ac62.jpg",
            "https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=1fad2b46952397ddc9799f046983b216/dc54564e9258d109c94bbb13d558ccbf6d814de2.jpg",
            "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=ff0999f6d4160924c325a51be406359b/86d6277f9e2f070861ccd4a0ed24b899a801f241.jpg"};

    public JSEventListAdapter(Context context, ArrayList<List1> modelList
    ) {
        mModelList = modelList;
        mcontext = context;
        this.dtInterface = dtInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_sample_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        int seletedColor = ContextCompat.getColor(mcontext, R.color.colorAccent);
        //  int seletedColorBG=ContextCompat.getColor(mcontext, R.color.be_app_grey);

        final List1 model = mModelList.get(position);

        holder.myEventNameTXT.setText(model.getName());

        JSHelper.loadCompanyImage(mcontext, IMAGE_URL + model.getImageURL(), holder.myEventIM);

        holder.myEventIM.setCornerRadius(20, 20, 0, 0);
/*
        BaseViewPagerAdapter<String> adapter = new BaseViewPagerAdapter<String>(mcontext) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Picasso.with(mcontext).load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {
                textView.setText(s);
            }
        };
        holder.myAutoScrollVP.setAdapter(adapter);

        adapter.add(initData());*/

       /* holder.view.setBackgroundColor(model.isSelected() ? seletedColor : Color.WHITE);

        if (model.isSelected() == true) {
            holder.textView.setTextColor(model.isSelected() ? Color.WHITE : Color.BLACK);
            selectedItemList.add("" + model.getId());
        }

        holder.layout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? seletedColor : Color.WHITE);
                holder.textView.setTextColor(model.isSelected() ? Color.WHITE : Color.BLACK);

                // selectedItemList.add(""+model.getId());

                if (model.isSelected() == true) {
                    selectedItemList.add("" + model.getId());
                } else {
                    selectedItemList.remove("" + model.getId());
                }

                dtInterface.setValues(selectedItemList);
            }
        });
*/
        holder.myAutoScrollVP.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
            }
        });

        holder.myAutoScrollVP.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
              /*  ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(paths[position], view, options);
                container.addView(view);*/

                View item = LayoutInflater.from(container.getContext())
                        .inflate(R.layout.item_event_tour_page, container, false);


                RoundedImageView imgView = item.findViewById(R.id.rount_IM);
                imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                JSHelper.loadCompanyImage(mcontext, paths[position], imgView);
                // ImageLoader.getInstance().displayImage(paths[position], imgView, options);
                container.addView(item);


                return item;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        holder.myAutoScrollVP.setOffscreenPageLimit(4);
        holder.myAutoScrollVP.startAutoScroll(2000);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        Picture picture;
        for (int i = 0; i < paths.length; i++) {
            data.add(paths[i]);
        }
        return data;
    }


    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView myEventDateTXT, myEventNameTXT;
        private RelativeLayout layout_text;
        private RoundedImageView myEventIM;
        private AutoScrollViewPager myAutoScrollVP;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            myEventDateTXT = (TextView) itemView.findViewById(R.id.event_list_date_TXT);
            myEventNameTXT = (TextView) itemView.findViewById(R.id.list_eventName_TXT);
            myEventIM = (RoundedImageView) itemView.findViewById(R.id.event_IM);
            myAutoScrollVP = (AutoScrollViewPager) itemView.findViewById(R.id.scroll_pager);

        }
    }

    DataTransferInterface dtInterface;

    public interface DataTransferInterface {
        public void setValues(ArrayList<String> al);
    }


}
