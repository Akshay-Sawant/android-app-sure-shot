package com.sureshots.app.ui.mobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshots.app.R
import com.sureshots.app.data.model.SimCompanyModel
import kotlinx.android.synthetic.main.recycler_view_sim_company.view.*

class SimCompanyAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mSimCompanyModelList: List<SimCompanyModel>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<SimCompanyAdapter.SimCompanyViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(mPosition: SimCompanyModel)
    }

    inner class SimCompanyViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        private var mConstraintRvSimCompany: ConstraintLayout =
            mItemView.findViewById(R.id.constraintRvSimCompany)

        init {
            mConstraintRvSimCompany.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemSelectedListener.onItemSelected(mSimCompanyModelList[adapterPosition])
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
            .into(holder.itemView.imageViewRVSimCompany)
        holder.itemView.textViewCompanyName.text = mSimCompanyModelList[position].companyName
    }
}