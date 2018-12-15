package app.jobsearch.com.jobsearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unstoppable.submitbuttonview.SubmitButton;

import app.jobsearch.com.jobsearch.R;


public class JSSignupActivity extends AppCompatActivity {

    private MaterialEditText myEmailEDT, myPwdEDT;
    private SubmitButton myLoginBTN, myLoadingBTN;
    FragmentActivity myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        myContext = JSSignupActivity.this;
        //  classInitialization();
    }

    private void classInitialization() {
        myEmailEDT = (MaterialEditText) findViewById(R.id.emailEDT);
        myPwdEDT = (MaterialEditText) findViewById(R.id.pwdEDT);
        myLoginBTN = (SubmitButton) findViewById(R.id.login_btn);
        myLoadingBTN = (SubmitButton) findViewById(R.id.login_progress);
        clickListener();
    }

    private void clickListener() {

        myLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLoadingBTN.setVisibility(View.VISIBLE);
                myLoginBTN.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
            }
        });
        myLoginBTN.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {

            }
        });
    }
}
