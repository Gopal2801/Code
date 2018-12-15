package app.jobsearch.com.jobsearch.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import app.jobsearch.com.jobsearch.R
import app.jobsearch.com.jobsearch.helper.Preference
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds
    var myPreference: Preference? = null;
    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            splash_im.setColorFilter(ContextCompat.getColor(applicationContext, R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);

            if (myPreference!!.status) {
                val intent = Intent(applicationContext, SampleActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, MainActivityAlpha::class.java)
                startActivity(intent)
                finish()

            }

            /* val intent = Intent(applicationContext, MainActivityAlpha::class.java)
             startActivity(intent)
             finish()*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        myPreference = Preference(applicationContext)
//        supportActionBar!!.hide()

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}
