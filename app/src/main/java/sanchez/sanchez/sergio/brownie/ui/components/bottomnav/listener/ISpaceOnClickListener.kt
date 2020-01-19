package sanchez.sanchez.sergio.brownie.ui.components.bottomnav.listener


interface ISpaceOnClickListener {
    fun onCentreButtonClick()
    fun onItemClick(itemIndex: Int, itemName: String)
    fun onItemReselected(itemIndex: Int, itemName: String)
}