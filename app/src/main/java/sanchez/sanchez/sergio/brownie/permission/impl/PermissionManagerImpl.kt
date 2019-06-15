package sanchez.sanchez.sergio.brownie.permission.impl

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.fernandocejas.arrow.checks.Preconditions
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import sanchez.sanchez.sergio.brownie.permission.IPermissionManager
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.NoticeDialogFragment
import java.util.*


class PermissionManagerImpl constructor(
    private val activity: AppCompatActivity): IPermissionManager {


    private var checkPermissionListener: IPermissionManager.OnCheckPermissionListener? = null


    override fun checkSinglePermission(permission: String, reasonText: String) {
        Preconditions.checkNotNull(permission, "Permission can not be null")
        Preconditions.checkNotNull(permission.isNotEmpty(), "Permission can not be empty")
        Preconditions.checkNotNull(reasonText, "Reason can not be null")

        if (shouldAskPermission(permission)) {

            val permissionListener = buildPermissionListener(permission, reasonText)

            Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(permissionListener)
                .check()
        } else {
            checkPermissionListener?.onErrorOccurred(permission)
        }
    }

    override fun shouldAskPermission(permission: String): Boolean {
        if (shouldAskPermission() && appManifestContainsPermission(permission)) {
            val permissionResult = ActivityCompat.checkSelfPermission(activity, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    override fun setCheckPermissionListener(checkPermissionListener: IPermissionManager.OnCheckPermissionListener) {
        this.checkPermissionListener = checkPermissionListener
    }


    /** PRIVATE METHOD **/

    private fun shouldAskPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun appManifestContainsPermission(permission: String): Boolean {

        val pm = activity.packageManager

        try {

            val packageInfo = pm.getPackageInfo(activity.packageName,
                PackageManager.GET_PERMISSIONS)

            var requestedPermissions: Array<String>? = null
            if (packageInfo != null) {
                requestedPermissions = packageInfo.requestedPermissions
            }
            if (requestedPermissions == null) {
                return false
            }
            if (requestedPermissions.isNotEmpty()) {
                val requestedPermissionsList = Arrays.asList(*requestedPermissions)
                return requestedPermissionsList.contains(permission)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return false
    }


    private fun buildPermissionListener(permission: String, reasonText: String): PermissionListener {

        return object : BasePermissionListener() {

            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                super.onPermissionGranted(response)
                checkPermissionListener?.onSinglePermissionGranted(permission)
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                super.onPermissionDenied(response)
                NoticeDialogFragment.showDialog(activity,
                    reasonText, false, object : NoticeDialogFragment.NoticeDialogListener {
                        override fun onAccepted(dialog: DialogFragment) {
                            checkPermissionListener?.onSinglePermissionRejected(permission)

                        }
                    })

            }
        }
    }
}