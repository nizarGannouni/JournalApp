package com.journal.gannouni.journalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journal.gannouni.journalapp.database.AppDataBase;
import com.journal.gannouni.journalapp.database.DiaryEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DiaryActivity extends AppCompatActivity implements EntryAdapter.ItemClickListener {
    private RecyclerView mRecylerDiary;
    private EntryAdapter mEntryAdapter;
    private AppDataBase mAppDataBase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        mRecylerDiary = findViewById(R.id.recylerDiary);
        mRecylerDiary.setLayoutManager(new LinearLayoutManager(this));
        mEntryAdapter = new EntryAdapter(this, this);
        mRecylerDiary.setAdapter(mEntryAdapter);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecylerDiary.addItemDecoration(decoration);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                List<DiaryEntry> entries = mEntryAdapter.getDiary();
                dialogDeleteEntry(entries.get(position));

            }
        }).attachToRecyclerView(mRecylerDiary);
        mAppDataBase = AppDataBase.getInstance(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(DiaryActivity.this, AddEntryActivity.class);
                startActivity(mIntent);
            }
        });

    }

    private void dialogDeleteEntry(final DiaryEntry diaryEntry) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAppDataBase.mDiaryDao().deleteEntry(diaryEntry);
                        mEntryAdapter.setEntries(mAppDataBase.mDiaryDao().loadAllEntry(userId));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mEntryAdapter.setEntries(mAppDataBase.mDiaryDao().loadAllEntry(userId));
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mEntryAdapter.setEntries(mAppDataBase.mDiaryDao().loadAllEntry(userId));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(DiaryActivity.this, AddEntryActivity.class);
        intent.putExtra("update", itemId);
        startActivity(intent);
    }
}
