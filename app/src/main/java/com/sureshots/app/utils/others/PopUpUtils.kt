package com.sureshots.app.utils.others

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class PopUpUtils {

    companion object {
        private var INSTANCE: PopUpUtils? = null

        fun getPopUpUtils(): PopUpUtils {
            synchronized(this@Companion) {
                var mPopUpUtilsInstance = INSTANCE

                if (mPopUpUtilsInstance == null) {
                    mPopUpUtilsInstance = PopUpUtils()
                }
                return mPopUpUtilsInstance
            }
        }
    }

    /**
     * Toast
     * @param mMessage
     * */
    fun onShortToast(mContext: Context, mMessage: String) {
        return Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show()
    }

    fun onLongToast(mContext: Context, mMessage: String) {
        return Toast.makeText(mContext, mMessage, Toast.LENGTH_LONG).show()
    }

    /**
     * Snackbar
     * @param mView
     * @param mMessage
     * */
    fun onShortSnackbar(mView: ViewGroup, mMessage: String) {
        Snackbar.make(mView, mMessage, Snackbar.LENGTH_SHORT).show()
    }

    fun onLongSnackbar(mView: ViewGroup, mMessage: String) {
        Snackbar.make(mView, mMessage, Snackbar.LENGTH_LONG).show()
    }

    fun onIndefiniteSnackbar(mView: ViewGroup, mMessage: String, mDuration: Int) {
        Snackbar.make(mView, mMessage, Snackbar.LENGTH_INDEFINITE).show()
    }
}