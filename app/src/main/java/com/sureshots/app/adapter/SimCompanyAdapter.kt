package com.sureshots.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshots.app.R
import com.sureshots.app.model.SimCompanyModel
import kotlinx.android.synthetic.main.recycler_view_sim_company.view.*

class SimCompanyAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mSimCompanyModelList: List<SimCompanyModel>,private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<SimCompanyAdapter.SimCompanyViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(adapterPosition: Int)
    }

    inner class SimCompanyViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),View.OnClickListener {

        init {
            mItemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemSelectedListener.onItemSelected(adapterPosition)
            /*view?.let {

                Navigation.findNavController(it)
                    .navigate(R.id.action_dashboard_to_rechargeOneFragment)
            }*/
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimCompanyViewHolder {
        return SimCompanyViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mSimCompanyModelList.size
    }

    override fun onBindViewHolder(holder: SimCompanyViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(mSimCompanyModelList[position].companyLogoUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemView.imageViewRVSimComany)
        holder.itemView.textViewCompanyName.text = mSimCompanyModelList[position].companyName
    }
}