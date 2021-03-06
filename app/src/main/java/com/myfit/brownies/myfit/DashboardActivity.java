package com.myfit.brownies.myfit;

/**
 * Created by essamyousry on 10/28/17.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

abstract class DashBoardActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible) {
        ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
        View inflated = stub.inflate();

        TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
        txtTitle.setText(title);

        /**
        Button btnHome = (Button) inflated.findViewById(R.id.btnHome);
        if (!btnHomeVisible)
            btnHome.setVisibility(View.INVISIBLE);

        Button btnFeedback = (Button) inflated.findViewById(R.id.btnFeedback);
        if (!btnFeedbackVisible)
            btnFeedback.setVisibility(View.INVISIBLE);

         */

    }

    /**
     * Home button click handler
     *
     * @param v
     */
    /**
    public void btnHomeClick(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    /**
     * Feedback button click handler
     *
     * @param v
     */
    /**
    public void btnFeedbackClick(View v) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
     */
}

