/*
 *
 *  Copyright 2018 Muthukrishnan R
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.rmkrishna.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat

private const val MFragment_TAG = "MFragment_TAG"

/**
 * To check and get the permission from FragmentActivity
 */
fun FragmentActivity.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    checkAndAskPermission(permissions.filter { true }, getPermissionListener(listener))
}

/**
 * To check a single permission and not expecting any result from AppCompatActivity
 */
infix fun FragmentActivity.getPermission(permission: String) {
    checkAndAskPermission(arrayListOf(permission), null)
}

/**
 * To check a single permission and not expecting any result from Fragment
 */
infix fun Fragment.getPermission(permission: String) {
    activity?.checkAndAskPermission(arrayListOf(permission), null)
}

/**
 * To check and get the permission from Fragment
 */
fun Fragment.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    activity?.let {
        it.checkAndAskPermission(
            permissions.filter { true }, getPermissionListener(listener)
        )
    }
}

/**
 * To check and get the permission from FragmentActivity
 */
private fun FragmentActivity.checkAndAskPermission(
    permissions: List<String>,
    listener: MPermissionListener?
) {
    val notGrantedPermissions = permissions.filter { !hasPermission(this, it) }

    if (notGrantedPermissions.isEmpty()) listener?.granted() else {
        val mFragment = supportFragmentManager.findFragmentByTag(MFragment_TAG)

        if (mFragment == null) {
            var fragment =
                MFragment.newInstance(permissions = notGrantedPermissions as ArrayList<String>)
            fragment = fragment.setListener(listener)

            supportFragmentManager.beginTransaction().add(fragment, MFragment_TAG)
                .commitAllowingStateLoss()
        }
    }
}

/**
 * @param context
 * @param permission
 * @return true -> has permission, false otherwise
 */
private fun hasPermission(context: Context, permission: String) =
    (ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED)

private const val deprecatedMessage = "Instead of this try to use MHelper"

/**
 *
 */
object MPermission {
    @Deprecated(deprecatedMessage)
    const val CAMERA = MHelper.CAMERA

    @Deprecated(deprecatedMessage)
    const val READ_CALENDAR = MHelper.READ_CALENDAR

    @Deprecated(deprecatedMessage)
    const val WRITE_CALENDAR = MHelper.WRITE_CALENDAR

    @Deprecated(deprecatedMessage)
    const val READ_CONTACTS = MHelper.READ_CONTACTS

    @Deprecated(deprecatedMessage)
    const val WRITE_CONTACTS = MHelper.WRITE_CONTACTS

    @Deprecated(deprecatedMessage)
    const val GET_ACCOUNTS = MHelper.GET_ACCOUNTS

    @Deprecated(deprecatedMessage)
    const val ACCESS_FINE_LOCATION = MHelper.ACCESS_FINE_LOCATION

    @Deprecated(deprecatedMessage)
    const val ACCESS_COARSE_LOCATION = MHelper.ACCESS_COARSE_LOCATION

    @Deprecated(deprecatedMessage)
    const val RECORD_AUDIO = MHelper.RECORD_AUDIO

    @Deprecated(deprecatedMessage)
    const val READ_PHONE_STATE = MHelper.READ_PHONE_STATE

    @Deprecated(deprecatedMessage)
    const val CALL_PHONE = MHelper.CALL_PHONE

    @Deprecated(deprecatedMessage)
    const val PROCESS_OUTGOING_CALLS = MHelper.PROCESS_OUTGOING_CALLS

    @Deprecated(deprecatedMessage)
    const val ADD_VOICEMAIL = MHelper.ADD_VOICEMAIL

    @Deprecated(deprecatedMessage)
    const val SEND_SMS = MHelper.SEND_SMS

    @Deprecated(deprecatedMessage)
    const val RECEIVE_SMS = MHelper.RECEIVE_SMS

    @Deprecated(deprecatedMessage)
    const val READ_SMS = MHelper.READ_SMS

    @Deprecated(deprecatedMessage)
    const val READ_EXTERNAL_STORAGE = MHelper.READ_EXTERNAL_STORAGE

    @Deprecated(deprecatedMessage)
    const val WRITE_EXTERNAL_STORAGE = MHelper.WRITE_EXTERNAL_STORAGE

    @JvmStatic
    fun askPermissions(
        activity: FragmentActivity,
        vararg permissions: String,
        listener: MPermissionListener
    ) {
        activity.checkAndAskPermission(permissions.filter { true }, listener)
    }


    @JvmStatic
    fun get() = MHelper()
}

typealias MHelper = Manifest.permission

/**
 * Permission listener to get the state of the permission
 */
interface MPermissionListener {
    /**
     * All the given permissions are granted for the application
     */
    fun granted()

    /**
     * List of permissions are denied by the user for the application
     */
    fun denied(permissions: List<String>)

    /**
     * List of permissions are denied and enable never asked by the user for the application
     */
    fun neverAskAgain(permissions: List<String>)
}

fun getPermissionListener(listener: PermissionListener.() -> Unit) =
    PermissionListener().apply { listener() }

/**
 *
 */
open class PermissionListener : MPermissionListener {
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