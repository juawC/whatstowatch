package com.juawapps.whatstowatch.common.data

import javax.inject.Inject

class ListMapper<I, O> @Inject constructor(
    private val mapper: Mapper<I, O>
) : Mapper<List<I>, List<O>> {
    override fun map(input: List<I>): List<O> = input.map(mapper::map)
}
