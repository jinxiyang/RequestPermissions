package com.yang.requestpermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yang.requestpermissions.util.StatusBarHelper;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanCodeActivity extends BaseActivity  implements QRCodeView.Delegate{

    public static final String SCAN_RESULT = "scan_result";

    public static final int REQUEST_CODE_CAMERA = 0x123;

    private ZXingView mZXingView;

    private String[] cameraPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    private boolean hasRequestPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.translucent(this);
        setContentView(R.layout.activity_scan_code);

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkDangerousPermissions(this, cameraPermissions)){
            mZXingView.startCamera();
            mZXingView.showScanRect();
            mZXingView.startSpotDelay(100);
        }else {
            if (!hasRequestPermission){
                showScanCodeTip();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mZXingView.stopCamera();
    }

    private void showScanCodeTip() {
        ScanCodeTipDialog dialog = new ScanCodeTipDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hasRequestPermission = true;
                requestDangerousPermissions(cameraPermissions, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show(getSupportFragmentManager(), ScanCodeActivity.class.getSimpleName());
    }

    @Override
    public boolean handlePermissionResult(int requestCode, boolean granted) {
        if (requestCode == REQUEST_CODE_CAMERA){
            if (!granted){
                finish();
            }
            return true;
        }
        return super.handlePermissionResult(requestCode, granted);
    }

    @Override
    public void onDestroy() {
        mZXingView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        mZXingView.stopSpot();
        mZXingView.stopCamera();
        Intent intent = getIntent();
        if (intent == null){
            intent = new Intent();
        }
        intent.putExtra(SCAN_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
