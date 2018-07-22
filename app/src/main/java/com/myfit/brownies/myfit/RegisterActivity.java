package com.myfit.brownies.myfit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutWeight;
    private TextInputLayout textInputLayoutActivity;
    private TextInputLayout textInputLayoutHeight;
    private TextInputLayout textInputLayoutAge;
    private TextInputLayout textInputLayoutSex;
    private TextInputLayout textInputLayoutGoal;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextWeight;
    private TextInputEditText textInputEditTextActivity;
    private TextInputEditText textInputEditTextHeight;
    private TextInputEditText textInputEditTextAge;
    private TextInputEditText textInputEditTextSex;
    private TextInputEditText textInputEditTextGoal;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private Database databaseHelper;
    private User user;

    private String DB_URL = "jdbc:mysql://192.168.1.3:3306/userlogin";
    private String USER = "tanya";
    private String PASS = "12345";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);
        textInputLayoutWeight = findViewById(R.id.textInputLayoutWeight);
        textInputLayoutActivity = findViewById(R.id.textInputLayoutActivity);
        textInputLayoutHeight = findViewById(R.id.textInputLayoutHeight);
        textInputLayoutAge =  findViewById(R.id.textInputLayoutAge);
        textInputLayoutSex = findViewById(R.id.textInputLayoutSex);
        textInputLayoutGoal = findViewById(R.id.textInputLayoutGoal);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextWeight = findViewById(R.id.textInputEditTextWeight);
        textInputEditTextActivity = findViewById(R.id.textInputEditTextActivity);
        textInputEditTextHeight = findViewById(R.id.textInputEditTextHeight);
        textInputEditTextAge = findViewById(R.id.textInputEditTextAge);
        textInputEditTextSex = findViewById(R.id.textInputEditTextSex);
        textInputEditTextGoal = findViewById(R.id.textInputEditTextGoal);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new Database(activity);
        user = new User();

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private boolean postDataToSQLite() {

        boolean PostSuccessful = true;

        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return false;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return false;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            user.setWeight(Integer.parseInt(textInputEditTextWeight.getText().toString().trim()));
            user.setActivity(textInputEditTextActivity.getText().toString().trim());
            user.setHeight(Integer.parseInt(textInputEditTextHeight.getText().toString().trim()));
            user.setAge(Integer.parseInt(textInputEditTextAge.getText().toString().trim()));
            user.setSex(textInputEditTextSex.getText().toString().trim());
            user.setGoal(textInputEditTextGoal.getText().toString().trim());
            user.setBMR(user.getBMR(Integer.parseInt(textInputEditTextWeight.getText().toString().trim()), Integer.parseInt(textInputEditTextHeight.getText().toString().trim()), Integer.parseInt(textInputEditTextAge.getText().toString().trim()), textInputEditTextSex.getText().toString().trim(), textInputEditTextActivity.getText().toString().trim(), textInputEditTextGoal.getText().toString().trim()));

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();



        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            return false;
        }

        return PostSuccessful;

    }

//    private void checkLevelOfCredit(String email, String password){
//        class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
//            @Override
//            protected String doInBackground(String... params) {
//                String paramEmail = params[0].trim();
//                String paramPassword = params[1].trim();
//                HttpClient httpClient = new DefaultHttpClient();
//                //String URL_MYSREVER = Constant.serverUrl + "check_number.php?&identification=" + paramUseriden;
//                String URL_MYSREVER = "http://192.168.1.219:8080/test/mysqlplug?aa=1";
//                HttpGet httpGet = new HttpGet(URL_MYSREVER);
//                try {
//                    HttpResponse httpResponse = httpClient.execute(httpGet);
//                    InputStream inputStream = httpResponse.getEntity().getContent();
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String bufferedStrChunk;
//                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
//                        stringBuilder.append(bufferedStrChunk);
//                    }
//                    return stringBuilder.toString();
//                } catch (Exception e) {e.printStackTrace();}
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String resultJSON) {
//                try {
//                    JSONObject jsonObject = new JSONObject(resultJSON);
//                    int result = jsonObject.getInt("result");
//                    if(result == 0){
//                        if (postDataToSQLite()) {
//                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Faild", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
//        httpGetAsyncTask.execute(email, password);
//    }

    private boolean checkBlankingState(){
        boolean bFlage = true;
        if(textInputEditTextName.getText().length() == 0 ||
                textInputEditTextEmail.getText().length() == 0 ||
                textInputEditTextPassword.getText().length() == 0 ||
                textInputEditTextConfirmPassword.getText().length() == 0 ||
                textInputEditTextWeight.getText().length() == 0 ||
                textInputEditTextActivity.getText().length() == 0 ||
                textInputEditTextHeight.getText().length() == 0 ||
                textInputEditTextAge.getText().length() == 0 ||
                textInputEditTextSex.getText().length() == 0 ||
                textInputEditTextGoal.getText().length() == 0 )
            bFlage = false;
        return bFlage;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                if (checkBlankingState()) {
                    InsertData();
                }
                break;

            case R.id.appCompatTextViewLoginLink:

                Intent b = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(b);
                break;
            }
    }

    private void InsertData() {
        InsertInfo task = new InsertInfo();
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertInfo extends AsyncTask<String, String, String>{
        String paramUsername = textInputEditTextName.getText().toString().trim();
        String paramEmail = textInputEditTextEmail.getText().toString().trim();
        String paramPassword = textInputEditTextPassword.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            handler.sendEmptyMessage(2);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                if(conn == null){

                } else {
                    String query = "INSERT INTO user (username, useremail, password) VALUES ('" + paramUsername + "','" + paramEmail + "','" + paramPassword + "')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    handler.sendEmptyMessage(1);
                }
            } catch (Exception e){
                handler.sendEmptyMessage(0);
            }
            return null;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(RegisterActivity.this, "DB Connecting Faild, Please check Network state", Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    if (postDataToSQLite()) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "Please Wait Inserting Data", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }


}
