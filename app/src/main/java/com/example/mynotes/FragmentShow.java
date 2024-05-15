package com.example.mynotes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class FragmentShow extends Fragment {

    private ListView listView; //поле для отображения списка
    private ListAdapter adapter; //адаптер для управления данными списка
    private NoteDatabase database; //инициализация базы данных

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false); //создание макета фрагмента

        database = new NoteDatabase(getActivity()); //инициализация базы данных
        List<Note> notes = database.showNotes(); //получение списка заметок

        listView = view.findViewById(R.id.listView); //находим ListView по идентификатору
        adapter = new ListAdapter(getActivity(), notes); //создание адаптера для списка
        listView.setAdapter(adapter); //установка адаптера для ListView

        return view; //возвращаем созданный макет
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Note> updatedNotes = database.showNotes(); //получение обновленного списка заметок
        adapter.updateData(updatedNotes); //обновление данных в адаптере

        adapter.notifyDataSetChanged(); //уведомление адаптера об изменении данных
    }
}
