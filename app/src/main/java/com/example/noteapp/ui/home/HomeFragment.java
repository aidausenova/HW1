package com.example.noteapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.App;
import com.example.noteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.noteapp.models.Note;

import java.util.List;

public class HomeFragment extends Fragment implements NoteAdapter.OnTouchItem {
    private boolean check = false;
    private NoteAdapter adapter;
    private int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        adapter.setListener(this);
        setHasOptionsMenu(true);
        List<Note> list = App.getDatabase().noteDao().getAll();
        adapter.setList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = false;
                openNote(null);
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk_note", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = (Note) result.getSerializable("note");
                if (check) adapter.getList(position, note);
                else adapter.addItem(note);
            }
        });
    }

    private void openNote(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.noteFragment, bundle);
    }

    @Override
    public void click(int position) {
        this.position = position;
        check = true;
        Note note = adapter.getItem(position);
        openNote(note);
    }

    @Override
    public void longClick(int position) {
        new AlertDialog.Builder(getContext()).setTitle("SMS").setMessage("Delete?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getDatabase().noteDao().delete(adapter.getItem(position));
                App.getDatabase().noteDao().update(adapter.getItem(position));
                adapter.remove(position);
            }
        }).setNegativeButton("NO", null).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_fragment_pop, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sortHF) {
            adapter.sortList(App.getDatabase().noteDao().sortAll());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}