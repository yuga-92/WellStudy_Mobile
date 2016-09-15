package com.bootcamp.wellstudy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bootcamp.wellstudy.api.ServiceGenerator;
import com.bootcamp.wellstudy.api.WellStudyClient;
import com.bootcamp.wellstudy.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bootcamp.wellstudy.Constants.USER_PREFERENCES;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SharedPreferences userPrefs;
    private Boolean isPrefsSaved;

    @Bind(R.id.input_email) EditText _login;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id._remember) CheckBox rememberCheckbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loadPreferences();
        if (isPrefsSaved) {
            rememberCheckbox.setChecked(true);
            login();
        }
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }
    private void loadPreferences() {
        userPrefs = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        _login.setText(userPrefs.getString("Login", ""));
        _passwordText.setText(userPrefs.getString("Password", ""));
        isPrefsSaved = userPrefs.getBoolean("isPrefsSaved", false);
    }

    private void savePreferences(String login, String password) {
        userPrefs = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("Login", login);
        editor.putString("Password", password);
        editor.putBoolean("isPrefsSaved", true);
        editor.apply();
    }

    private void deletePreferences(String login, String password) {
        userPrefs = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.remove(login);
        editor.remove(password);
        editor.apply();
    }

    private void login() {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
       // _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        String login = _login.getText().toString();
        String password = _passwordText.getText().toString();
        connectToServerAndLogin(login, password);
        if (rememberCheckbox.isChecked()) {
            savePreferences(login, password);
        } else {
            deletePreferences("Login","Password");
        }
    }

    private void connectToServerAndLogin(final String login, final String password) {
        ServiceGenerator serviceGenerator = ServiceGenerator.getInstance(login, password);
        WellStudyClient client = serviceGenerator.getService();
        Call<User> call = client.login();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    //User user = response.body();
                    onLoginSuccess();
                    /** For debug */
                    System.out.println(response.code() + "###### " + response.isSuccessful() +
                            response.headers() + "88888" + response.message());
                    System.out.println("###############" + response.body());
                    /** For debug */
                } else if (response.code() == 401) {
                    System.out.println("##UNA###"+login+"  "+ password);
                    onLoginFailed();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Something went wrong: " + t.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivityAdmin
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }


    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        ServiceGenerator.setInstanceNull();
        _loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String login = _login.getText().toString();
        String password = _passwordText.getText().toString();

        if (login.isEmpty()) {
            _login.setError("can not be empty");
            valid = false;
        } else {
            _login.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 60) {
            _passwordText.setError("between 5 and 60 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
