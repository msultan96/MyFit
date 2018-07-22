package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 11/14/17.
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FoodDiary extends DashBoardActivity {

    TextInputEditText textInputEditTextFood;
    TextInputLayout textInputLayoutFood;
    TextView txtSpeechInput;
    Button btn_Check;
    ImageButton btnSpeak;
    TextView ViewDay;
    Calendar calendar;
    int capacity = 7;
    ChainedHashTable<Food> FoodArray= new ChainedHashTable<>(capacity);

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_diary);

        textInputLayoutFood = (TextInputLayout) findViewById(R.id.textInputLayoutFood);
        textInputEditTextFood = (TextInputEditText) findViewById(R.id.textInputEditTextFood);

        //txtSpeechInput = (TextView) findViewById(R.id.SpeechInput);
        ViewDay = (TextView) findViewById(R.id.ViewDay);
        btnSpeak = (ImageButton) findViewById(R.id.Speak);
        btn_Check = (Button) findViewById(R.id.btn_CheckFood);
        //btnAddFood = (Button) findViewById(R.id.Add);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        btn_Check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SendInfo();
            }
        });

        DisplayDiary();
    }

    public void SendInfo() {

        Intent searchWord = new Intent(this, API.class);
        searchWord.putExtra("Search", textInputEditTextFood.getText().toString().trim());
        startActivity(searchWord);

    }


    public void DisplayDiary(){
        String Day = getCurrentDay();
        ViewDay.setText(Day);


    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInputEditTextFood.setText(result.get(0));

                }
                break;
            }

        }

    }



    public String getCurrentDay(){

        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return daysArray[day];

    }
    /*
    public String getDay (int day){
        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};
        return daysArray[day];
    }
    */

    public int getDay(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}