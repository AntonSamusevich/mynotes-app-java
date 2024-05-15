package com.example.mynotes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentDelete extends Fragment {

    private NoteDatabase database; //инициализация базы данных

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false); //создание макета фрагмента

        EditText enterNumber = view.findViewById(R.id.enterNumber); //инициализируем поле ввода
        Button buttonDelete = view.findViewById(R.id.buttonDelete); //инициализируем кнопку

        database = new NoteDatabase(getActivity()); //инициализируем базу данных

        //устанавливаем фильтры ввода для EditText
        enterNumber.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        enterNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteIdString = enterNumber.getText().toString(); //получаем введенный номер заметки
                if (!noteIdString.isEmpty()) { //проверка на пустое описание
                    int noteId = Integer.parseInt(noteIdString);
                    if (database.existNote(noteId)) { //если такое id существует
                        deleteNoteById(noteId);
                        showToast("Заметка успешно удалена");
                        enterNumber.getText().clear(); //очищаем поле ввода
                    } else {
                        showToast("Такой заметки не существует");
                    }
                } else {
                    showToast("Введите номер заметки");
                }
            }
        });

        return view; //возвращаем созданный макет
    }

    private void deleteNoteById(int id) {
        database.deleteNote(id); //удаляем заметку из базы данных
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show(); //выводим сообщение
    }
}
