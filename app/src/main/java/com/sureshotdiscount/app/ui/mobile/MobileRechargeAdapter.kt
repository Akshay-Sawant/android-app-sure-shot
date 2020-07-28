package com.sureshotdiscount.app.ui.mobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshotdiscount.app.R
import kotlinx.android.synthetic.main.rv_mobile_recharge.view.*

class MobileRechargeAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mMobileRechargeListModelList: List<MobileRechargeListModel>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<MobileRechargeAdapter.MobileRechargeViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(mPosition: MobileRechargeListModel)
    }

    inner class MobileRechargeViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        private var mConstraintRvSimCompany: ConstraintLayout =
            mItemView.findViewById(R.id.constraintRvSimCompany)

        init {
            mConstraintRvSimCompany.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemSelectedListener.onItemSelected(mMobileRechargeListModelList[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileRechargeViewHolder {
        return MobileRechargeViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mMobileRechargeListModelList.size
    }

    override fun onBindViewHolder(holder: MobileRechargeViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(mMobileRechargeListModelList[position].mCompanyLogoUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemView.appCompatImageViewMobileRechargeCompanyLogo)
        holder.itemView.textViewMobileRechargeCompanyName.text =
            mMobileRechargeListModelList[position].mCompanyName
    }
}