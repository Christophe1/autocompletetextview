import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.chris.myapplication.R;

public class MainActivity extends Activity {

    private CustomAutoCompleteView autoComplete;

    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteAdapter.setNotifyOnChange(true); // This is so I don't have to manually sync whenever changed
        autoComplete = (CustomAutoCompleteView) findViewById(R.id.autoCompleteCountry);
        autoComplete.setHint("Country");
        autoComplete.setThreshold(3);
        autoComplete.setAdapter(autoCompleteAdapter);

        autoComplete.addTextChangedListener(textChecker);

    }
}