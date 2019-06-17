package com.example.se_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Create a new Activity to edit moments..
 */
public class MomentEditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputMoments;
    private final MomentEditActivity momentEdit = this;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.moments_send_activity);
        inputMoments = (EditText) findViewById(R.id.input_moments);
        findViewById(R.id.send_moments).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
    }








    public void onClick(View view) {
        if( view.getId() == R.id.send_moments ){
            AppData.getInstance().getMe().sendMoments(inputMoments.getText().toString());
        }
        finish();
    }
}
