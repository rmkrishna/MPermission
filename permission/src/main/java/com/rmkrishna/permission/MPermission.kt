package com.rmkrishna.permission

import android.Manifest
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

private const val MFragment_TAG = "MFragment_TAG"

fun AppCompatActivity.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    checkAndAskPermission(permissions.filter { true }, listener)
}

fun Fragment.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    activity!!.checkAndAskPermission(permissions.filter { true }, listener)
}

private fun FragmentActivity.checkAndAskPermission(
    permissions: List<String>,
    listener: PermissionListener.() -> Unit
) {

    val permissionListener = getPermissionListener(listener)

    val notGrantedPermissions = permissions.filter { hasPermission(it) }

    if (notGrantedPermissions.isEmpty()) permissionListener.granted() else {
        val mFragment = supportFragmentManager.findFragmentByTag(MFragment_TAG)

        if (mFragment == null) {
            var fragment =
                MFragment.newInstance(permissions = notGrantedPermissions as ArrayList<String>)
            fragment = fragment.setListener(permissionListener)

            supportFragmentManager.beginTransaction().add(fragment, MFragment_TAG)
                .commitNowAllowingStateLoss()
        }
    }
}


fun android.app.Activity.hasPermission(permission: String) =
    PermissionHelper.instance.hasPermission(this, permission)

fun android.app.Activity.hasPermissions(permissions: MutableList<String>) =
    PermissionHelper.instance.hasPermissions(this, permissions)

interface MPermissionListener {

    fun granted()

    fun denied(permissions: List<String>)

    fun neverAskAgain(permissions: List<String>)
}

fun getPermissionListener(listener: PermissionListener.() -> Unit) =
    PermissionListener().apply { listener() }

/**
 */
class PermissionListener : MPermissionListener {

    private var mGranted: () -> Unit = {}
    private var mDenied: (permissions: List<String>) -> Unit = {}
    private var mNeverAskAgain: (permissions: List<String>) -> Unit = {}

    fun granted(func: () -> Unit) {
        mGranted = func
    }

    fun denied(func: (permissions: List<String>) -> Unit) {
        mDenied = func
    }

    fun neverAskAgain(func: (permissions: List<String>) -> Unit) {
        mNeverAskAgain = func
    }

    override fun granted() {
        mGranted.invoke()
    }

    override fun denied(permissions: List<String>) {
        mDenied.invoke(permissions)
    }

    override fun neverAskAgain(permissions: List<String>) {
        mNeverAskAgain.invoke(permissions)
    }

}

/**
 *
 */
object MPermission {
    const val CAMERA = Manifest.permission.CAMERA

    const val READ_CALENDAR = Manifest.permission.READ_CALENDAR
    const val WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR

    const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    const val WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS

    const val GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS

    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO

    const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

    const val CALL_PHONE = Manifest.permission.CALL_PHONE
    const val PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS

    const val ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL

    const val SEND_SMS = Manifest.permission.SEND_SMS
    const val RECEIVE_SMS = Manifest.permission.RECEIVE_SMS
    const val READ_SMS = Manifest.permission.READ_SMS

    const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
}