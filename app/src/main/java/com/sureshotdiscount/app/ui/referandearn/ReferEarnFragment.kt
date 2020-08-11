package com.sureshotdiscount.app.ui.referandearn

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils

class ReferEarnFragment : Fragment(R.layout.fragment_refer_earn), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var mTextViewReferAndEarnVerifyYourKYC: TextView

    private lateinit var mTextInputLayoutReferAndEarnName: TextInputLayout
    private lateinit var mTextInputEditTextReferAndEarnName: TextInputEditText

    private lateinit var mTextInputLayoutReferAndEarnEmailId: TextInputLayout
    private lateinit var mTextInputEditTextReferAndEarnEmailId: TextInputEditText

    private lateinit var mTextInputLayoutReferAndEarnAddress: TextInputLayout
    private lateinit var mTextInputEditTextReferAndEarnAddress: TextInputEditText

    private lateinit var mTextInputLayoutReferAndEarnPanNo: TextInputLayout
    private lateinit var mTextInputEditTextReferAndEarnPanNo: TextInputEditText

    private lateinit var mImageViewReferAndEarnUploadAddressProof: ImageView
    private lateinit var mTextViewReferAndEarnUploadAddressProof: TextView

    private lateinit var mImageViewReferAndEranUploadPanCard: ImageView
    private lateinit var mTextTextViewReferAndEarnUploadPanCard: TextView

    private lateinit var mCheckBoxReferAndEarnAcceptContract: CheckBox

    private lateinit var mButtonReferAndEarnVerify: Button

    private lateinit var mTextViewReferAndEarnReferralCode: TextView
    private lateinit var mTextViewReferAndEarnTapToCopy: TextView
    private lateinit var mButtonReferAndEarnShareNow: Button

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewReferAndEarnVerifyYourKYC =
            view.findViewById(R.id.textViewReferAndEarnVerifyYourKYC)

        mTextInputLayoutReferAndEarnName = view.findViewById(R.id.textInputLayoutReferAndEarnName)
        mTextInputEditTextReferAndEarnName =
            view.findViewById(R.id.textInputEditTextReferAndEarnName)

        mTextInputLayoutReferAndEarnEmailId =
            view.findViewById(R.id.textInputLayoutReferAndEarnEmailId)
        mTextInputEditTextReferAndEarnEmailId =
            view.findViewById(R.id.textInputEditTextReferAndEarnEmailId)

        mTextInputLayoutReferAndEarnAddress =
            view.findViewById(R.id.textInputLayoutReferAndEarnAddress)
        mTextInputEditTextReferAndEarnAddress =
            view.findViewById(R.id.textInputEditTextReferAndEarnAddress)

        mTextInputLayoutReferAndEarnPanNo = view.findViewById(R.id.textInputLayoutReferAndEarnPanNo)
        mTextInputEditTextReferAndEarnPanNo =
            view.findViewById(R.id.textInputEditTextReferAndEarnPanNo)

        mImageViewReferAndEarnUploadAddressProof =
            view.findViewById(R.id.imageViewReferAndEarnUploadAddressProof)
        mImageViewReferAndEarnUploadAddressProof.setOnClickListener(this@ReferEarnFragment)

        mTextViewReferAndEarnUploadAddressProof =
            view.findViewById(R.id.textViewReferAndEarnAddressProof)

        mImageViewReferAndEranUploadPanCard =
            view.findViewById(R.id.imageViewReferAndEarnUploadPanCard)
        mImageViewReferAndEranUploadPanCard.setOnClickListener(this@ReferEarnFragment)

        mTextTextViewReferAndEarnUploadPanCard = view.findViewById(R.id.textViewReferAndEarnPanCard)

        mCheckBoxReferAndEarnAcceptContract =
            view.findViewById(R.id.checkboxReferAndEarnAcceptContract)
        mCheckBoxReferAndEarnAcceptContract.setOnCheckedChangeListener(this@ReferEarnFragment)

        mButtonReferAndEarnVerify = view.findViewById(R.id.buttonReferAndEarnVerify)
        mButtonReferAndEarnVerify.setOnClickListener(this@ReferEarnFragment)

        mTextViewReferAndEarnReferralCode = view.findViewById(R.id.textViewReferralCode)
        mTextViewReferAndEarnTapToCopy = view.findViewById(R.id.textViewTapToCopy)
        mTextViewReferAndEarnTapToCopy.setOnClickListener(this@ReferEarnFragment)

        mButtonReferAndEarnShareNow = view.findViewById(R.id.buttonShareNow)
        mButtonReferAndEarnShareNow.setOnClickListener(this@ReferEarnFragment)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        mTextViewReferAndEarnReferralCode.text = mSharedPreferenceUtils.getLoggedInUser().referralid
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewTapToCopy -> onClickTapToCopy()
            R.id.buttonShareNow -> onClickShareNow()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {
            R.id.checkboxReferAndEarnAcceptContract -> {

            }
        }
    }

    private fun onClickTapToCopy() {
        val clipboard: ClipboardManager? =
            context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip =
            ClipData.newPlainText(
                getString(R.string.text_your_referral_code),
                mSharedPreferenceUtils.getLoggedInUser().referralid
            )
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    private fun onClickShareNow() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Sure Shot Discounts")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Via")
        startActivity(shareIntent)
    }
}