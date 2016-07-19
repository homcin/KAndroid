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
 * 登录
 *
 * @version 1.0 创建时间：15/6/26
 */
public class LoginActivity extends KBaseActivity {

    private EditText etPhone;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnToRegister;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle saveInstanceState) {
        setContentView(R.layout.activity_login);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnToRegister = (Button) findViewById(R.id.btnToRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegister();
            }
        });

    }

    @Override
    protected void loadData() {

    }

    // 准备登录
    private void toLogin() {
        String loginName = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        btnLogin.setEnabled(false);
        this.appAction.login(loginName, password, new ActionCallbackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                Toast.makeText(context, R.string.loginActivity_toast_loginSuccess, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CouponListActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
            }
        });
    }

    // 进入注册页
    private void toRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}

