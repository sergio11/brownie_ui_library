package sanchez.sanchez.sergio.brownie.ui.core.viewmodel

import androidx.annotation.CallSuper
import sanchez.sanchez.sergio.brownie.models.Section
import sanchez.sanchez.sergio.brownie.models.SectionHeader
import sanchez.sanchez.sergio.brownie.models.SectionItem


abstract class SupportGroupedLCEViewModel<T, E>: SupportLCEViewModel<Section<T>, E>() {

    @CallSuper
    override suspend fun onLoadData(): List<Section<T>> =
        onCreateSections(onCreateDataSet() as MutableList<T>)

    @CallSuper
    override suspend fun onLoadData(params: E): List<Section<T>> =
        onCreateSections(onCreateDataSet(params) as MutableList<T>)

    /**
     * On Compare Elements
     */
    abstract fun onCompareElements(elementOne: T, elementTwo: T): Int

    /**
     * On Check If Next Element is in same group
     * @param previousElement
     * @param nextElement
     */
    abstract fun onCheckIfNextElementIsInSameGroup(previousElement: T, nextElement: T): Boolean

    /**
     * On Update Title For Group
     * @param firstElement
     * @param lastElement
     */
    open fun onUpdateTitleForGroup(firstElement: T, lastElement: T): String = ""

    /**
     * On Create Data Set
     */
    abstract fun onCreateDataSet(params: E? = null): List<T>


    /**
     * Private Methods
     */

    /**
     * On Create Sections
     * @param data
     */
    private fun onCreateSections(data: MutableList<T>): List<Section<T>> {

        data.sortWith(Comparator { elementOne, elementTwo ->
            onCompareElements(elementOne, elementTwo) })

        val sectionList = ArrayList<Section<T>>()


        var currentSectionHeaderGroup: SectionHeader<T>? = null

        for (i in 0 until data.size) {

            if(i == 0 || !onCheckIfNextElementIsInSameGroup(
                    previousElement = data[i - 1],
                    nextElement = data[i]
                ))
                currentSectionHeaderGroup = SectionHeader(i, data[i]).also {
                    sectionList.add(it)
                }

            sectionList.add(SectionItem(i, data[i]))
            currentSectionHeaderGroup?.apply {
                groupCount += 1
                lastElement = data[i]
                groupTitle = onUpdateTitleForGroup(
                    element(),
                    data[i]
                )
            }


        }

        return sectionList
    }
}