package com.example.mynotes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout; //инициализация вкладок
    private ViewPager viewPager; //инициализация контейнера для фрагментов
    private NotesAdapter notesAdapter; //инициализация адаптера фрагментов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //установка макета

        tabLayout = findViewById(R.id.tabLayout); //идентификатор вкладок
        viewPager = findViewById(R.id.viewPager); //идентификатор контейнера для фрагментов

        //создание адаптера
        notesAdapter = new NotesAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //добавление фрагментов
        notesAdapter.addFragment(new FragmentShow(), "Show");
        notesAdapter.addFragment(new FragmentAdd(), "Add");
        notesAdapter.addFragment(new FragmentDelete(), "Delete");
        notesAdapter.addFragment(new FragmentUpdate(), "Update");

        viewPager.setAdapter(notesAdapter); //установка адаптера для контейнера
        tabLayout.setupWithViewPager(viewPager); //связывание вкладок с контейнером
    }
}
