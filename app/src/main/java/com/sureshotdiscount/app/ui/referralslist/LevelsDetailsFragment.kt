package com.sureshotdiscount.app.ui.referralslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R

class LevelsDetailsFragment : Fragment(R.layout.fragment_levels_details) {

    private lateinit var mTextViewLevelsDetailsUnsubscribed: TextView
    private lateinit var mTextViewLevelsDetailsSubscribed: TextView

    private lateinit var mTextViewLevelsDetailsNoDataFound: TextView
    private lateinit var mRecyclerViewLevelsDetails: RecyclerView

    private lateinit var mLevelsDetailsAdapter: LevelsDetailsAdapter
    private var mLevelsDetailsModel: ArrayList<LevelsDetailsModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewLevelsDetailsUnsubscribed =
            view.findViewById(R.id.textViewLevelsDetailsUnsubscribed)
        mTextViewLevelsDetailsSubscribed = view.findViewById(R.id.textViewLevelsDetailsSubscribed)

        mTextViewLevelsDetailsNoDataFound = view.findViewById(R.id.textViewLevelsDetailsNoDataFound)
        mRecyclerViewLevelsDetails = view.findViewById(R.id.recyclerViewLevelsDetails)
    }
}