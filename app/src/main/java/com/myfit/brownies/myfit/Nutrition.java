package com.myfit.brownies.myfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Nutrition extends DashBoardActivity {

        Button button;

        @Override
        /**
         * Called when the activity is first created.
         * This is where you should do all of your normal static set up:
         * create views, bind data to lists, etc. This method also provides
         * you with a Bundle containing the activity's previously frozen state,
         * if there was one. Always followed by onStart().
         */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.progress_layout);

            button = (Button) findViewById(R.id.AddFood);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Nutrition.this, FoodDiary.class);
                    startActivity(intent);
                }
            });

        }

        /**
         * Called when the activity is becoming visible to the user.
         * Followed by onResume() if the activity comes to the foreground,
         * or onStop() if it becomes hidden.
         */
        public void onStart() {
            super.onStart();

            TextView Calories = (TextView) findViewById(R.id.CalNumber);
            TextView Goal = (TextView) findViewById(R.id.GoalText);
            TextView Info = (TextView) findViewById(R.id.UserInfo);
            ProgressBar CalProg = (ProgressBar) findViewById(R.id.CalProgress) ;
            ProgressBar CarbProgress = (ProgressBar) findViewById(R.id.CarbProgress);
            ProgressBar ProteinProgress = (ProgressBar) findViewById(R.id.ProteinProgress);
            ProgressBar FatsProgress = (ProgressBar) findViewById(R.id.FatProgress);

            Database helper = new Database(this);

            String username = getIntent().getStringExtra("username");

            int BMR = helper.getBMR(username);

            String GoalInfo = helper.getGoal(username);
            helper.close();


            String showGoal = "If you want to " + GoalInfo + " you must consume " + BMR + " calories daily in order to gain one pound per week" + "\n\n"
                    + "Carbs: 65% of daily calorie intake" + "\n"
                    + "Protein: 20% of daily calorie intake" + "\n"
                    + "Fats: 15% of dailiy calorie intake";

            Info.setText(showGoal);

            CarbProgress.setMax(CalculateCarbs(BMR));
            ProteinProgress.setMax(CalculateCarbs(BMR));
            FatsProgress.setMax(CalculateFat(BMR));

            CarbProgress.setProgress(60);
            ProteinProgress.setProgress(60);
            FatsProgress.setProgress(60);


            int progress = 2000;


            CalProg.setMax(BMR);
            CalProg.setProgress(progress);
            Calories.setText(Integer.toString(progress));
            Goal.setText(Integer.toString(BMR));

            //Calories.setTextColor(Color.rgb(51, 204, 51));
        }

        public int CalculateCarbs (int number){
            double carbs = (number * 0.65);
            return (int) carbs;
        }

        public int CalculateProtein (int number){
            double protein = (number * 0.20);
            return (int) protein;
        }

        public int CalculateFat (int number){
            double fats = (number * 0.15);
            return (int) fats;
        }



        /**
         * Called after your activity has been stopped, prior to it
         * being started again. Always followed by onStart()
         */
        public void onRestart() {
            super.onRestart();
        }

        /**
         * Called when the activity will start interacting with the user.
         * At this point your activity is at the top of the activity stack,
         * with user input going to it. Always followed by onPause().
         */
        public void onResume() {
            super.onResume();
        }

        /**
         * Called as part of the activity lifecycle when an activity is
         * going into the background, but has not (yet) been killed.
         * The counterpart to onResume(). When activity B is launched in
         * front of activity A, this callback will be invoked on A.
         * B will not be created until A's onPause() returns, so
         * be sure to not do anything lengthy here.
         */
        public void onPause() {
            super.onPause();
        }

        /**
         * Called when you are no longer visible to the user.
         * You will next receive either onRestart(), onDestroy(), or nothing,
         * depending on later user activity.
         * <p>
         * Note that this method may never be called, in low memory situations
         * where the system does not have enough memory to keep your
         * activity's process running after its onPause()
         * method is called.
         */
        public void onStop() {
            super.onStop();
        }

        /**
         * The final call you receive before your activity is destroyed.
         * This can happen either because the activity is
         * finishing (someone called finish() on it, or because the
         * system is temporarily destroying this instance of the activity
         * to save space. You can distinguish between these two scenarios
         * with the isFinishing() method.
         */
        public void onDestroy() {
            super.onDestroy();
        }


    }



