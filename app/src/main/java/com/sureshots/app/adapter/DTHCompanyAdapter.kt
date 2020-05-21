package com.sureshots.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshots.app.R
import com.sureshots.app.model.DTHCompanyModel
import kotlinx.android.synthetic.main.recycler_view_dth_company.view.*


class DTHCompanyAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mDTHCompanyModelList: List<DTHCompanyModel>
) : RecyclerView.Adapter<DTHCompanyAdapter.DTHCompanyViewHolder>() {

    inner class DTHCompanyViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_rechargeOneFragment)
            }
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