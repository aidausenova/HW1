package com.example.noteapp.ui.home;

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

    public NoteAdapter() {
        list = new ArrayList<>();
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
        list.add("gpjogjie");
    }

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addNewOne(String text) {
        list.add(0,text);
        notifyItemChanged(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textw;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textw = itemView.findViewById(R.id.textTitle);
        }
        public void bind(String s) {
            textw.setText(s);
        }
    }
}
