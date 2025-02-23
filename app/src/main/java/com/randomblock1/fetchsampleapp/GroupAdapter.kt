package com.randomblock1.fetchsampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.Int

class GroupAdapter(private val data: Map<Int, List<Item>>) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listIdTextView = view.findViewById<TextView>(R.id.listIdTextView)
        val innerRecyclerView = view.findViewById<RecyclerView>(R.id.innerRecyclerView)
        val expandArrowImageView = view.findViewById<ImageView>(R.id.expandCollapseIcon)

        var isExpanded = false

        init {

            view.setOnClickListener {
                expandArrowImageView.animate().rotationBy(180f)
                    .setInterpolator(LinearInterpolator()).setDuration(250).withLayer()
                innerRecyclerView.visibility = if (isExpanded) View.GONE else View.VISIBLE
                isExpanded = !isExpanded
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_group, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val groupNum = data.keys.elementAt(position)
        viewHolder.listIdTextView.text = String.format("Group %d", groupNum)

        val childItems = data.values.elementAt(position)

        val layoutManager = LinearLayoutManager(
            viewHolder.innerRecyclerView.context, LinearLayoutManager.VERTICAL, false
        )

        layoutManager.initialPrefetchItemCount = childItems.size

        val childItemAdapter = ChildAdapter(childItems)
        viewHolder.innerRecyclerView.setLayoutManager(layoutManager)
        viewHolder.innerRecyclerView.setAdapter(childItemAdapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}