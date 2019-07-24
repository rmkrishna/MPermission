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

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

private const val MFragment_TAG = "MFragment_TAG"

/**
 * To check and get the permission from FragmentActivity
 *
 * @param permissions - list of permissions
 * @param listener - @PermissionListener
 */
fun FragmentActivity.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    checkAndAskPermission(permissions.toList(), getPermissionListener(listener))
}

/**
 * To check whether the app has following permissions from FragmentActivity
 *
 * @return true -> If the app has all the permissions, false otherwise
 */
fun FragmentActivity.hasPermissions(vararg permissions: String): Boolean {
    val firstNotGrantedPermissions = permissions.firstOrNull { !hasPermission(this, it) }
    return firstNotGrantedPermissions.isNullOrEmpty()
}

/**
 * To check and get the permission from Fragment
 *
 * @param permissions - list of permissions
 * @param listener - @PermissionListener
 */
fun Fragment.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    activity?.checkAndAskPermission(permissions.filter { true }, getPermissionListener(listener))
}

/**
 * To check a single permission and not expecting any result back from Fragment
 *
 * @param permission - single permission
 */
infix fun Fragment.getPermission(permission: String) {
    activity?.checkAndAskPermission(arrayListOf(permission), null)
}

/**
 * To check a single permission and not expecting any result back from Activity
 *
 * @param permission - single permission
 */
infix fun FragmentActivity.getPermission(permission: String) {
    checkAndAskPermission(arrayListOf(permission), null)
}

private fun hasPermissions(context: Context, permissions: List<String>): Boolean {
    val firstNotGrantedPermissions = permissions.firstOrNull { !hasPermission(context, it) }
    return firstNotGrantedPermissions.isNullOrEmpty()
}

/**
 * @param context
 * @param permission
 *
 * @return true -> has permission, false otherwise
 */
private fun hasPermission(context: Context, permission: String) =
    (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)

/**
 * To check the permission from FragmentActivity
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
                .commitNowAllowingStateLoss()
        }
    }
}

/**
 *  To support AndroidX Java
 */
object MPermission {
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
