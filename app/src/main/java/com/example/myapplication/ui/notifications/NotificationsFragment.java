package com.example.myapplication.ui.notifications;

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
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private MaterialCalendarView calendarView;
//    private com.applandeo.materialcalendarview.CalendarView calendarView;
    private CheckBox morningCheckBox, noonCheckBox, eveningCheckBox;
    private CalendarDay selectedDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
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

        // チェックボックスのリスナーを設定
        morningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());
        noonCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());
        eveningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCalendarMarker());

        // カレンダーの日付選択リスナーを設定
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            selectedDate = date; // 選択された日付を保持
            updateCalendarMarker(); // デコレーターを更新
        });

        // カレンダーの日付選択リスナーを設定
//        calendarView.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
//            @Override
//            public void onClick(@NonNull CalendarDay calendarDay) {
//                selectedDate = calendarDay; // 選択された日付を保持
//                updateCalendarMarker(); // デコレーターを更新
//            }

//            @Override
//            public void onDayClick(CalendarDay calendarDay) {
//                selectedDate = calendarDay; // 選択された日付を保持
//                updateCalendarMarker(); // デコレーターを更新
////                Calendar clickedDayCalendar = calendarDay.getCalendar();
//            }
//        });

//        calendarView.setOnDateChangedListener((widget, date, selected) -> {
//            selectedDate = date; // 選択された日付を保持
//            updateCalendarMarker(); // デコレーターを更新
//        });
    }

    private void updateCalendarMarker() {
        if (selectedDate == null) return;

        Log.d("NotificationsFragment", "Selected Date: " + selectedDate);
        Log.d("NotificationsFragment", "Checkboxes - Morning: " + morningCheckBox.isChecked() +
                ", Noon: " + noonCheckBox.isChecked() + ", Evening: " + eveningCheckBox.isChecked());

        // すべてのチェックボックスがチェックされている場合
        if (morningCheckBox.isChecked() && noonCheckBox.isChecked() && eveningCheckBox.isChecked()) {
            // デコレーターをクリアして新しいデコレーターを設定
            calendarView.removeDecorators();

//            calendarView(R.layout.custom_calendar_day_layout);

            // デコレーターを追加
            calendarView.addDecorator(new DotDecorator(selectedDate, Color.RED));

            // カレンダーを強制的に再描画
            calendarView.invalidateDecorators();
        } else {
            // デコレーターを削除したことをログに出力
            Log.d("NotificationsFragment", "Removing all decorators");

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
