/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.content.Intent.EXTRA_HTML_TEXT;
import static android.content.Intent.EXTRA_TEXT;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        // cannot order more than 100 coffees.
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */

    public void decrement(View view) {
        //cannot order less than one coffee
        if (quantity == 1) {

            //adding toast message to say cannot order less than 1 coffee
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();

            return;
        }
        quantity = quantity - 1;


        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Figure out if user wants Whipped Cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        //Figure out if user wants chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        intent.putExtra(EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

         //displayMessage(priceMessage);
    }



    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the use wants whipped cream
     * @param hasChocolate    is whether or not the user wants chocolate
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;

    }


    /**
     * creates summary of order
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);

 //   }





}

