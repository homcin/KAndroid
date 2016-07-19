package com.keegan.kandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keegan.core.ActionCallbackListener;
import com.keegan.kandroid.R;

/**
 * Created by Administrator on 2016/7/9.
 */
public class RegisterActivity extends KBaseActivity {

    private EditText etPhone;
    private EditText etCode;
    private EditText etPassword;
    private Button btnSendCode;
    private Button btnRegister;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        setContentView(R.layout.activity_register);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etCode = (EditText) findViewById(R.id.etCode);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSendCode = (Button) findViewById(R.id.btnSendCode);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSendCode();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegister();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void toSendCode() {
        String phoneNum = etPhone.getText().toString();
        btnSendCode.setEnabled(false);
        this.appAction.sendSmsCode(phoneNum, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(context, R.string.registerActivity_toast_codeHasSent, Toast.LENGTH_SHORT).show();
                btnSendCode.setEnabled(true);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                btnSendCode.setEnabled(true);
            }
        });
    }

    // 准备注册
    public void toRegister() {
        String phoneNum = etPhone.getText().toString();
        String code = etCode.getText().toString();
        String password = etPassword.getText().toString();
        btnRegister.setEnabled(false);
        this.appAction.register(phoneNum, code, password, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(context, R.string.registerActivity_toast_registerSuccess, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CouponListActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                btnRegister.setEnabled(true);
            }
        });
    }
}
