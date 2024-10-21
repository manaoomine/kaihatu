package com.example.myapplication.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MaterialCalendarView calendarView;
    //    private com.applandeo.materialcalendarview.CalendarView calendarView;
    private CheckBox morningCheckBox, noonCheckBox, eveningCheckBox;
    private CalendarDay selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UIコンポーネントの取得
        calendarView = binding.calendarView;
        morningCheckBox = binding.morningCh;
        noonCheckBox = binding.noonCh;
        eveningCheckBox = binding.eveningCh;

        // SharedPreferences の初期化
        SharedPreferences preferences = getActivity().getSharedPreferences("calendar_prefs", 0);

        // カレンダーの日付選択リスナーを設定
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = date; // 選択された日付を保持
            String dateKey = selectedDate.toString();

            // 保存されたチェックボックスの状態を読み込む
            boolean morningChecked = preferences.getBoolean(dateKey + "_morning", false);
            boolean noonChecked = preferences.getBoolean(dateKey + "_noon", false);
            boolean eveningChecked = preferences.getBoolean(dateKey + "_evening", false);

            morningCheckBox.setChecked(morningChecked);
            noonCheckBox.setChecked(noonChecked);
            eveningCheckBox.setChecked(eveningChecked);

            updateCalendarMarker(); // デコレーターを更新
        });

        // チェックボックスのリスナーを設定
        morningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());
        noonCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());
        eveningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());
    }


    private void updateCalendarMarker() {
        if (selectedDate == null) return;

        // SharedPreferences の初期化
        SharedPreferences preferences = getActivity().getSharedPreferences("calendar_prefs", 0);
        SharedPreferences.Editor editor = preferences.edit();

        // 選択された日付をキーとしてチェックボックスの状態を保存
        String dateKey = selectedDate.toString();
        editor.putBoolean(dateKey + "_morning", morningCheckBox.isChecked());
        editor.putBoolean(dateKey + "_noon", noonCheckBox.isChecked());
        editor.putBoolean(dateKey + "_evening", eveningCheckBox.isChecked());

        editor.apply();

        Log.d("HomeFragment", "Selected Date: " + selectedDate);
        Log.d("HomeFragment", "Checkboxes - Morning: " + morningCheckBox.isChecked() +
                ", Noon: " + noonCheckBox.isChecked() + ", Evening: " + eveningCheckBox.isChecked());

        // すべてのチェックボックスがチェックされている場合
        if (morningCheckBox.isChecked() && noonCheckBox.isChecked() && eveningCheckBox.isChecked()) {
            // デコレーターをクリアして新しいデコレーターを設定
            calendarView.removeDecorators();

            // デコレーターを追加
            calendarView.addDecorator(new DotDecorator(selectedDate, Color.RED));

            // カレンダーを強制的に再描画
            calendarView.invalidateDecorators();
        } else {
            // デコレーターを削除したことをログに出力
            Log.d("HomeFragment", "Removing all decorators");

            // チェックが外れた場合、デコレーターをクリア
            calendarView.removeDecorators();
            calendarView.invalidateDecorators();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
