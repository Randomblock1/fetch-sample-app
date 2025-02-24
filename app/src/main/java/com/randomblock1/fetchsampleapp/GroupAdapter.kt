package com.randomblock1.fetchsampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale
import kotlin.Int

/**
 * A list of groups with nested RecyclerViews for their items
 */
class GroupAdapter(private val data: Map<Int, List<Item>>) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listIdTextView = view.findViewById<TextView>(R.id.listIdTextView)
        val innerRecyclerView = view.findViewById<RecyclerView>(R.id.innerRecyclerView)
        val expandArrowImageView = view.findViewById<ImageView>(R.id.expandCollapseIcon)

        val expandAnimation = RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val collapseAnimation = RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

        var isExpanded = false

        init {
            expandAnimation.duration = 250
            expandAnimation.interpolator = LinearInterpolator()
            expandAnimation.fillAfter = true
            collapseAnimation.duration = 250
            collapseAnimation.interpolator = LinearInterpolator()
            collapseAnimation.fillAfter = true

            view.setOnClickListener {
                if (isExpanded) {
                    expandArrowImageView.startAnimation(collapseAnimation)
                    innerRecyclerView.visibility = View.GONE
                } else {
                    expandArrowImageView.startAnimation(expandAnimation)
                    innerRecyclerView.visibility = View.VISIBLE
                }
                isExpanded = !isExpanded
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_group, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val groupNum = data.keys.elementAt(position)
        viewHolder.listIdTextView.text = String.format(Locale.getDefault(), "List %d", groupNum)

        val childItems = data.values.elementAt(position)

        val layoutManager = LinearLayoutManager(
            viewHolder.innerRecyclerView.context, LinearLayoutManager.VERTICAL, false
        )
        layoutManager.initialPrefetchItemCount = childItems.size
        viewHolder.innerRecyclerView.setLayoutManager(layoutManager)

        val childItemAdapter = ChildAdapter(childItems)
        viewHolder.innerRecyclerView.setAdapter(childItemAdapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}