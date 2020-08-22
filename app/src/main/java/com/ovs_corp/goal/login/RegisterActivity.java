package com.ovs_corp.goal.login;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ovs_corp.goal.AppInfo;
import com.ovs_corp.goal.R;


public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText UserEmail, UserPassword, ConfimPassword;
    private FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppInfo.nightModeState)
            setTheme(R.style.NightAppTheme);
        else
            setTheme(R.style.AppTheme);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();



        createAccountButton = findViewById(R.id.register_button);
        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        ConfimPassword = findViewById(R.id.register_confim_password);
        Context context;
        loadingbar = new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                String copassword = ConfimPassword.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "Введите Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(copassword))
                {
                    Toast.makeText(RegisterActivity.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingbar.setTitle("Аккаунт создается");
                loadingbar.setMessage("Подождите, идет создание аккаунта на наших серверах...");
                loadingbar.setCanceledOnTouchOutside(true);
                loadingbar.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String currentUserID = mAuth.getCurrentUser().getUid();
                            RootRef.child("Users").child(currentUserID).setValue("");
                            RootRef.child("Users").child(currentUserID).child("TutorMessages").setValue("");

                            Intent g = new Intent(RegisterActivity.this, Register2Activity.class);
                            startActivity(g);
                            finish();
                            //Toast.makeText(RegisterActivity.this, "Аккаунт успешно создан!", Toast.LENGTH_SHORT).show();

                            loadingbar.dismiss();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Ошибка " + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }

                    }
                });
            }
        });

    }

    public void BackToLogInMenu(View view) {
        super.onBackPressed();
    }
}
