package com.rmkrishna.mpermission

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.rmkrishna.permission.MPermission
import com.rmkrishna.permission.askPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        askContactPermission.setOnClickListener {
            askPermissions(MPermission.WRITE_CONTACTS) {
                granted {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted Success!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                denied {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission Denied by user",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                neverAskAgain {
                    Toast.makeText(
                        this@MainActivity,
                        "Don't ask this permission, Pleaseeee",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        askMultiplePermission.setOnClickListener {
            askPermissions(
                MPermission.WRITE_EXTERNAL_STORAGE,
                MPermission.READ_EXTERNAL_STORAGE,
                MPermission.CAMERA
            ) {
                granted {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted Success!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                denied {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission Denied by user",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                neverAskAgain {
                    Toast.makeText(
                        this@MainActivity,
                        "Don't ask this permission, Pleaseeee",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        openJavaActivity.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    MainJavaActivity::class.java
                )
            )
        }
    }
}
