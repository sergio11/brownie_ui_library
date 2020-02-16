package sanchez.sanchez.sergio.brownie.models

class SectionHeader<T>(
    private val position: Int,
    private val element: T,
    var groupCount: Int = 0,
    var groupTitle: String? = null,
    var lastElement: T? = null): Section<T> {
    override fun element(): T = element
    override fun type(): Int = SECTION_HEADER
    override fun sectionPosition(): Int = position
}