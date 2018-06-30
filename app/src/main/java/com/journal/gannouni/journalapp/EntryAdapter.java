package com.journal.gannouni.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journal.gannouni.journalapp.database.DiaryEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    private List<DiaryEntry> mDiaryEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
    private ItemClickListener mItemClickListener;

    public EntryAdapter(Context context, ItemClickListener itemClickListener) {
        super();
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.entry_layout, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {

        holder.bind(mDiaryEntries.get(position));

    }


    @Override
    public int getItemCount() {
        return mDiaryEntries.size();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public void setEntries(List<DiaryEntry> mDiaryEntries) {
        this.mDiaryEntries = mDiaryEntries;
        notifyDataSetChanged();
    }

    public List<DiaryEntry> getDiary() {
        return mDiaryEntries;
    }

    class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleEntry;
        TextView descriptionEntry;
        TextView dateEntry;

        public EntryViewHolder(View itemView) {
            super(itemView);
            titleEntry = itemView.findViewById(R.id.titleEntry);
            descriptionEntry = itemView.findViewById(R.id.descEntry);
            dateEntry = itemView.findViewById(R.id.dateEntry);
            itemView.setOnClickListener(this);
        }

        void bind(DiaryEntry diaryEntry) {
            titleEntry.setText(diaryEntry.getTitle());
            descriptionEntry.setText(diaryEntry.getDescription());
            dateEntry.setText(dateFormat.format(diaryEntry.getDateEntry()));

        }

        @Override
        public void onClick(View view) {
            int elementId = mDiaryEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
