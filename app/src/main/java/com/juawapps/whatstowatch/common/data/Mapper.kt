package com.juawapps.whatstowatch.common.data

interface Mapper<I, O> {
    fun map(input: I): O
}
