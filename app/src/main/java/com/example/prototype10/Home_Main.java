package com.example.prototype10;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class Home_Main extends Fragment {
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_main, container, false);

        View bottomSheetView = view.findViewById(R.id.persistent_bottom_sheet);
        RadarChart radarChart = bottomSheetView.findViewById(R.id.radarChart);

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setAxisMinimum(0f); // 최소값 0으로 설정
        yAxis.setAxisMaximum(80f); // 최대값 80으로 설정해야 그래프가 100까지 표현됨...
        yAxis.setSpaceTop(10f);

        Exercise_Main exerciseMain = new Exercise_Main();
//        int backValue = exerciseMain.back;
//        int shoulderValue = exerciseMain.shoulder;
//        int absValue = exerciseMain.abs;
//        int legValue = exerciseMain.leg;
//        int armValue = exerciseMain.arm;
//        int chestValue = exerciseMain.chest;

        //항목 별 데이터 값 입력
        List<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(10));    //등
        entries.add(new RadarEntry(10));    //어깨
        entries.add(new RadarEntry(10));    //복근
        entries.add(new RadarEntry(10));    //하체
        entries.add(new RadarEntry(10));    //팔
        entries.add(new RadarEntry(10));    //가슴

        //데이터 최대값 100이 넘어갈 경우 100으로 고정
        for (RadarEntry entry : entries) {
            if (entry.getValue() > 100) {
                entry.setY(100);
            }
        }

        RadarDataSet dataSet = new RadarDataSet(entries, "Data Set");
        dataSet.setColor(Color.BLUE);   //그래프 외곽선 색상
        dataSet.setFillColor(Color.BLUE);   //그래프 내부 색상
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(100);  //그래프 투명도
        dataSet.setLineWidth(1f);   //그래프 외곽선 두께
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        RadarData data = new RadarData(dataSets);
        data.setValueTextSize(12f); //항목 글자 크기
        data.setDrawValues(false);
        String[] labels =  {"등", "어깨", "복근", "하체", "팔", "가슴"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextSize(15);
        radarChart.setData(data);
        radarChart.getDescription().setEnabled(false);
        radarChart.animateXY(500, 500);
        radarChart.invalidate();

        // BottomSheetBehavior 설정
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);

        // 슬라이딩 동작
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(400); // 바텀 시트 최소 높이로 설정

        return view;
    }
}
