package com.juawapps.whatstowatch.common.ui

import androidx.databinding.ViewDataBinding

class DefaultListAdapter<DataType, ItemIdentifierType, BindingType : ViewDataBinding>(
    layoutResId: Int,
    private val bindView: BindingType.(DataType) -> Unit,
    itemIdentifier: DataType.() -> ItemIdentifierType,
    haveContentsChanged: DataType.(DataType) -> Boolean
) : BindingListAdapter<DataType, BindingType>(
    layoutResId,
    DefaultDiffCallback(itemIdentifier, haveContentsChanged)
) {
    override fun BindingType.bind(element: DataType, payloads: MutableList<Any>) = bindView(element)
}
