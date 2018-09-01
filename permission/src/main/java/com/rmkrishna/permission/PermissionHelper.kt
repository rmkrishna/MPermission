package com.rmkrishna.permission

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionHelper {

    companion object {
        var instance = PermissionHelper()
    }

    /**
     * @param context
     * @param permissions
     * @return
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
     * @return
     */
    fun hasPermission(context: Context, permission: String) =
        (ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED)
}