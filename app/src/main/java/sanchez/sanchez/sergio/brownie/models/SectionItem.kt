package sanchez.sanchez.sergio.brownie.models

class SectionItem<T>(
    private val position: Int,
    private val element: T): Section<T> {
    override fun element(): T = element
    override fun type(): Int = SECTION_ITEM
    override fun sectionPosition(): Int = position
}