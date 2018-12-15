package app.jobsearch.com.jobsearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.unstoppable.submitbuttonview.SubmitButton;

import app.jobsearch.com.jobsearch.R;

public class JSLoginActivity extends AppCompatActivity {

    private MaterialEditText myEmailEDT, myPwdEDT;
    private SubmitButton myLoginBTN, myLoadingBTN;
    FragmentActivity myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        myContext = JSLoginActivity.this;
        classInitialization();
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
                myLoadingBTN.doResult(true);
                myLoadingBTN.setVisibility(View.VISIBLE);
                myLoginBTN.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent i = new Intent(myContext, JSSignupActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();
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
