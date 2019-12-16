package sanchez.sanchez.sergio.brownie.extension

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import sanchez.sanchez.sergio.brownie.ui.core.activity.SupportActivity
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.ConfirmationDialogFragment
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.NoticeDialogFragment
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.ProgressDialogFragment

fun SupportActivity.showNoticeDialog(title: String, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    NoticeDialogFragment.showDialog(this, title, isSuccess,  noticeDialogListener)
}

fun Fragment.showNoticeDialog(title: String, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    (activity as SupportActivity).showNoticeDialog(title, isSuccess, noticeDialogListener)
}

fun SupportActivity.showNoticeDialog(@StringRes stringResId: Int, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    showNoticeDialog(getString(stringResId), isSuccess, noticeDialogListener)
}

fun Fragment.showNoticeDialog(@StringRes stringResId: Int, isSuccess: Boolean = true, noticeDialogListener: NoticeDialogFragment.NoticeDialogListener? = null) {
    (activity as SupportActivity).showNoticeDialog(getString(stringResId), isSuccess, noticeDialogListener)
}

fun SupportActivity.showProgressDialog(title: String) {
    ProgressDialogFragment.showDialog(this, title)
}

fun Fragment.showProgressDialog(title: String) {
    (activity as SupportActivity).showProgressDialog(title)
}

fun SupportActivity.showProgressDialog(@StringRes stringResId: Int) {
    showProgressDialog(getString(stringResId))
}

fun Fragment.showProgressDialog(@StringRes stringResId: Int) {
    (activity as SupportActivity).showProgressDialog(stringResId)
}

fun SupportActivity.hideProgressDialog() {
    ProgressDialogFragment.cancelCurrent()
}

fun Fragment.hideProgressDialog() {
    (activity as SupportActivity).hideProgressDialog()
}

fun SupportActivity.showConfirmationDialog(title: String, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    ConfirmationDialogFragment.showDialog(this, title, confirmationDialogListener)
}

fun Fragment.showConfirmationDialog(title: String, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    (activity as SupportActivity).showConfirmationDialog(title, confirmationDialogListener)
}

fun SupportActivity.showConfirmationDialog(@StringRes stringResId: Int, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    showConfirmationDialog(getString(stringResId), confirmationDialogListener)
}

fun Fragment.showConfirmationDialog(@StringRes stringResId: Int, confirmationDialogListener: ConfirmationDialogFragment.ConfirmationDialogListener? = null) {
    (activity as SupportActivity).showConfirmationDialog(stringResId, confirmationDialogListener)
}



