package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_verify_o_t_p.*

class VerifyOTPActivity : AppCompatActivity(),View.OnClickListener {

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context): Intent {
            mIntent = Intent(context, VerifyOTPActivity::class.java)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonOTPVerifyAndProceed.setOnClickListener(this)
        reverseTimer(60,textViewOTPTime)
        val ss = SpannableString(getString(R.string.text_did_not_receive_the_code))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@VerifyOTPActivity, "OTP sent again succesfully!", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 26, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val textView = findViewById(R.id.textViewResend) as TextView
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonOTPVerifyAndProceed -> startActivity(DashboardMainActivity.newIntent(this))
        }
    }

    private fun reverseTimer(Seconds: Int, tv: TextView) {
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60
                tv.text = String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
            }
            override fun onFinish() {
                tv.text = "Completed"
            }
        }.start()
    }
}
