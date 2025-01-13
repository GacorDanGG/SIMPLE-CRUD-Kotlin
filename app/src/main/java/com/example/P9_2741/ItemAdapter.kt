package com.example.pertemuan9_12

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.P9_2741.MainActivity


class ItemAdapter(val context: Context, val items:
ArrayList<DataModelClass>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_row,parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val item = items.get(position)
        holder.tvNama.text = item.nama
        holder.tvNim.text = item.nim

        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(context,R.color.colorLightGray)
            )
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        holder.ivEdit.setOnClickListener { view ->

            if (context is MainActivity) {
                context.updateRecordDialog(item)
            }
        }

        holder.ivDelete.setOnClickListener { view ->

            if (context is MainActivity) {
                context.deleteRecordAlertDialog(item)
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val llMain = view.findViewById<LinearLayout>(R.id.llMain)
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvNim = view.findViewById<TextView>(R.id.tv_nim)
        val ivEdit = view.findViewById<ImageView>(R.id.iv_edit)
        val ivDelete = view.findViewById<ImageView>(R.id.iv_delete)
    }
}