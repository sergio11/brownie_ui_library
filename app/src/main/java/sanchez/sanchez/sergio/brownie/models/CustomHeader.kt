package sanchez.sanchez.sergio.brownie.models

import androidx.recyclerview.widget.RecyclerView

class CustomHeader<T>(private val element: T): Section<T> {
    override fun element(): T = element
    override fun type(): Int = SECTION_CUSTOM_HEADER
    override fun sectionPosition(): Int = RecyclerView.NO_POSITION
}