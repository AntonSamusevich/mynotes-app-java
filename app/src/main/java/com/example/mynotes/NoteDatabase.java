package com.example.mynotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db"; //имя базы данных
    private static final int DATABASE_VERSION = 1; //версия базы данных
    private static final String TABLE_NAME = "notes"; //имя таблицы
    private static final String COLUMN_ID = "id"; //имя столбца ID
    private static final String COLUMN_DESCRIPTION = "description"; //имя столбца описания

    public NoteDatabase(Context context) { //класс, который представляет базу данных
        super(context, DATABASE_NAME, null, DATABASE_VERSION); //вызов конструктора родительского класса
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //метод вызывается при создании базы данных
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESCRIPTION + " TEXT)"; //создание таблицы с полями ID и описания
        db.execSQL(createTableQuery); //выполнение запроса
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //метод вызывается при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //удаление таблицы при обновлении
        onCreate(db); //создание новой таблицы
    }

    public long addNote(String description) {
        SQLiteDatabase db = this.getWritableDatabase(); //получение базы данных для записи
        ContentValues values = new ContentValues(); //создание объекта для хранения значений
        values.put(COLUMN_DESCRIPTION, description); //добавление описания в объект
        long newRowId = db.insert(TABLE_NAME, null, values); //вставка записи в таблицу
        db.close(); //закрытие базы данных
        return newRowId; //возвращение ID новой записи
    }

    public List<Note> showNotes() {
        List<Note> notes = new ArrayList<>(); //создание списка заметок
        SQLiteDatabase db = this.getReadableDatabase(); //получение базы данных для чтения

        //выполнение запроса на выборку всех записей
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)); //получение ID из курсора
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)); //получение описания из курсора
                notes.add(new Note(id, description)); //добавление новой заметки в список
            } while (cursor.moveToNext()); //переход к следующей записи
            cursor.close(); //закрытие курсора
        }

        return notes; //возвращение списка заметок
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase(); //получение базы данных для записи
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}); //удаление записи по ID
        db.close(); //закрытие базы данных
    }

    public void updateNote(int id, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase(); //получение базы данных для записи
        ContentValues values = new ContentValues(); //создание объекта для хранения значений
        values.put(COLUMN_DESCRIPTION, newDescription); //добавление нового описания в объект
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}); //обновление записи по ID
        db.close(); //закрытие базы данных
    }

    public boolean existNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase(); //получение базы данных для чтения
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID}, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close(); //закрытие курсора
        }
        db.close(); //закрытие базы данных

        return exists;
    }
}
