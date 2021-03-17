package com.example.noteapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.noteapp.models.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Query("SELECT * FROM note ORDER BY title ASC")
    List<Note> sortAll();

    @Delete
    void  delete(Note note);
}
