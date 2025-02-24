package com.randomblock1.fetchsampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

/**
 * A list of items sorted by name
 */
class ChildAdapter(private val data: List<Item>) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNameTextView = view.findViewById<TextView>(R.id.itemNameTextView)
        val itemIdTextView = view.findViewById<TextView>(R.id.itemIdTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemNameTextView.text = data[position].name
        viewHolder.itemIdTextView.text = String.format(Locale.getDefault(), "%d", data[position].id)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}