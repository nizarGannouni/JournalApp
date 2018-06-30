package com.journal.gannouni.journalapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.journal.gannouni.journalapp.databinding.ActivityNewUserBinding;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityNewUserBinding mActivityNewUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNewUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivityNewUserBinding.btnNewSignup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == mActivityNewUserBinding.btnNewSignup.getId()) {

            String email = mActivityNewUserBinding.etNewEmail.getText().toString().trim();
            String password = mActivityNewUserBinding.etNewPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(NewUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(NewUserActivity.this, getString(R.string.ErrorCountAlready), Toast.LENGTH_LONG).show();
                            } else {
                                startActivity(new Intent(NewUserActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });

        }
    }
}
