package com.puneet.codeexamples.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puneet.codeexamples.R
import com.puneet.codeexamples.databinding.LayoutItemIndicatorBinding
import kotlin.properties.Delegates

class ViewPagerIndicatorAdapter :
    RecyclerView.Adapter<ViewPagerIndicatorAdapter.ViewPagerIndicatorViewHolder>() {
    private val list: ArrayList<String> = ArrayList()
    private var listener: OnClickListener? = null
    private var checkedIndex by Delegates.observable(0) { _, oldPos, newPos ->
        if (newPos in list.indices) {
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerIndicatorViewHolder {
        return ViewPagerIndicatorViewHolder(
            LayoutItemIndicatorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerIndicatorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    fun submitData(newData: List<String>) {
        list.clear()
        list.addAll(newData)
        notifyItemRangeChanged(0, list.size)
    }

    fun setSelectedItem(position: Int) {
        checkedIndex = position
        if (position >= 1)
            notifyItemRangeChanged(position - 1, 3)
        else
            notifyItemRangeChanged(position, 2)
    }

    inner class ViewPagerIndicatorViewHolder(
        private val binding: LayoutItemIndicatorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            when (checkedIndex) {
                -1 -> {
                    binding.root.strokeColor =
                        binding.root.context.getColor(R.color.colorStrokeSliderUnselected)
                }
                adapterPosition -> {
                    binding.root.strokeColor =
                        binding.root.context.getColor(R.color.colorStrokeSliderSelected)
                }
                else -> {
                    binding.root.strokeColor =
                        binding.root.context.getColor(R.color.colorStrokeSliderUnselected)
                }
            }
            Glide.with(binding.root)
                .load(url)
                .into(binding.imageView)
            binding.root.setOnClickListener {
                listener?.onClick(adapterPosition)
                checkedIndex = adapterPosition
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}