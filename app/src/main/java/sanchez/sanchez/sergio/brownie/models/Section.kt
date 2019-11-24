package sanchez.sanchez.sergio.brownie.models

const val SECTION_HEADER = 0
const val SECTION_ITEM = 1
const val SECTION_CUSTOM_HEADER = 2

interface Section<T> {
    fun type(): Int
    fun sectionPosition(): Int
    fun element(): T
}