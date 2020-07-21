package com.sureshotdiscount.app.ui.referandearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.model.ReferralsModel
import com.sureshotdiscount.app.data.model.SubUnsubModel


class ReferralsListAdapter(
                           private var mList : List<ReferralsModel>)
    : RecyclerView.Adapter<ReferralsListAdapter.ViewHolder>() {
    var mExpandedPosition = -1
    var previousExpandedPosition = -1
    private lateinit var mRecyclerViewSubUnSub: RecyclerView
    private lateinit var mSubUnSubAdapter: SubUnsubAdapter
    private var mSubUnSubModelList: ArrayList<SubUnsubModel> = ArrayList()
    /*interface OnItemSelectedListener {
        fun onItemSelected(bestCampSites: BestCampSites, adapterPosition: Int)
    }*/
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textViewLevel : TextView = itemView.findViewById(R.id.textViewLevel)
        val textViewAmount : TextView = itemView.findViewById(R.id.textViewAmount)
        val layoutSubUnsub : ConstraintLayout = itemView.findViewById(R.id.layoutSubUnsub)
        val recyclerViewList : RecyclerView = itemView.findViewById(R.id.recyclerViewPhoneNumbers)


        init {
            //itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            //onItemSelectedListener.onItemSelected(mList[adapterPosition], adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_referrals_list, parent, false)
        mRecyclerViewSubUnSub = itemView.findViewById(R.id.recyclerViewPhoneNumbers)
        mSubUnSubModelList.clear()
        mSubUnSubAdapter = this.let {
            SubUnsubAdapter(mSubUnSubModelList)
        }
        mRecyclerViewSubUnSub.adapter = mSubUnSubAdapter
        mSubUnSubAdapter.notifyDataSetChanged()
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "9876543210",
                "Unsubscribed"
            )
        )
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "9638527410",
                "Subscribed"
            )
        )
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "7894561230",
                "Unsubscribed"
            )
        )
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "9412563879",
                "Subscribed"
            )
        )
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "9978844552",
                "Subscribed"
            )
        )
        mSubUnSubModelList.add(
            SubUnsubModel(
                "1",
                "8289848789",
                "Unsubscribed"
            )
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewLevel.text = mList[position].level
        holder.textViewAmount.text = mList[position].amount
        val isExpanded = position == mExpandedPosition
        if(position == 0){
            holder.recyclerViewList.visibility = View.VISIBLE
            holder.recyclerViewList.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        }
        else{
            holder.recyclerViewList.visibility = View.GONE
        }
        holder.layoutSubUnsub.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.itemView.isActivated = isExpanded
        if (isExpanded) previousExpandedPosition = position
        holder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = mList.size
}