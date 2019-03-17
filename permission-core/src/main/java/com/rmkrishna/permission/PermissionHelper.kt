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

typealias MHelper = Manifest.permission

//val CALENDAR = arrayOf(MHelper.READ_CALENDAR, MHelper.WRITE_CALENDAR)
//val CONTACTS = arrayOf(MHelper.READ_CONTACTS, MHelper.WRITE_CONTACTS, MHelper.GET_ACCOUNTS)
//val LOCATION = arrayOf(MHelper.ACCESS_FINE_LOCATION, MHelper.ACCESS_COARSE_LOCATION)
//val STORAGE = arrayOf(MHelper.READ_EXTERNAL_STORAGE, MHelper.WRITE_EXTERNAL_STORAGE)

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


