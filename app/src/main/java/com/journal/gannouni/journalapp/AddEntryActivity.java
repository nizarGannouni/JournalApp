package com.journal.gannouni.journalapp;

import android.app.IntentService;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.journal.gannouni.journalapp.database.AppDataBase;
import com.journal.gannouni.journalapp.database.DiaryEntry;
import com.journal.gannouni.journalapp.databinding.ActivityAddEntryBinding;

import java.util.Date;

public class AddEntryActivity extends AppCompatActivity {

    private AppDataBase mAppDataBase;
    private boolean isUpdate = false;
    private DiaryEntry mDiaryEntry;
    private ActivityAddEntryBinding mActivityAddEntryBinding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEntryBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_entry);
        mAppDataBase = AppDataBase.getInstance(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("update")) {
            isUpdate = true;
            getSupportActionBar().setTitle(getString(R.string.update));
            mActivityAddEntryBinding.btnAddEntry.setText(getString(R.string.update));
            mDiaryEntry = mAppDataBase.mDiaryDao().loadEntryById(intent.getIntExtra("update", 0));
            mActivityAddEntryBinding.etTitle.setText(mDiaryEntry.getTitle());
            mActivityAddEntryBinding.etDescription.setText(mDiaryEntry.getDescription());
        }
        mActivityAddEntryBinding.btnAddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                if (TextUtils.isEmpty(mActivityAddEntryBinding.etTitle.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter a title!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mActivityAddEntryBinding.etDescription.getText())) {
                    Toast.makeText(getApplicationContext(), "Enter a description!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isUpdate) {

                    mDiaryEntry.setTitle(mActivityAddEntryBinding.etTitle.getText().toString());
                    mDiaryEntry.setDescription(mActivityAddEntryBinding.etDescription.getText().toString());
                    mAppDataBase.mDiaryDao().updateEntry(mDiaryEntry);
                } else {
                    DiaryEntry mDiaryEntry = new DiaryEntry(FirebaseAuth.getInstance().getCurrentUser().getUid(), mActivityAddEntryBinding.etTitle.getText().toString(),
                            mActivityAddEntryBinding.etDescription.getText().toString(), date);
                    mAppDataBase.mDiaryDao().insertEntry(mDiaryEntry);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
