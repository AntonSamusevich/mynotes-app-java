package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class NotesAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>(); //список фрагментов
    private final ArrayList<String> fragmentTitle = new ArrayList<>(); //список заголовков вкладок

    public NotesAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior); //вызов конструктора родительского класса
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position); //получение фрагмента по позиции
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size(); //получение общего количества фрагментов
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentArrayList.add(fragment); //добавление фрагмента в список
        fragmentTitle.add(title); //добавление заголовка в список
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position); //получение заголовка вкладки по позиции
    }
}
