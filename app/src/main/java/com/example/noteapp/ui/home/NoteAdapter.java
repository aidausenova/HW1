package com.example.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<String> list;

    private OnTouchItem item;

    public interface OnTouchItem {
        void click(String text, int position);
    }

    public void setListener(OnTouchItem item) {
        this.item = item;
    }

    public NoteAdapter() {
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false), item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position % 2 == 0) holder.itemView.setBackgroundColor(Color.YELLOW);
        else holder.itemView.setBackgroundColor(Color.GREEN);
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addNewOne(String text) {
        list.add(0, text);
        notifyItemChanged(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textw;

        public ViewHolder(@NonNull View itemView, OnTouchItem item) {
            super(itemView);
            textw = itemView.findViewById(R.id.textTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.click(list.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder d = new AlertDialog.Builder(v.getRootView().getContext());
                    d.setTitle("Delete?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), list.size());
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return true;
                }
            });
        }

        public void bind(String s) {
            textw.setText(s);
        }
    }
}
