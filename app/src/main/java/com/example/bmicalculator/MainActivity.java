package com.example.bmicalculator;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput, heightInput;
    private TextView bmiResult, bmiCategory;
    public Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightInput = findViewById(R.id.etWeight);
        heightInput = findViewById(R.id.etHeight);
        bmiResult = findViewById(R.id.tvBmiResult);
        bmiCategory = findViewById(R.id.tvBmiCategory);
        calculateButton = findViewById(R.id.btnCalculate);


        weightInput.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        heightInput.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});

        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr) / 100;

            double bmi = weight / (height * height);
            DecimalFormat df = new DecimalFormat("#,###.##");
            bmiResult.setText(df.format(bmi));

            String category;
            int backgroundColor;

            if (bmi < 16) {
                category = getString(R.string.severe);
                backgroundColor = getColor(R.color.severeThinness);
            } else if (bmi < 17) {
                category = getString(R.string.moderate);
                backgroundColor = getColor(R.color.moderateThinness);
            } else if (bmi < 18.5) {
                category = getString(R.string.mild);
                backgroundColor = getColor(R.color.mildThinness);
            }
            else if (bmi < 23) {
                category = getString(R.string.normal);
                backgroundColor = getColor(R.color.normal);
            }  else if (bmi < 25) {
                category = getString(R.string.overweight);
                backgroundColor = getColor(R.color.overweight);
            } else if (bmi < 30) {
                category = getString(R.string.obese1);
                backgroundColor = getColor(R.color.obeseClass1);
            }
            else if (bmi < 40) {
                category = getString(R.string.obese2);
                backgroundColor = getColor(R.color.obeseClass2);
            } else {
                category = getString(R.string.obese3);
                backgroundColor = getColor(R.color.obeseClass3);
            }
            bmiCategory.setText(category);
            bmiCategory.setBackgroundColor(backgroundColor);
        }
    }

    class DecimalDigitsInputFilter implements InputFilter {
        private Pattern mPattern;

        DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }
}