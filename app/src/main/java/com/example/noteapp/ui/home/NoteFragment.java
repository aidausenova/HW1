package com.example.noteapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.noteapp.App;
import com.example.noteapp.R;

import com.example.noteapp.models.Note;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteFragment extends Fragment {
    private Note note;
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_Text);
        Button btnSave = view.findViewById(R.id.btnSave);
        note = (Note) requireArguments().getSerializable("note");
        if (note != null) editText.setText(note.getTitle());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = editText.getText().toString().trim();
                if (note == null) {
                    note = new Note(txt);
                    App.getDatabase().noteDao().insert(note);
                } else {
                    note.setTitle(txt);
                    App.getDatabase().noteDao().update(note);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                getParentFragmentManager().setFragmentResult("rk_note", bundle);
                //close();
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
//    private void saveToFirestore(Note note) {
//        FirebaseFirestore.getInstance().collection("notes").add(note).addOnCanceledListener(
//    }
}