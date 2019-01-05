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

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

private const val ARG_PERMISSIONS = "permissions"
private const val PERMISSION_REQUEST_CODE = 4883

/***
 * Transparent Fragment help to get the permission and send back the result back to UI using callbacks
 */
internal class MFragment : Fragment() {
    private val permissions: ArrayList<String> = arrayListOf()
    private lateinit var listener: MPermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getStringArrayList(ARG_PERMISSIONS)?.let {
                permissions.addAll(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_m, container, false)
    }

    /**
     * To set the {@link MPermissionListener} to the fragment
     */
    fun setListener(@Nullable listener: MPermissionListener): MFragment {
        this.listener = listener
        return this
    }

    override fun onResume() {
        super.onResume()
        // Check for permissions and request the permissions
        if (permissions.size > 0) {
            // Convert to array and send that to requestPermissions
            val permissionArray = arrayOfNulls<String>(permissions.size)
            permissions.toArray(permissionArray)

            requestPermissions(permissionArray, PERMISSION_REQUEST_CODE)
            return
        }
        // If there is no permission to get, just remove the fragment
        fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var grantedAllPermissions = true
            val neverAskAgainPermissionList = mutableListOf<String>()
            val deniedPermissionList = arrayListOf<String>()

            permissions.forEachIndexed { index, permission ->
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    // Some permissions are not granted
                    grantedAllPermissions = false
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            permission
                        )
                    ) {
                        neverAskAgainPermissionList.add(permission)
                    } else {
                        deniedPermissionList.add(permission)
                    }
                }
            }

            if (grantedAllPermissions) { //All permissions are granted
                listener.granted()
            } else {
                if (deniedPermissionList.isNotEmpty()) {
                    listener.denied(deniedPermissionList)
                }
                if (neverAskAgainPermissionList.isNotEmpty()) {
                    listener.neverAskAgain(neverAskAgainPermissionList)
                }
            }

            fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(permissions: ArrayList<String>) =
            MFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PERMISSIONS, permissions)
                }
            }
    }
}


