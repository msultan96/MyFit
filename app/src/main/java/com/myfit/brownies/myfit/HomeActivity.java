package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 10/28/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends DashBoardActivity {
    /** Called when the activity is first created. */

    SessionManager session;
    Button btn_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        setHeader(getString(R.string.text_title), false, true);
    }

    /**
     * Button click handler on Main activity
     * @param v
     */
    public void onButtonClicker(View v)
    {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_progress:
                intent = new Intent(this, Nutrition.class);
                startActivity(intent);
                break;

            case R.id.btn_qr:
                intent = new Intent(this, QRActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_diary:
                intent = new Intent(this, FoodDiary.class);
                startActivity(intent);
                break;

            case R.id.btn_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            /*
            case R.id.main_btn_gingerbread:
                intent = new Intent(this, Activity_Gingerbread.class);
                startActivity(intent);
                break;

            case R.id.main_btn_honeycomb:
                intent = new Intent(this, Activity_Honeycomb.class);
                startActivity(intent);
                break;

            case R.id.main_btn_ics:
                intent = new Intent(this, Activity_ICS.class);
                startActivity(intent);
                break;

            case R.id.main_btn_jellybean:
                intent = new Intent(this, Activity_JellyBean.class);
                startActivity(intent);
                break;
                **/
            default:
                break;

        }
    }
}