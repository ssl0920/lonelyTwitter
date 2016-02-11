package ca.ualberta.cs.lonelytwitter;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sajediba on 2/8/16.
 */
public class IntentReaderActivityTest extends ActivityInstrumentationTestCase2{

    public IntentReaderActivityTest() {
        super(IntentReaderActivity.class);
    }

    // TESTING INTENTS
    //
    public void testSendText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY, "Test message 2");
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();
        assertEquals("IntentReaderActivity should get the text from intent!",
                "Test message 2", intentReaderActivity.getText());
    }

    public void testDisplayText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY, "Test message 3");
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();
        TextView textView = (TextView) intentReaderActivity.findViewById(R.id.intentText);

        assertEquals("Text shold be displayed in the TextView!",
                textView.getText().toString(), "Test message 3");
    }

    public void testDoubleText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY, "Test message 4");
        intent.putExtra(IntentReaderActivity.MODE_OF_TRANSFORM_KEY, IntentReaderActivity.DOUBLE);
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();

        assertEquals("The text should be repeated!", "Test message 4Test message 4",
                intentReaderActivity.getText());
    }

    //
    //

    //TODO: Add your code here ...
//-------------------------------------------------------------------------------

    public void testReverseText() {
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY, "Test message 5");
        intent.putExtra(IntentReaderActivity.MODE_OF_TRANSFORM_KEY, IntentReaderActivity.REVERSE);
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();

        assertEquals("The text should be reversed!", "5 egassem tseT",
                intentReaderActivity.getText());
    }

    public void testDefaultMessage() {
        Intent intent = new Intent();
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();

        assertEquals("The text should be default", "default value",
                intentReaderActivity.getText());
    }

    public void testTextViewVisibleOnScreen() {
        Intent intent = new Intent();
        setActivityIntent(intent);

        IntentReaderActivity intentReaderActivity = (IntentReaderActivity) getActivity();

        TextView textView = (TextView) intentReaderActivity.findViewById(R.id.intentText);


        ViewAsserts.assertOnScreen(intentReaderActivity.getWindow().getDecorView(), textView);

        assertEquals("The text should be default", "default value",
                intentReaderActivity.getText());
    }




//-------------------------------------------------------------------------------
}
