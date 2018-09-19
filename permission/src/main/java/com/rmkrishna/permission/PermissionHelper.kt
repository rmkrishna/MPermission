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
import android.support.v4.content.ContextCompat

/**
 * Helper class for permission
 * <p>
 *      * To check the app has permissions(both single and multiple)
 * </p>
 */
class PermissionHelper {

    companion object {
        var instance = PermissionHelper()
    }

    /**
     * @param context
     * @param permissions
     * @return true -> has permission, false otherwise
     */
    fun hasPermissions(context: Context, permissions: MutableList<String>): Boolean {
        permissions.forEach {
            if (!hasPermission(context, it)) {
                return false
            }
        }
        return true
    }

    /**
     * @param context
     * @param permission
     * @return true -> has permission, false otherwise
     */
    fun hasPermission(context: Context, permission: String) =
        (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED)
}