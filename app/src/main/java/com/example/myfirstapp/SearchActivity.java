package com.example.myfirstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat; import java.text.SimpleDateFormat;
import java.util.Calendar; import java.util.Date;
import java.util.Locale;
public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date now = calendar.getTime();
            String todayStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now);
            Date today = format.parse((String) todayStr);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tomorrowStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format( calendar.getTime());
            Date tomorrow = format.parse((String) tomorrowStr);
            ((EditText) findViewById(R.id.etFromDateTime)).setText(new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(today));
            ((EditText) findViewById(R.id.etToDateTime)).setText(new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(tomorrow));

            ((EditText) findViewById(R.id.etKeywords)).setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        ((Button) findViewById(R.id.go)).performClick();
                        return true;
                    }
                    return false;
                }
            });

        } catch (Exception ex) { }
    }
    public void cancel(final View v) {
        finish();
    }
    public void go(final View v) {
        Intent i = new Intent();
        EditText from = (EditText) findViewById(R.id.etFromDateTime);
        EditText to = (EditText) findViewById(R.id.etToDateTime);
        EditText keywords = (EditText) findViewById(R.id.etKeywords);
        EditText location = (EditText) findViewById(R.id.etLocation);
        i.putExtra("STARTTIMESTAMP", from.getText() != null ? from.getText().toString() : "");
        i.putExtra("ENDTIMESTAMP", to.getText() != null ? to.getText().toString() : "");
        i.putExtra("KEYWORDS", keywords.getText() != null ? keywords.getText().toString() : "");
        i.putExtra("LOCATION", location.getText() != null ? location.getText().toString() : "");
        setResult(RESULT_OK, i);
        finish();
    }
}