package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_verify_o_t_p.*

class VerifyOTPActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var textViewResend : TextView
    private lateinit var Number: String


    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context,phoneNumber : String): Intent {
            mIntent = Intent(context, VerifyOTPActivity::class.java)
            mIntent.putExtra("number",phoneNumber)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonOTPVerifyAndProceed.setOnClickListener(this)
        Number = intent.getSerializableExtra("number") as String
        textViewResend = findViewById(R.id.textViewResend)
        textViewMobile.text = Number
        reverseTimer(60,textViewOTPTime)
        val ss = SpannableString(getString(R.string.text_did_not_receive_the_code))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@VerifyOTPActivity, "OTP sent again succesfully!", Toast.LENGTH_SHORT).show()
                textViewResend.movementMethod = null
                reverseTimer(60,textViewOTPTime)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 26, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textViewResend.text = ss
        //textViewResend.movementMethod = LinkMovementMethod.getInstance()

        editTextOTPOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPTwo.requestFocus()
                }
            }
        })
        editTextOTPTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPThree.requestFocus()
                }
            }
        })
        editTextOTPThree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if(count > 0){
                    editTextOTPFour.requestFocus()
                }
            }
        })
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
                tv.text = "0.00"
                textViewResend.movementMethod = LinkMovementMethod.getInstance()
            }
        }.start()
    }

    private fun verifyOtp() {
        /*Form Validation*/
        val firstDigit = editTextOTPOne.text.toString().trim()
        val secondDigit = editTextOTPTwo.text.toString().trim()
        val thirdDigit = editTextOTPThree.text.toString().trim()
        val fourthDigit = editTextOTPFour.text.toString().trim()

        if (firstDigit == "" || secondDigit == "" || thirdDigit == "" || fourthDigit == "") {
            val toast =
                Toast.makeText(this, "Please enter the OTP to continue!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }
    }
        /*Form Validation*/
}
