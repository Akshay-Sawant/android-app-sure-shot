package com.sureshotdiscount.app.ui.dth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sureshotdiscount.app.R
import kotlinx.android.synthetic.main.rv_dth.view.*

class DTHAdapter(
    private var mContext: Context,
    private var mItemLayout: Int,
    private var mDTHModelList: List<DTHModel>,
    private val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<DTHAdapter.DTHViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(mView: View, mPosition: DTHModel)
    }

    inner class DTHViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView),
        View.OnClickListener {
        private val mConstrainLayoutDTHCompany: ConstraintLayout =
            mItemView.findViewById(R.id.constrainLayoutDTHCompany)

        init {
            mConstrainLayoutDTHCompany.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemSelectedListener.onItemSelected(
                mConstrainLayoutDTHCompany,
                mDTHModelList[adapterPosition]
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DTHViewHolder {
        return DTHViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    mItemLayout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return mDTHModelList.size
    }

    override fun onBindViewHolder(holder: DTHViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(mDTHModelList[position].mCompanyLogoUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.itemView.circleImageViewDTHCompanyLogo)
        holder.itemView.textViewDTHCompanyName.text = mDTHModelList[position].mCompanyName
    }
}