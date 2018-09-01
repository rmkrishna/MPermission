# MPermission
Easy way to handle permissions in Android, Which support both Kotlin and Java


[ ![Download](https://api.bintray.com/packages/rmkrishna/rmkrishna/MPermission/images/download.svg?version=0.0.1) ](https://bintray.com/rmkrishna/rmkrishna/MPermission/0.0.1/link) [![Apache License V.2](https://img.shields.io/badge/license-Apache%20V.2-blue.svg)](https://github.com/rmkrishna/MLog-Kotlin/blob/master/LICENSE)


## Usage
### Dependency
```groovy
implementation 'com.rmkrishna:permission:0.0.2'
```

### How to use

```Kotlin
askPermissions(MPermission.WRITE_CONTACTS) {
    granted {
        // If permission(or 's) granted successfully
    }

    denied {
        // If permission(or 's) denied by the user
    }

    neverAskAgain {
        // User selected the option for never ask again
    }
}
```

``` Java
MPermission.askPermissions(MainJavaActivity.this, new String[]{MPermission.WRITE_CONTACTS}, new MPermissionListener() {

    @Override
    public void neverAskAgain(@NotNull List<String> permissions) {

    }

    @Override
    public void denied(@NotNull List<String> permissions) {
        // If the user denied the permission(or 's)
    }

    @Override
    public void granted() {
        // If permission(or 's) granted successfully
    }
});
```


## License
<pre>

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

Copyright 2018 Muthukrishnan R

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
