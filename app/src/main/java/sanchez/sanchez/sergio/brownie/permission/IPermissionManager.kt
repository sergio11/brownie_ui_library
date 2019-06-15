package sanchez.sanchez.sergio.brownie.permission

interface IPermissionManager {

    fun checkSinglePermission(permission: String, reasonText: String)

    fun shouldAskPermission(permission: String): Boolean

    fun setCheckPermissionListener(checkPermissionListener: OnCheckPermissionListener)

    interface OnCheckPermissionListener {
        fun onSinglePermissionGranted(permission: String)
        fun onSinglePermissionRejected(permission: String)
        fun onErrorOccurred(permission: String)
    }
}