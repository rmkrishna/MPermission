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

package com.rmkrishna.permissionX

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rmkrishna.permission.MPermissionListener
import com.rmkrishna.permission.PermissionListener
import com.rmkrishna.permission.getPermissionListener
import com.rmkrishna.permission.hasPermission

private const val MFragment_TAG = "MFragment_TAG"

/**
 * To check the permission from AppCompatActivity
 */
fun AppCompatActivity.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    checkAndAskPermission(permissions.filter { true },
        getPermissionListener(listener)
    )
}

/**
 * To check the permission from Fragment
 */
fun Fragment.askPermissions(
    vararg permissions: String,
    listener: PermissionListener.() -> Unit
) {
    activity!!.checkAndAskPermission(permissions.filter { true },
        getPermissionListener(listener)
    )
}

/**
 * To check the permission from FragmentActivity
 */
private fun FragmentActivity.checkAndAskPermission(
    permissions: List<String>,
    listener: MPermissionListener
) {

    val notGrantedPermissions = permissions.filter { !hasPermission(it) }

    if (notGrantedPermissions.isEmpty()) listener.granted() else {
        val mFragment = supportFragmentManager.findFragmentByTag(MFragment_TAG)

        if (mFragment == null) {
            var fragment =
                MFragment.newInstance(permissions = notGrantedPermissions as ArrayList<String>)

            fragment = fragment.setListener(listener)

            supportFragmentManager.beginTransaction().add(
                fragment,
                MFragment_TAG
            )
                .commitNowAllowingStateLoss()
        }
    }
}