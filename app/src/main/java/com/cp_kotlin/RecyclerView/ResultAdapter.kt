package com.cp_kotlin.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.R
import java.util.*

class ResultAdapter(
    var rows: ArrayList<String>
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    interface ResultAdapterListener {
        fun onResultClicked(cardView: View, result: Result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.result_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultRow.setText(rows.get(position))

    }

    override fun getItemCount(): Int {
        return rows.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var resultRow: TextView
        var myLayout: LinearLayout

        init {
            resultRow = super.itemView.findViewById(R.id.resultName)
            myLayout = super.itemView.findViewById(R.id.resultLayout)

            itemView.setOnClickListener{ view ->
                view.findNavController().navigate(R.id.action_Fragment2_to_ResultFragment)
            }
        }
    }

}