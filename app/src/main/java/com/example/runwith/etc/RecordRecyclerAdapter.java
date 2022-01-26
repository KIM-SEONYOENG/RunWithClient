package com.example.runwith.etc;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runwith.R;
import com.example.runwith.domain.RecordEntity;

import java.util.ArrayList;
import java.util.List;

public class RecordRecyclerAdapter extends RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder> {
    private static final String TAG = "recycler";

    private ArrayList<RecordEntity> recordList;

    public void setRecordList(ArrayList<RecordEntity> list) {
        this.recordList = list;
        if(recordList!=null)
            Log.d(TAG, "list is ok");
        else
            Log.d(TAG, "null list... Why???");

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(recordList.get(position));
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        RecordEntity item = recordList.get(position);

        holder.tvDate.setText(item.getDay());
        holder.tvRecord.setText(String.valueOf(item.getCount()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvRecord;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvRecord = (TextView) itemView.findViewById(R.id.tvRecord);
        }

        public void onBind(RecordEntity record) {
            tvDate.setText(record.getDay());
            tvRecord.setText(record.getCount());
        }
    }
}
