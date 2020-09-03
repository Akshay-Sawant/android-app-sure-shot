package com.sureshotdiscount.app.ui.referandearn

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.api.APIClient
import com.sureshotdiscount.app.utils.error.ErrorUtils
import com.sureshotdiscount.app.utils.others.AlertDialogUtils
import com.sureshotdiscount.app.utils.others.SharedPreferenceUtils
import com.sureshotdiscount.app.utils.others.ValidationUtils
import com.sureshotdiscount.app.utils.server.ServerInvalidResponseException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

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

    private lateinit var mMaterialCardViewReferAndEarn: MaterialCardView

    private lateinit var mTextViewYourReferralCode: TextView
    private lateinit var mTextViewReferAndEarnReferralCode: TextView
    private lateinit var mTextViewReferAndEarnTapToCopy: TextView
    private lateinit var mButtonReferAndEarnShareNow: Button

    private lateinit var mContentLoadingProgressBarReferAndEarn: ContentLoadingProgressBar

    private lateinit var mSharedPreferenceUtils: SharedPreferenceUtils

    private val TAKE_PICTURE = 10
    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 123
    private var filePath: Uri? = null

    private var mAddressProofBody: MultipartBody.Part? = null
    private var mPanCardBody: MultipartBody.Part? = null

    private var mIsAddressProofClicked: Boolean = false
    private var mIsPanCardClicked: Boolean = false
    private var mShareableData: String? = null

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

        mMaterialCardViewReferAndEarn = view.findViewById(R.id.materialCardViewReferAndEarn)

        mTextViewYourReferralCode = view.findViewById(R.id.textViewYourReferralCode)
        mTextViewReferAndEarnReferralCode = view.findViewById(R.id.textViewReferralCode)
        mTextViewReferAndEarnTapToCopy = view.findViewById(R.id.textViewTapToCopy)
        mTextViewReferAndEarnTapToCopy.setOnClickListener(this@ReferEarnFragment)

        mButtonReferAndEarnShareNow = view.findViewById(R.id.buttonShareNow)
        mButtonReferAndEarnShareNow.setOnClickListener(this@ReferEarnFragment)

        mContentLoadingProgressBarReferAndEarn =
            view.findViewById(R.id.contentLoadingProgressBarReferAndEarn)

        context?.let {
            mSharedPreferenceUtils = SharedPreferenceUtils(it)
        }
        mTextViewReferAndEarnReferralCode.text = mSharedPreferenceUtils.getLoggedInUser().referralid
    }

    override fun onResume() {
        super.onResume()
        mContentLoadingProgressBarReferAndEarn.show()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
        checkIfKYCIsDone()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewReferAndEarnUploadAddressProof -> {
                mIsAddressProofClicked = true
                mIsPanCardClicked = false
                openAlert()
            }
            R.id.imageViewReferAndEarnUploadPanCard -> {
                mIsPanCardClicked = true
                mIsAddressProofClicked = false
                openAlert()
            }
            R.id.buttonReferAndEarnVerify -> isReferEarnValidated()
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

    //This method will be called when the user will tap on allow or deny
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(
                    context,
                    "Permission granted now you can read the storage",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(context, "Oops you just denied the permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    //handling the image chooser activity result
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_IMAGE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                    filePath = data.data
                    try {
                        if (filePath == null) {
                            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
                            if (mIsAddressProofClicked) {
                                mIsAddressProofClicked = false
                            } else {
                                mIsPanCardClicked = false
                            }
                        } else {
                            if (mIsAddressProofClicked) {
                                getAddressProof(filePath)
                                mIsPanCardClicked = false
                            }
                            if (mIsPanCardClicked) {
                                getPanCard(filePath)
                                mIsAddressProofClicked = false
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            TAKE_PICTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val photo = data!!.extras!!["data"] as Bitmap?
                    filePath = photo?.let { getImageUri(it) }
                    try {
                        if (filePath == null) {
                            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
                            if (mIsAddressProofClicked) {
                                mIsAddressProofClicked = false
                            } else {
                                mIsPanCardClicked = false
                            }
                        } else {
                            if (mIsAddressProofClicked) {
                                getAddressProof(filePath)
                            }
                            if (mIsPanCardClicked) {
                                getPanCard(filePath)
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(context, "No Image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context?.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private fun openAlert() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Your Photo")
        builder.setItems(options) { dialog: DialogInterface?, which: Int ->
            when (options[which]) {
                "Take Photo" -> {
                    takePhoto()
                }
                "Choose from Gallery" -> {
                    requestStoragePermission()
                }
                "Cancel" -> {
                    dialog?.dismiss()
                }
            }
        }

        builder.show()
    }

    private fun takePhoto() {
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath)
        startActivityForResult(intent, TAKE_PICTURE)
    }

    //Requesting permission
    private fun requestStoragePermission() {
        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED -> {
                showFileChooser()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                AlertDialogUtils.getInstance().showAlert(
                    context as Activity,
                    R.drawable.ic_check_circle_black,
                    "Alert",
                    "This app needs to access your device's settings to change the permission for gallery. Please allow!",
                    getString(android.R.string.ok),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    },
                    getString(R.string.text_label_cancel),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    }
                )
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.CAMERA
            ) -> {
                AlertDialogUtils.getInstance().showAlert(
                    context as Activity,
                    R.drawable.ic_check_circle_black,
                    "Alert",
                    "This app needs to access your device's settings to change the permission for camera. Please allow!",
                    getString(android.R.string.ok),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    },
                    getString(R.string.text_label_cancel),
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    }
                )
            }
            else -> {
                //And finally ask for the permission
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                    STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    //method to show file chooser
    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //method to get the file path from uri
    private fun getPath(uri: Uri?): String? {
        var path: String? = null
        var cursor: Cursor? =
            uri?.let {
                context?.contentResolver?.query(
                    it,
                    null,
                    null,
                    null,
                    null
                )
            }
        cursor?.moveToFirst()
        var document_id: String = cursor?.getString(0).toString()
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor?.close()
        cursor = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
        )
        cursor?.moveToFirst()
        path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor?.close()

        return path
    }

    private fun isReferEarnValidated() {
        when {
            !ValidationUtils.getValidationUtils()
                .isInputEditTextFilledFunc(
                    mTextInputEditTextReferAndEarnName,
                    mTextInputLayoutReferAndEarnName,
                    getString(R.string.text_error_empty_field)
                ) -> return
            !ValidationUtils.getValidationUtils()
                .isInputEditTextEmailFunc(
                    mTextInputEditTextReferAndEarnEmailId,
                    mTextInputLayoutReferAndEarnEmailId,
                    getString(R.string.text_error_email_id)
                ) -> return
            !ValidationUtils.getValidationUtils()
                .isInputEditTextFilledFunc(
                    mTextInputEditTextReferAndEarnAddress,
                    mTextInputLayoutReferAndEarnAddress,
                    getString(R.string.text_error_empty_field)
                ) -> return
            !ValidationUtils.getValidationUtils()
                .isInputEditTextFilledFunc(
                    mTextInputEditTextReferAndEarnPanNo,
                    mTextInputLayoutReferAndEarnPanNo,
                    getString(R.string.text_error_empty_field)
                ) -> return
            !mCheckBoxReferAndEarnAcceptContract.isChecked ->
                context?.let {
                    AlertDialogUtils.getInstance().showAlert(
                        it,
                        R.drawable.ic_warning_black,
                        "Alert",
                        "Please accept the contract by checking the check box.",
                        getString(android.R.string.ok),
                        null,
                        DialogInterface.OnDismissListener {
                            it.dismiss()
                            view?.let { it1 ->
                                ValidationUtils.getValidationUtils()
                                    .hideKeyboardFunc(
                                        it1
                                    )
                            }
                        }
                    )
                }
            else -> {
                context?.let {
                    if (mSharedPreferenceUtils.getSubscriptionDone(it)!!) {
                        Toast.makeText(context, "KYC Done", Toast.LENGTH_SHORT).show()
                        /*val mLoginToken = mSharedPreferenceUtils.getLoggedInUser().loginToken
                            .toRequestBody("text/plain".toMediaTypeOrNull())
                        val mName = mTextInputEditTextReferAndEarnName.text.toString().trim()
                            .toRequestBody("text/plain".toMediaTypeOrNull())
                        val mEmailId = mTextInputEditTextReferAndEarnEmailId.text.toString().trim()
                            .toRequestBody("text/plain".toMediaTypeOrNull())
                        val mAddress = mTextInputEditTextReferAndEarnAddress.text.toString().trim()
                            .toRequestBody("text/plain".toMediaTypeOrNull())
                        val mPanNo = mTextInputEditTextReferAndEarnPanNo.text.toString().trim()
                            .toRequestBody("text/plain".toMediaTypeOrNull())
                        val mContract = mCheckBoxReferAndEarnAcceptContract.isChecked.toString()
                            .toRequestBody("text/plain".toMediaTypeOrNull())

                        onClickVerify(
                            mLoginToken,
                            mName,
                            mEmailId,
                            mAddress,
                            mPanNo,
                            mContract
                        )*/
                    } else {
                        AlertDialogUtils.getInstance().showAlert(
                            it,
                            R.drawable.ic_warning_black,
                            "Subscription Pending",
                            "Please do complete your subscription to proceed further.",
                            getString(android.R.string.ok),
                            null,
                            DialogInterface.OnDismissListener {
                                it.dismiss()
                                view?.let { it1 ->
                                    ValidationUtils.getValidationUtils()
                                        .hideKeyboardFunc(
                                            it1
                                        )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getAddressProof(mFilePath: Uri?) {
        if (mFilePath != null) {
            Glide.with(this)
                .load(filePath)
                .into(mImageViewReferAndEarnUploadAddressProof)

            //pass it like this
            val mAddressProofFile = File(getPath(filePath)!!)
            val mAddressProofRequestFile: RequestBody =
                mAddressProofFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            // MultipartBody.Part is used to send also the actual file name
            mAddressProofBody =
                MultipartBody.Part.createFormData(
                    "image",
                    mAddressProofFile.name,
                    mAddressProofRequestFile
                )
        }
    }

    private fun getPanCard(mFilePath: Uri?) {
        if (mFilePath != null) {
            Glide.with(this)
                .load(filePath)
                .into(mImageViewReferAndEranUploadPanCard)

            //pass it like this
            val mPanCardFile = File(getPath(filePath)!!)
            val mPanCardRequestFile: RequestBody =
                mPanCardFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            // MultipartBody.Part is used to send also the actual file name
            mPanCardBody =
                MultipartBody.Part.createFormData(
                    "image",
                    mPanCardFile.name,
                    mPanCardRequestFile
                )
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
            putExtra(Intent.EXTRA_TEXT, mShareableData)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share Via")
        startActivity(shareIntent)
    }

    private fun showKYCForm() {
        //KYC Form VISIBLE
        mTextViewReferAndEarnVerifyYourKYC.visibility = View.VISIBLE
        mTextInputLayoutReferAndEarnName.visibility = View.VISIBLE
        mTextInputLayoutReferAndEarnEmailId.visibility = View.VISIBLE
        mTextInputLayoutReferAndEarnAddress.visibility = View.VISIBLE
        mTextInputLayoutReferAndEarnPanNo.visibility = View.VISIBLE

        mImageViewReferAndEarnUploadAddressProof.visibility = View.VISIBLE
        mTextViewReferAndEarnUploadAddressProof.visibility = View.VISIBLE

        mImageViewReferAndEranUploadPanCard.visibility = View.VISIBLE
        mTextTextViewReferAndEarnUploadPanCard.visibility = View.VISIBLE

        mCheckBoxReferAndEarnAcceptContract.visibility = View.VISIBLE

        mButtonReferAndEarnVerify.visibility = View.VISIBLE

        //Referral Code GONE
        mMaterialCardViewReferAndEarn.visibility = View.GONE

        mTextViewYourReferralCode.visibility = View.GONE
        mTextViewReferAndEarnReferralCode.visibility = View.GONE
        mTextViewReferAndEarnTapToCopy.visibility = View.GONE

        mButtonReferAndEarnShareNow.visibility = View.GONE
    }

    private fun showReferralCode() {
        //KYC Form GONE
        mTextViewReferAndEarnVerifyYourKYC.visibility = View.GONE
        mTextInputLayoutReferAndEarnName.visibility = View.GONE
        mTextInputLayoutReferAndEarnEmailId.visibility = View.GONE
        mTextInputLayoutReferAndEarnAddress.visibility = View.GONE
        mTextInputLayoutReferAndEarnPanNo.visibility = View.GONE

        mImageViewReferAndEarnUploadAddressProof.visibility = View.GONE
        mTextViewReferAndEarnUploadAddressProof.visibility = View.GONE

        mImageViewReferAndEranUploadPanCard.visibility = View.GONE
        mTextTextViewReferAndEarnUploadPanCard.visibility = View.GONE

        mCheckBoxReferAndEarnAcceptContract.visibility = View.GONE

        mButtonReferAndEarnVerify.visibility = View.GONE

        //Referral Code VISIBLE
        mMaterialCardViewReferAndEarn.visibility = View.VISIBLE

        mTextViewYourReferralCode.visibility = View.VISIBLE
        mTextViewReferAndEarnReferralCode.visibility = View.VISIBLE
        mTextViewReferAndEarnTapToCopy.visibility = View.VISIBLE

        mButtonReferAndEarnShareNow.visibility = View.VISIBLE
    }

    private fun checkIfKYCIsDone() {
        if (context?.let { mSharedPreferenceUtils.getIsKYCDone(it) }!!) {
            mContentLoadingProgressBarReferAndEarn.hide()
            showReferralCode()
        } else {
            mContentLoadingProgressBarReferAndEarn.hide()
            showKYCForm()
        }
    }

    private fun onClickVerify(
        mLoginToken: RequestBody,
        mName: RequestBody,
        mEmailId: RequestBody,
        mAddress: RequestBody,
        mPanNo: RequestBody,
        mContract: RequestBody
    ) {
        context?.let {
            if (APIClient.isNetworkConnected(it)) {
                APIClient.apiInterface
                    .fillKyc(
                        mLoginToken,
                        mName,
                        mEmailId,
                        mAddress,
                        mPanNo,
                        mAddressProofBody,
                        mPanCardBody,
                        mContract
                    )
                    .enqueue(object : Callback<KYCModel> {
                        override fun onResponse(
                            call: Call<KYCModel>,
                            response: Response<KYCModel>
                        ) {
                            if (response.isSuccessful) {
                                val mKYCModel: KYCModel? = response.body()
                                mContentLoadingProgressBarReferAndEarn.visibility = View.GONE

                                if (mKYCModel != null) {
                                    if (mKYCModel.mStatus) {
                                        mSharedPreferenceUtils.saveIsKYCDone(mKYCModel.mKYCDetailsModel.mKycStatus)

                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_check_circle_black,
                                            mKYCModel.mTitle,
                                            mKYCModel.mMessage,
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener { mDialogInterface ->
                                                if (mSharedPreferenceUtils.getIsKYCDone(it)) {
                                                    showReferralCode()

                                                    mTextViewReferAndEarnReferralCode.text =
                                                        mKYCModel.mKYCDetailsModel.mReferralCode
                                                    mShareableData =
                                                        mKYCModel.mKYCDetailsModel.mShareNow
                                                } else {
                                                    showKYCForm()
                                                }
                                                mDialogInterface.dismiss()
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(
                                                            it1
                                                        )
                                                }
                                            }
                                        )
                                    } else {
                                        AlertDialogUtils.getInstance().showAlert(
                                            it,
                                            R.drawable.ic_warning_black,
                                            mKYCModel.mTitle,
                                            mKYCModel.mMessage,
                                            getString(android.R.string.ok),
                                            null,
                                            DialogInterface.OnDismissListener {
                                                it.dismiss()
                                                view?.let { it1 ->
                                                    ValidationUtils.getValidationUtils()
                                                        .hideKeyboardFunc(
                                                            it1
                                                        )
                                                }
                                            }
                                        )
                                    }
                                } else {
                                    ErrorUtils.logNetworkError(
                                        ServerInvalidResponseException.ERROR_200_BLANK_RESPONSE +
                                                "\nResponse: " + response.toString(),
                                        null
                                    )
                                    AlertDialogUtils.getInstance().displayInvalidResponseAlert(it)
                                }
                            }
                        }

                        override fun onFailure(call: Call<KYCModel>, t: Throwable) {
                            ErrorUtils.parseOnFailureException(
                                it,
                                call,
                                t
                            )
                            mContentLoadingProgressBarReferAndEarn.visibility = View.GONE
                        }
                    })
            } else {
                mContentLoadingProgressBarReferAndEarn.visibility = View.GONE
                AlertDialogUtils.getInstance().displayNoConnectionAlert(it)
            }
        }
    }
}