package com.yang.requestpermissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: 杨进玺
 * Time: 2018/4/17  10:37
 */

public class BaseActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    /**
     * 请求权限
     *
     * @param permissions
     * @param requestCode
     */
    public void requestDangerousPermissions(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            handlePermissionResult(requestCode, true);
            return;
        }
        List<String> needPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needPermissions.add(permission);
            }
        }
        if (needPermissions.size() == 0) {
            handlePermissionResult(requestCode, true);
            return;
        }
        needPermissions = Arrays.asList(permissions);
        ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), requestCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean granted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }
        boolean finish = handlePermissionResult(requestCode, granted);
        if (!finish){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    /**
     * 处理请求危险权限的结果
     * @return
     */
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        return false;
    }
}
