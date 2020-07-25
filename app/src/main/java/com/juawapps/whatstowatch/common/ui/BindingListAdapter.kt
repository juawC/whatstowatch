package com.juawapps.whatstowatch.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<DataType, BindingType : ViewDataBinding>(
    private val layoutResId: Int,
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
            holder.binding.bind(it, payloads)
            holder.binding.executePendingBindings()
        }
    }

    protected abstract fun BindingType.bind(element: DataType, payloads: MutableList<Any>)

    private fun createBinding(parent: ViewGroup): BindingType {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(
            layoutInflater,
            layoutResId,
            parent,
            false
        )
    }

    class ViewHolder<out DataType : ViewDataBinding>(
        val binding: DataType
    ) : RecyclerView.ViewHolder(binding.root)

    class DefaultDiffCallback<DataType, ItemIdentifierType> (
        private val itemIdentifier: DataType.() -> ItemIdentifierType,
        private val haveContentsChanged: DataType.(DataType) -> Boolean
    ): DiffUtil.ItemCallback<DataType>() {
        override fun areItemsTheSame(
            oldItem: DataType,
            newItem: DataType
        ): Boolean = oldItem.itemIdentifier() == newItem.itemIdentifier()

        override fun areContentsTheSame(
            oldItem: DataType,
            newItem: DataType
        ) = !oldItem.haveContentsChanged(newItem)
    }
}