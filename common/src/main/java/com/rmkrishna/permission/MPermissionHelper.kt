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

object MHelper {
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