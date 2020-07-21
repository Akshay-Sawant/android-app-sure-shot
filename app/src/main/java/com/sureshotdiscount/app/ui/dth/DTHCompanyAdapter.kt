package com.sureshotdiscount.app.ui.dth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.model.DTHCompanyModel
import kotlinx.android.synthetic.main.recycler_view_dth_company.view.*

class DTHCompanyAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mDTHCompanyModelList: List<DTHCompanyModel>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<DTHCompanyAdapter.DTHCompanyViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(mView: View, mPosition: DTHCompanyModel)
    }

    inner class DTHCompanyViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        private val mConstrainLayoutDTHCompany: ConstraintLayout =
            mItemView.findViewById(R.id.constrainLayoutDTHCompany)

        init {
            mConstrainLayoutDTHCompany.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemSelectedListener.onItemSelected(
                mConstrainLayoutDTHCompany,
                mDTHCompanyModelList[adapterPosition]
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DTHCompanyViewHolder {
        return DTHCompanyViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mDTHCompanyModelList.size
    }

    override fun onBindViewHolder(holder: DTHCompanyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(mDTHCompanyModelList[position].companyLogoUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemView.imageViewRVDTHCompany)
        holder.itemView.textViewCompanyName.text = mDTHCompanyModelList[position].companyName
    }
}