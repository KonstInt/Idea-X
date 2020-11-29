package com.ovs_corp.bstorm.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ovs_corp.bstorm.AppInfo;
import com.ovs_corp.bstorm.MainActivity;
import com.ovs_corp.bstorm.R;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button enterAppButton;
    private EditText UserEmail, UserPassword;
    ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();

        enterAppButton = findViewById(R.id.login_button);
        UserEmail = findViewById(R.id.login_email);
        UserPassword = findViewById(R.id.login_password);
        loadingbar = new ProgressDialog(this);

        enterAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();

                if(email.isEmpty())
                {
                    Toast.makeText(Login.this, "Введите Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.isEmpty()) {
                    Toast.makeText(Login.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingbar.setTitle("Sign In");
                loadingbar.setMessage("Вход...");
                loadingbar.setCanceledOnTouchOutside(true);
                loadingbar.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent g = new Intent(Login.this, MainActivity.class);
                            g.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(g);
                            finish();
                            loadingbar.dismiss();
                            finish();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(Login.this, "Ошибка " + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
        Intent mainIntent = new Intent(Login.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
