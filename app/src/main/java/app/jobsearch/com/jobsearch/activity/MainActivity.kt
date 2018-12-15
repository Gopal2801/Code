package app.jobsearch.com.jobsearch.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.jobsearch.com.jobsearch.R
import com.unstoppable.submitbuttonview.SubmitButton
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    val mProgressBTN: SubmitButton? = null
    val mLoginBTN: SubmitButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_btn.setOnResultEndListener(SubmitButton.OnResultEndListener {
            Toast.makeText(this@MainActivity, "ResultEnd", Toast.LENGTH_SHORT).show()
        })
    }
}
