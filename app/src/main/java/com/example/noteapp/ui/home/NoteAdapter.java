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

import com.example.noteapp.App;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;

import com.example.noteapp.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> list;

    private OnTouchItem item;

    public void addItem(Note note) {
        list.add(0, note);
        notifyItemChanged(list.indexOf(0));
    }

    public Note getItem(int position) {
        return list.get(position);
    }

    public void sortList(List<Note> sortAll) {
        list.clear();
        list.addAll(sortAll);
        notifyDataSetChanged();
    }

    public void setList(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public interface OnTouchItem {
        void click(int position);

        void longClick(int position);
    }

    public void setListener(OnTouchItem item) {
        this.item = item;
    }

    public NoteAdapter() {
        list = new ArrayList<Note>();
    }

    public void getList(int position, Note note) {
        list.set(position, note);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false), item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0) holder.itemView.setBackgroundColor(Color.YELLOW);
        else holder.itemView.setBackgroundColor(Color.GREEN);
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textw;

        public ViewHolder(@NonNull View itemView, OnTouchItem item) {
            super(itemView);
            textw = itemView.findViewById(R.id.textTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.click(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    item.longClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Note note) {
            textw.setText(note.getTitle());
        }
    }
}
