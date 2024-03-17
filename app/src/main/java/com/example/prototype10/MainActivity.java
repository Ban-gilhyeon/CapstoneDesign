package com.example.prototype10;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Home_Main home;
    private Exercise_Main exercise;
    private Record_Main record;
    private Mypage_Main mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_homeBtn) {
                    setFrag(0);
                } else if (itemId == R.id.action_exerciseBtn) {
                    setFrag(1);
                } else if (itemId == R.id.action_recordBtn) {
                    setFrag(2);
                } else if (itemId == R.id.action_mypageBtn) {
                    setFrag(3);
                }
                return true;
            }
        });
        home = new Home_Main();
        exercise = new Exercise_Main();
        record = new Record_Main();
        mypage = new Mypage_Main();
        setFrag(0); // 첫 프래그먼트 화면 무엇으로 지정해줄 것인지 선택.


    }
    //프래그 먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction(); //실제적인 프래그먼트 교체를 이룰때 프래그먼트를 가져와서 트랙잭션
        switch (n) {
            case 0:
                ft.replace(R.id.main_fram,home);
                ft.commit(); //저장
                break;
            case 1:
                ft.replace(R.id.main_fram,exercise);
                ft.commit(); //저장
                break;
            case 2:
                ft.replace(R.id.main_fram,record);
                ft.commit(); //저장
                break;
            case 3:
                ft.replace(R.id.main_fram,mypage);
                ft.commit(); //저장
                break;
        }
    }


}