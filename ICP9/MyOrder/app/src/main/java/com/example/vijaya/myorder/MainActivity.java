package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int COFFEE_PRICE = 5;
    final int WHIPPED_CREAM_PRICE = 1;
    final int CHOCOLATE_PRICE = 2;
    int quantity = 2;
    private Button b1;
    private Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder(v);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderSummary(v);
            }
        });
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get input from user
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();
        // Check if whipped cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();
        // Check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();
        // Calculate and store the total price
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        // Create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate, totalPrice);

        String[] TO = {"test_sender@gmail.com"};
        String[] CC = {"test_receiver@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
        sendEmail();
    }

    private String boolToString(boolean bool){
        return bool?(getString(R.string.yes)):(getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasWhippedCream, boolean hasChocolate, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name,userInputName) +"\n"+
                getString(R.string.order_summary_whipped_cream,boolToString(hasWhippedCream))+"\n"+
                getString(R.string.order_summary_chocolate,boolToString(hasChocolate)) +"\n"+
                getString(R.string.order_summary_quantity,quantity)+"\n"+
                getString(R.string.order_summary_total_price,price) +"\n"+
                getString(R.string.thank_you);
        return orderSummaryMessage;

    }
    protected void sendEmail() {

    }

    /**
     * Method to calculate the total price
     */
    private float calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = COFFEE_PRICE;
        if (hasWhippedCream) {
            basePrice += WHIPPED_CREAM_PRICE;
        }
        if (hasChocolate) {
            basePrice += CHOCOLATE_PRICE;
        }

        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
        }
    }

    public void orderSummary(View view) {
        // Get input from user
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();
        // Check if whipped cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();
        // Check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();
        // Calculate and store the total price
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        // Create and store the order summary
        String orderSummary = createOrderSummary(userInputName, hasWhippedCream, hasChocolate, totalPrice);

        Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
        intent.putExtra("orderSummary", orderSummary);
        startActivity(intent);
    }
}