    package com.example.prototype10;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.ScrollView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;

    import java.util.ArrayList;
    import java.util.List;

    public class Exercise_Main extends Fragment {

        private ScrollView scrollView;
        private LinearLayout routineListLayout;
        private Button addRoutineButton;
        private Button addExerciseButton;

        private List<String> routineNames; // 루틴 이름을 저장할 리스트
        private SharedPreferences sharedPreferences;

        public Exercise_Main() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.exercise_main, container, false);

            scrollView = view.findViewById(R.id.scrollView);
            routineListLayout = view.findViewById(R.id.routineListLayout);
            addRoutineButton = view.findViewById(R.id.addRoutineButton);

            routineNames = new ArrayList<>(); // 루틴 이름을 저장할 리스트 초기화

            sharedPreferences = requireContext().getSharedPreferences("RoutineData", Context.MODE_PRIVATE);

            addRoutineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //오류 확인을 위한 log
                    Log.d("Exercise_Main", "onCreateView() called");
                    showDataInputPage();
                }
            });

            loadRoutineNames();

            return view;
        }
        private void updateButton(int buttonIndex, String buttonText) {
            Button buttonToUpdate = (Button) routineListLayout.getChildAt(buttonIndex);
            buttonToUpdate.setText(buttonText);
        }

        // 루틴 추가 다이얼로그를 표시하는 메소드
        private void showDataInputPage() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("루틴 추가");
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_routine, null);
            builder.setView(dialogView);

            final EditText routineNameEditText = dialogView.findViewById(R.id.routineNameEditText);

            builder.setPositiveButton("저장하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String routineName = routineNameEditText.getText().toString();
                    addRoutineButton(routineName);
                    saveRoutineNames();
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        // 루틴 버튼을 추가하는 메소드
        private void addRoutineButton(String buttonText) {
            Button newButton = new Button(requireContext());
            newButton.setText(buttonText);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 8, 0, 8);
            newButton.setLayoutParams(layoutParams);

            final int buttonIndex = routineNames.size(); // 버튼의 인덱스를 저장

            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showButtonOptions(buttonIndex);
                }
            });

            routineListLayout.addView(newButton);
            routineNames.add(buttonText);
            saveRoutineNames();
        }

        // 루틴 버튼 옵션을 표시하는 메소드
        private void showButtonOptions(final int buttonIndex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("버튼 옵션");
            builder.setItems(new CharSequence[]{"운동시작","운동추가", "수정", "삭제"}, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 1:{
                            //운동시작
                            /*Intent intent = new Intent(requireContext(), doExercise_main.class);
                            startActivity(intent);*/
                            break;
                        }
                        case 2: // 운동추가
                            showAddExerciseDialog(buttonIndex);
                            break;
                        case 3:  // 수정
                            showEditRoutineDialog(buttonIndex);
                            break;
                        case 4:  // 삭제
                            removeButton(buttonIndex);
                            break;
                    }
                }
            });
            builder.show();
        }

        // 루틴 수정 다이얼로그를 표시하는 메소드
        private void showEditRoutineDialog(final int buttonIndex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("루틴 수정");
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_routine, null);
            builder.setView(dialogView);

            final EditText routineNameEditText = dialogView.findViewById(R.id.routineNameEditText);
            routineNameEditText.setText(routineNames.get(buttonIndex));

            builder.setPositiveButton("저장하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String updatedRoutineName = routineNameEditText.getText().toString();
                    updateButton(buttonIndex, updatedRoutineName);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }


        // 운동 추가 다이얼로그를 표시하는 메소드
        private void showAddExerciseDialog(final int buttonIndex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("운동 추가");
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_exercise, null);
            builder.setView(dialogView);

            final EditText exerciseNameEditText = dialogView.findViewById(R.id.exerciseNameEditText);
            final EditText setCountEditText = dialogView.findViewById(R.id.setCountTextView);

            builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String exerciseName = exerciseNameEditText.getText().toString();
                    String setCountStr = setCountEditText.getText().toString();
                    int setCount = Integer.parseInt(setCountStr);
                    addExerciseButton(buttonIndex, exerciseName, setCount);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            // 추가된 부분: 다이얼로그를 닫을 때마다 저장
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    saveRoutineNames();
                }
            });
        }

        // 운동 버튼과 세트 수를 추가하는 메소드
        private void addExerciseButton(final int buttonIndex, String exerciseName, int setCount) {
            LinearLayout exerciseLayout = new LinearLayout(requireContext());
            exerciseLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 8, 0, 8);
            exerciseLayout.setLayoutParams(layoutParams);

            TextView exerciseTextView = new TextView(requireContext());
            exerciseTextView.setText(exerciseName);
            exerciseLayout.addView(exerciseTextView);

            // "Set Weight and Reps" 텍스트뷰 추가
            TextView weightRepsTextView = new TextView(requireContext());
            weightRepsTextView.setText("Set Weight and Reps");
            exerciseLayout.addView(weightRepsTextView);


            for (int i = 1; i <= setCount; i++) {
                LinearLayout setLayout = new LinearLayout(requireContext());
                setLayout.setOrientation(LinearLayout.HORIZONTAL);
                setLayout.setLayoutParams(layoutParams);

                TextView setTextView = new TextView(requireContext());
                setTextView.setText(i + "세트");
                setLayout.addView(setTextView);

                EditText weightEditText = new EditText(requireContext());
                weightEditText.setHint("무게");
                setLayout.addView(weightEditText);

                EditText repetitionEditText = new EditText(requireContext());
                repetitionEditText.setHint("반복횟수");
                setLayout.addView(repetitionEditText);

                exerciseLayout.addView(setLayout);
            }

            Button exerciseButton = new Button(requireContext());
            exerciseButton.setText(exerciseName);
            exerciseButton.setLayoutParams(layoutParams);
            exerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showExerciseOptions(buttonIndex, exerciseName);
                }
            });
            //운동 버튼을 추가하기 전 OnClickListener 설정
            exerciseLayout.addView(exerciseButton);
            routineListLayout.addView(exerciseButton);
            routineListLayout.addView(exerciseLayout);
           // saveRoutineNames(); // 수정된 부분: saveRoutineNames 호출 위치 변경
        }

        // 운동 버튼 옵션을 표시하는 메소드
        private void showExerciseOptions(final int buttonIndex, final String exerciseName) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("운동 옵션");
            builder.setItems(new CharSequence[]{"수정", "삭제"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // 수정
                            showEditExerciseDialog(buttonIndex, exerciseName);
                            break;
                        case 1: // 삭제
                            removeExercise(buttonIndex, exerciseName);
                            break;
                    }
                }
            });
            builder.show();
        }

        // 운동 수정 다이얼로그를 표시하는 메소드
        private void showEditExerciseDialog(final int buttonIndex, final String exerciseName) {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("운동 수정");
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.edit_exercise, null);
            builder.setView(dialogView);

            final EditText exerciseNameEditText = dialogView.findViewById(R.id.exerciseNameEditText);
            exerciseNameEditText.setText(exerciseName);

            builder.setPositiveButton("저장하기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String updatedExerciseName = exerciseNameEditText.getText().toString();
                    updateExerciseButton(buttonIndex, exerciseName, updatedExerciseName);
                    saveRoutineNames();
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        // 운동 버튼을 수정하는 메소드
        private void updateExerciseButton(int buttonIndex, String oldExerciseName, String updatedExerciseName) {
            Button buttonToUpdate = (Button) routineListLayout.getChildAt(buttonIndex * 2 - 1);
            buttonToUpdate.setText(updatedExerciseName);

            for (int i = 0; i < routineListLayout.getChildCount(); i++) {
                View view = routineListLayout.getChildAt(i);
                if (view instanceof LinearLayout) {
                    LinearLayout exerciseLayout = (LinearLayout) view;
                    TextView exerciseTextView = (TextView) exerciseLayout.getChildAt(0);
                    if (exerciseTextView.getText().toString().equals(oldExerciseName)) {
                        exerciseTextView.setText(updatedExerciseName);
                        break;
                    }
                }
            }

            saveRoutineNames();
        }

        // 운동 버튼과 세트 수를 삭제하는 메소드
        private void removeExercise(int buttonIndex, String exerciseName) {
            Button buttonToRemove = (Button) routineListLayout.getChildAt(buttonIndex * 2 - 1);
            routineListLayout.removeView(buttonToRemove);

            for (int i = 0; i < routineListLayout.getChildCount(); i++) {
                View view = routineListLayout.getChildAt(i);
                if (view instanceof LinearLayout) {
                    LinearLayout exerciseLayout = (LinearLayout) view;
                    TextView exerciseTextView = (TextView) exerciseLayout.getChildAt(0);
                    if (exerciseTextView.getText().toString().equals(exerciseName)) {
                        routineListLayout.removeView(exerciseLayout);
                        break;
                    }
                }
            }

            saveRoutineNames();
        }

        // 루틴 버튼을 삭제하는 메소드
        private void removeButton(int buttonIndex) {
            Button buttonToRemove = (Button) routineListLayout.getChildAt(buttonIndex * 2 - 2);
            routineListLayout.removeView(buttonToRemove);

            for (int i = routineListLayout.getChildCount() - 1; i >= 0; i--) {
                View view = routineListLayout.getChildAt(i);
                if (view instanceof Button) {
                    Button exerciseButton = (Button) view;
                    routineListLayout.removeView(exerciseButton);
                } else if (view instanceof LinearLayout) {
                    LinearLayout exerciseLayout = (LinearLayout) view;
                    routineListLayout.removeView(exerciseLayout);
                } else {
                    break;
                }
            }

            routineNames.remove(buttonIndex);
            saveRoutineNames();
        }

        // 루틴 이름 목록을 SharedPreferences에 저장하는 메소드
        private void saveRoutineNames() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("routineCount", routineNames.size());
            for (int i = 0; i < routineNames.size(); i++) {
                String routineName = routineNames.get(i);
                editor.putString("routine" + i, routineName);
            }
            // 수정된 부분: apply 대신 commit 메소드를 사용합니다.
            editor.commit();
        }

        // SharedPreferences에서 루틴 이름 목록을 로드하는 메소드
        private void loadRoutineNames() {
            int routineCount = sharedPreferences.getInt("routineCount", 0);
            for (int i = 0; i < routineCount; i++) {
                String routineName = sharedPreferences.getString("routine" + i, "");
                if (!routineName.isEmpty()) {
                    addRoutineButton(routineName);
                }
            }
        }
    }
