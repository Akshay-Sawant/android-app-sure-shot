package com.sureshots.app.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sureshots.app.R
import kotlinx.android.synthetic.main.activity_recharge_two.*

class RechargeTwoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var phoneNumber :String

    companion object {
        private lateinit var mIntent: Intent
        fun newIntent(context: Context,phoneNumber:String): Intent {
            mIntent = Intent(context, RechargeTwoActivity::class.java)
            mIntent.putExtra("phoneNumber",phoneNumber)
            return mIntent
        }
        fun newIntentNewTask(context: Context,phoneNumber:String): Intent {
            mIntent = Intent(context, RechargeTwoActivity::class.java)
            mIntent.putExtra("phoneNumber",phoneNumber)
            return mIntent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_two)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        buttonProceed.setOnClickListener(this)
        textViewChangeSim.setOnClickListener(this)
        textViewChangeNumber.setOnClickListener(this)
        phoneNumber = intent.getSerializableExtra("phoneNumber") as String
        textViewMobileNumber.setText(phoneNumber)
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
            R.id.buttonProceed -> startActivity(SubscriptionActivity.newIntent(this))
            R.id.textViewChangeSim -> {
                // startActivity(DashboardMainActivity.newIntentFromReTwo(this))
            }
            R.id.textViewChangeNumber -> {
                textViewMobileNumber.isFocusableInTouchMode = true
                //textViewMobileNumber.requestFocus()
                Toast.makeText(this, "Text Editable", Toast.LENGTH_SHORT).show()
                //startActivity(RechargeOneActivity.newIntentFromReTwo(this))
            }
        }
    }
}
