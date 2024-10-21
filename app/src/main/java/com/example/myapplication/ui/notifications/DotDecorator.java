package com.example.myapplication.ui.notifications;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class DotDecorator implements DayViewDecorator {

    private final CalendarDay date; // デコレーションする日付
    private final int color; // ドットの色

    public DotDecorator(CalendarDay date, int color) {
        this.date = date;
        this.color = color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date);  // 選択した日付にのみデコレーションを適用
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(12, color));

        // カスタムレイアウトを使用している場合、特定の変更が必要な場合にデコレーションする
//        view.addSpan(new DayViewSpan() {
//            @Override
//            public void applyToDayView(View view) {
//                // 日付 (TextView) とドット (ImageView) を取得
//                TextView dayLabel = view.findViewById(R.id.dayLabel);
//                ImageView dayIcon = view.findViewById(R.id.dayIcon);
//
//                // 日付のスタイルをカスタマイズ
//                dayLabel.setTextColor(Color.BLACK);  // ここで色を設定
//                dayLabel.setTextSize(16);  // テキストサイズを調整
//
//                // ドットの色やスタイルをカスタマイズ
//                if (dayIcon != null) {
//                    dayIcon.setImageDrawable(DrawableUtils.getCircleDrawableWithColor(context, color)); // ドットに色を付ける
//                    dayIcon.setVisibility(View.VISIBLE); // ドットを表示する
//                }
//            }
//        });
    }
}
