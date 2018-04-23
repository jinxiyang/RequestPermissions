package com.yang.requestpermissions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private Button btnScanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScanCode = findViewById(R.id.btn_scan_code);
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScanCodePage();
            }
        });
    }


    private void showScanCodePage(){
        startActivity(new Intent(MainActivity.this, ScanCodeActivity.class));
    }
}
