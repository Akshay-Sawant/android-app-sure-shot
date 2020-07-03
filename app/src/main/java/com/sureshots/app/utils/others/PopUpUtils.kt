package com.sureshots.app.utils.others

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class PopUpUtils(private val mContext: Context) {

    /**
     * Toast
     * @param mMessage
     * */
    fun onShortToast(mMessage: String) {
        return Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show()
    }

    fun onLongToast(mMessage: String) {
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