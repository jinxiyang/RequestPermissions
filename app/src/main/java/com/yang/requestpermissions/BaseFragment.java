package com.yang.requestpermissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 杨进玺
 * Time: 2018/4/17  10:38
 */

public class BaseFragment extends Fragment {

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
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                needPermissions.add(permission);
            }
        }
        if (needPermissions.size() == 0) {
            handlePermissionResult(requestCode, true);
            return;
        }
        requestPermissions(needPermissions.toArray(new String[needPermissions.size()]), requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
        }

        if (!handlePermissionResult(requestCode, isGranted)){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 处理请求危险权限的结果
     *
     * @param requestCode
     * @param isGranted 是否允许
     * @return
     */
    public boolean handlePermissionResult(int requestCode, boolean isGranted) {
        return false;
    }
}
