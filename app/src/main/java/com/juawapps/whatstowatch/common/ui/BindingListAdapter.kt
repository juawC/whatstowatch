package com.juawapps.whatstowatch.common.ui

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<DataType, BindingType : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<DataType>
) : ListAdapter<DataType, BindingListAdapter.ViewHolder<BindingType>>(
    AsyncDifferConfig.Builder<DataType>(diffCallback).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<BindingType> {
        val binding = createBinding(parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<BindingType>, position: Int) {}

    override fun onBindViewHolder(
        holder: ViewHolder<BindingType>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let {
            bind(holder.binding, it, position, payloads)
            holder.binding.executePendingBindings()
        }
    }

    protected abstract fun bind(
        binding: BindingType,
        item: DataType,
        position: Int,
        payloads: MutableList<Any>
    )

    protected abstract fun createBinding(parent: ViewGroup): BindingType

    class ViewHolder<out DataType : ViewDataBinding>(
        val binding: DataType
    ) : RecyclerView.ViewHolder(binding.root)
}