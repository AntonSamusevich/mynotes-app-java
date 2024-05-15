package com.example.mynotes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentAdd extends Fragment {

    private NoteDatabase databaseHelper; //инициализация базы данных

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false); //создание макета фрагмента

        databaseHelper = new NoteDatabase(getActivity()); //инициализация базы данных

        EditText enterDescription = view.findViewById(R.id.enterDescription); //находим поле ввода описания

        Button addButton = view.findViewById(R.id.buttonAdd); //находим кнопку
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = enterDescription.getText().toString(); //получаем введенное описание

                if (!description.isEmpty()) { //проверка на пустое описание
                    long result = databaseHelper.addNote(description); //добавление заметки в базу данных

                    if (result != -1) {
                        enterDescription.setText(""); //очищаем поле ввода
                        showToast("Заметка успешно добавлена");
                    }
                } else {
                    showToast("Введите описание заметки");
                }
            }
        });

        return view; //возвращаем созданный макет
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show(); //выводим сообщение
    }
}
