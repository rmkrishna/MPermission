package com.rmkrishna.permission

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


private const val ARG_PERMISSIONS = "permissions"
private const val PERMISSION_REQUEST_CODE = 4883

class MFragment : Fragment() {

    init {
        retainInstance = true
    }

    private var permissions: ArrayList<String> = arrayListOf()

    private lateinit var listener: PermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            bundle.getStringArrayList(ARG_PERMISSIONS)?.let {
                permissions = it
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_m, container, false)
    }

    fun setListener(@Nullable listener: PermissionListener): MFragment {

        this.listener = listener

        return this
    }

    override fun onResume() {
        super.onResume()

        if (permissions.size > 0) {

            val permissionArray = arrayOfNulls<String>(permissions.size)
            permissions.toArray(permissionArray)

            requestPermissions(permissionArray, PERMISSION_REQUEST_CODE)

            return
        }

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

            if (grantedAllPermissions) {
                listener?.granted()
            } else {
                if (deniedPermissionList.isNotEmpty()) {
                    listener?.denied(deniedPermissionList)
                }
                if (neverAskAgainPermissionList.isNotEmpty()) {
                    listener?.neverAskAgain(neverAskAgainPermissionList)
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


