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
        it.checkAndAskPermission(permissions.filter { true }, getPermissionListener(listener))
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
    (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)

object MPermission {
    @JvmStatic
    fun askPermissions(
        activity: FragmentActivity, vararg permissions: String, listener: MPermissionListener
    ) {
        activity.checkAndAskPermission(permissions.filter { true }, listener)
    }

    @JvmStatic
    fun get() = MHelper()
}
