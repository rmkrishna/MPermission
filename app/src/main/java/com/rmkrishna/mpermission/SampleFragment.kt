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

package com.rmkrishna.mpermission


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rmkrishna.permission.MHelper
import com.rmkrishna.permission.askPermissions
import kotlinx.android.synthetic.main.activity_main.*


class SampleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askMultiplePermission.setOnClickListener {
            askPermissions(
                MHelper.WRITE_EXTERNAL_STORAGE,
                MHelper.READ_EXTERNAL_STORAGE,
                MHelper.CAMERA
            ) {
                granted {
                    Toast.makeText(
                        activity!!,
                        "Permission granted Success!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                denied {
                    Toast.makeText(
                        activity!!,
                        "Permission Denied by user",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                neverAskAgain {
                    Toast.makeText(
                        activity!!,
                        "Don't ask this permission, Pleaseeee",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
