package com.dreamsoftware.brownie.utils

interface IBrownieOneSideMapper<IN, OUT> {
    fun mapInToOut(input: IN): OUT
    fun mapInListToOutList(input: Iterable<IN>): Iterable<OUT>
}