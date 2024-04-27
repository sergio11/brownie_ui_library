package com.dreamsoftware.brownie.utils

interface IBrownieMapper<IN, OUT> {
    fun mapInToOut(input: IN): OUT
    fun mapInListToOutList(input: Iterable<IN>): Iterable<OUT>
    fun mapOutToIn(input: OUT): IN
    fun mapOutListToInList(input: Iterable<OUT>): Iterable<IN>
}