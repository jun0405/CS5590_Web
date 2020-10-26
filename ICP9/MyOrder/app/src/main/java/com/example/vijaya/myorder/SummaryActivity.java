package com.example.vijaya.myorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    TextView summaryText;
    Button OrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        summaryText = findViewById(R.id.summary_textView);
        summaryText.setText(Html.fromHtml("<u>Order Summary</u><br/><br/>"));
        if (getIntent() != null) {
            summaryText.append(getIntent().getStringExtra("orderSummary"));
        } else {
            summaryText.setText("No order found.");
        }
        summaryText.append(Html.fromHtml("<br/>"));
        summaryText.setVisibility(View.VISIBLE);
        OrderButton = findViewById(R.id.summary_button);

        OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_home();
            }
        });
    }
    public void go_home() {
        Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
