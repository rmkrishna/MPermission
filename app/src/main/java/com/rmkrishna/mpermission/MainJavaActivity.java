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

package com.rmkrishna.mpermission;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.rmkrishna.permission.MPermission;
import com.rmkrishna.permission.MPermissionListener;
import com.rmkrishna.permission.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        findViewById(R.id.askContactPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MPermission.askPermissions(MainJavaActivity.this, new String[]{MPermission.WRITE_CONTACTS}, new MPermissionListener() {

                    @Override
                    public void neverAskAgain(@NotNull List<String> permissions) {
                        Toast.makeText(
                                MainJavaActivity.this,
                                "Permission granted Success!!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void denied(@NotNull List<String> permissions) {
                        Toast.makeText(
                                MainJavaActivity.this,
                                "Permission Denied by user",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    @Override
                    public void granted() {
                        Toast.makeText(
                                MainJavaActivity.this,
                                "Don't ask this permission, Pleaseeee",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
            }
        });

    }

}
