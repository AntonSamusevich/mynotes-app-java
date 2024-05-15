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

public class FragmentUpdate extends Fragment {

    private NoteDatabase database; //инициализация базы данных

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false); //создание макета фрагмента

        EditText updateNumber = view.findViewById(R.id.updateNumber); //инициализируем поле ввода
        EditText updateDescription = view.findViewById(R.id.updateDescription); //инициализируем поле ввода
        Button buttonUpdate = view.findViewById(R.id.buttonUpdate); //инициализируем кнопку

        database = new NoteDatabase(getActivity()); //инициализируем базу данных

        //устанавливаем фильтры ввода для EditText
        updateNumber.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        updateNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //получаем введенный данные
                String noteIdString = updateNumber.getText().toString();
                String newDescription = updateDescription.getText().toString();

                if (!noteIdString.isEmpty() && !newDescription.isEmpty()) { //проверка на пустое описание
                    int noteId = Integer.parseInt(noteIdString);
                    if (database.existNote(noteId)) { //проверка на существующий id
                        updateNoteDescription(noteId, newDescription);
                        showToast("Заметка успещно обновлена");
                        updateNumber.getText().clear();
                        updateDescription.getText().clear();
                    } else {
                        showToast("Такой заметки не существует");
                    }
                } else {
                    showToast("Введите номер заметки и описание");
                }
            }
        });

        return view; //возвращаем созданный макет
    }

    private void updateNoteDescription(int id, String newDescription) {
        database.updateNote(id, newDescription); //обновляем заметку в базе данных
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show(); //выводим сообщение
    }
}
