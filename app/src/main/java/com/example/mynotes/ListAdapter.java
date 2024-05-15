package com.example.mynotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Note> notes; //список заметок
    private Context context; //контекст приложения

    public ListAdapter(Context context, List<Note> notes) {
        this.context = context; //инициализация контекста
        this.notes = notes; //инициализация списка заметок
    }

    @Override
    public int getCount() {
        return notes.size(); //получение общего количества заметок
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position); //получение заметки по позиции
    }

    @Override
    public long getItemId(int position) {
        return position; //получение идентификатора заметки по позиции
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false); //создание макета для элемента списка
            holder = new ViewHolder(); //создание объекта ViewHolder
            holder.idTextView = convertView.findViewById(R.id.id); //находим TextView
            holder.descriptionTextView = convertView.findViewById(R.id.description); //находим TextView
            convertView.setTag(holder); //устанавливаем тег для convertView
        } else {
            holder = (ViewHolder) convertView.getTag(); //получаем объект ViewHolder из тега
        }

        Note note = notes.get(position); //получаем текущую заметку
        holder.idTextView.setText(String.valueOf(note.getId())); //устанавливаем текст для TextView
        holder.descriptionTextView.setText(note.getDescription()); //устанавливаем текст для TextView
        return convertView; //возвращаем созданный элемент списка
    }

    public void updateData(List<Note> updatedNotes) {
        notes.clear(); //очищаем список заметок
        notes.addAll(updatedNotes); //добавляем обновленные заметки
    }

    static class ViewHolder {
        TextView idTextView; //textView для ID
        TextView descriptionTextView; //textView для описания
    }
}
