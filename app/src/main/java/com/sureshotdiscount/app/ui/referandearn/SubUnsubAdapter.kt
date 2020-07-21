package com.sureshotdiscount.app.ui.referandearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sureshotdiscount.app.R
import com.sureshotdiscount.app.data.model.SubUnsubModel


class SubUnsubAdapter(private var mList: List<SubUnsubModel>)
    : RecyclerView.Adapter<SubUnsubAdapter.ViewHolder>() {

    /*interface OnItemSelectedListener {
        fun onItemSelected(bestCampSites: BestCampSites, adapterPosition: Int)
    }*/
    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val textViewPhoneNumber : TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        val textViewSubStatus : TextView = itemView.findViewById(R.id.textViewSubStatus)


        init {
            //itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            //onItemSelectedListener.onItemSelected(mList[adapterPosition], adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_subunsub_numbers, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewPhoneNumber.text = mList[position].number
        holder.textViewSubStatus.text = mList[position].status
    }

    override fun getItemCount(): Int = mList.size
}