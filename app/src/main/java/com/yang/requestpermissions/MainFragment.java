package com.yang.requestpermissions;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private Button btnScanCode;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btnScanCode = view.findViewById(R.id.btn_scan_code);
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScanCodePage();
            }
        });
        return view;
    }

    private void showScanCodePage(){
        startActivity(new Intent(getContext(), ScanCodeActivity.class));
    }


//    private void attempToCall(String phone) {
//        if (!isIntentExisting(getContext(), Intent.ACTION_DIAL)) {
//            Toast.makeText(getContext(), "该设备不能打电话", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        mPhone = phone;
//        requestDangerousPermissions(permissions, AppConstant.RequestCode.WEBVIEW_CALL);
//    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //判断电话应用是否存在
    public boolean isIntentExisting(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }
}
