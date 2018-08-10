package org.samtech.naatexamen.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.model.retrofitmodels.request.Data;
import org.samtech.naatexamen.model.retrofitmodels.request.LoginRequest;
import org.samtech.naatexamen.model.retrofitmodels.response.LoginResponse;
import org.samtech.naatexamen.retrofit.ApiClient;
import org.samtech.naatexamen.retrofit.ApiDefinition;
import org.samtech.naatexamen.utils.KeyboardUtils;
import org.samtech.naatexamen.utils.Keys;
import org.samtech.naatexamen.utils.MessagesUtils;
import org.samtech.naatexamen.utils.NetworkUtils;
import org.samtech.naatexamen.utils.PreferencesUtils;
import org.samtech.naatexamen.utils.ProgressUtils;
import org.samtech.naatexamen.utils.RegexUtils;
import org.samtech.naatexamen.utils.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.samtech.naatexamen.utils.Keys.JSON_FACTORY;
import static org.samtech.naatexamen.utils.Keys.LOGIN_RESPONSE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {

    private ProgressBar progressBar;
    private TextView progressTextView, versionApp;
    private LinearLayout formLoginContainer;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_login);
        progressBar = findViewById(R.id.act_login_progress);
        progressTextView = findViewById(R.id.act_login_progress_status_txt);
        versionApp = findViewById(R.id.act_login_version_app);
        formLoginContainer = findViewById(R.id.act_login_form_container);
        usernameEditText = findViewById(R.id.act_login_username);
        passwordEditText = findViewById(R.id.act_login_password);
        AppCompatCheckBox showPasswordCheck = findViewById(R.id.act_login_show_pass_check);
        Button enterBtn = findViewById(R.id.act_login_enter_btn);

        showPasswordCheck.setOnCheckedChangeListener(this);
        passwordEditText.setOnEditorActionListener(this);
        enterBtn.setOnClickListener(this);

        setVersionApp();
    }

    private void setVersionApp() {
        PackageInfo pInfo;
        try {

            pInfo = LoginActivity.this.getPackageManager().
                    getPackageInfo(LoginActivity.this.getPackageName(), 0);
            String version = "Versión : " + pInfo.versionName;
            versionApp.setText(version);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_login_enter_btn:
                doLoginAccess();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

        switch (textView.getId()) {
            case R.id.act_login_password:
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    doLoginAccess();
                    return true;
                }
                break;
        }
        return false;
    }

    private void cleanUpLoginForm() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    private boolean isValidFormLogin() {

        String username = StringUtils.getEText(usernameEditText);
        String password = StringUtils.getEText(passwordEditText);

        if (RegexUtils.isNotemptyValidate(username)) {
            if (RegexUtils.isNotemptyValidate(password)) {
                return true;
            } else {
                MessagesUtils.showToast(LoginActivity.this, "Debes ingresar una constraseña");
            }
        } else {
            MessagesUtils.showToast(LoginActivity.this, "Debes ingresar un usuario");
        }
        return false;
    }

    private void doLoginAccess() {
        if (NetworkUtils.isConnected(this)) {
            if (isValidFormLogin()) {
                requestAccessToServer(StringUtils.getEText(usernameEditText),
                        StringUtils.getEText(passwordEditText));
            }
        } else {
            MessagesUtils.showToast(this, "No se detecta una conexión a internet");
        }
    }


    private void requestAccessToServer(String paramUserName, String paramPassword) {
        cleanUpLoginForm();
        KeyboardUtils.hideKeyboard(LoginActivity.this);

        ProgressUtils.setVisibilityToView
                (progressBar, formLoginContainer, true,
                        progressTextView, "Solicitando acceso ...");

        ApiDefinition apiDefinition =
                ApiClient.api(Keys.BASE_URL_DEV, JSON_FACTORY).create(ApiDefinition.class);

        Data data = new Data(paramPassword, paramUserName);
        LoginRequest loginRequest = new LoginRequest(data);

        Call<LoginResponse> callLoginResponse =
                apiDefinition.requestLogin(loginRequest);

        callLoginResponse.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,
                                   @NonNull Response<LoginResponse> response) {

                ProgressUtils.setVisibilityToView
                        (progressBar, formLoginContainer, false,
                                progressTextView, "");

                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        PreferencesUtils.with(LoginActivity.this).saveObjectAsPrefs(loginResponse, LOGIN_RESPONSE);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                System.out.print(t.getLocalizedMessage());
                ProgressUtils.setVisibilityToView
                        (progressBar, formLoginContainer, false,
                                progressTextView, "");
            }
        });


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (!isChecked) {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }
}
