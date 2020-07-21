package com.sureshotdiscount.app.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import com.sureshotdiscount.app.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * A simple [Fragment] subclass.
 */
class PaymentSuccessfulFragment : Fragment(R.layout.fragment_payment_successful),
    View.OnClickListener {

    private lateinit var mImageViewPaymentSuccessfulStatus: ImageView
    private lateinit var mTextViewPaymentSuccessfulStatus: TextView

    private lateinit var mTextViewPaymentSuccessfulRechargeFor: TextView
    private lateinit var mTextViewPaymentSuccessfulMobileNumber: TextView
    private lateinit var mCircleImageViewPaymentSuccessfulCompanyLogo: CircleImageView

    private lateinit var mTextViewPaymentSuccessfulOrderId: TextView
    private lateinit var mTextViewPaymentSuccessfulPaymentMedium: TextView

    private lateinit var mButtonPaymentSuccessfulContinue: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mImageViewPaymentSuccessfulStatus = view.findViewById(R.id.imageViewPaymentSuccessfulStatus)
        mTextViewPaymentSuccessfulStatus = view.findViewById(R.id.textViewPaymentSuccessfulStatus)

        mTextViewPaymentSuccessfulRechargeFor =
            view.findViewById(R.id.textViewPaymentSuccessfulRechargeFor)
        mTextViewPaymentSuccessfulMobileNumber =
            view.findViewById(R.id.textViewPaymentSuccessfulMobileNumber)
        mCircleImageViewPaymentSuccessfulCompanyLogo =
            view.findViewById(R.id.circleImageViewPaymentSuccessfulCompanyLogo)

        mTextViewPaymentSuccessfulOrderId = view.findViewById(R.id.textViewPaymentSuccessfulOrderId)
        mTextViewPaymentSuccessfulPaymentMedium =
            view.findViewById(R.id.textViewPaymentSuccessfulPaymentMedium)

        mButtonPaymentSuccessfulContinue = view.findViewById(R.id.buttonPaymentSuccessfulContinue)
        mButtonPaymentSuccessfulContinue.setOnClickListener(this@PaymentSuccessfulFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPaymentSuccessfulContinue -> view?.let {
                Navigation.findNavController(it)
                    .popBackStack(R.id.DashboardFragment, false)
            }
        }
    }
}