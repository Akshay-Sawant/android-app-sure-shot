package com.sureshotdiscount.app.ui.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.utils.others.ValidationUtils

class PlansFragment : Fragment(R.layout.fragment_plans) {

    private lateinit var mTextViewPlansNoDataFound: TextView
    private lateinit var mRecyclerViewPlans: RecyclerView

    private lateinit var mPlansAdapter: PlansAdapter
    private var mPlansModelList: ArrayList<PlansModel> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTextViewPlansNoDataFound = view.findViewById(R.id.textViewPlansNoDataFound)
        mRecyclerViewPlans = view.findViewById(R.id.recyclerViewPlans)
    }

    override fun onResume() {
        super.onResume()
        view?.let { ValidationUtils.getValidationUtils().hideKeyboardFunc(it) }
    }
}