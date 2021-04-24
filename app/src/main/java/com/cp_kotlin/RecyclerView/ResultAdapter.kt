package com.cp_kotlin.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.Fragment4.Companion.intentFactory
import com.cp_kotlin.R
import java.util.*

class ResultAdapter(
    var context: Context,
    var rows: ArrayList<String>
) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.result_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.resultRow.text = rows[position]
        holder.myLayout.setOnClickListener {
            val intent = intentFactory(context)
            intent.putExtra("USERNAME", rows[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var resultRow: TextView
        var myLayout: ConstraintLayout

        init {
            resultRow = itemView.findViewById(R.id.row_recy)
            myLayout = itemView.findViewById(R.id.recycle)
        }
    }

}