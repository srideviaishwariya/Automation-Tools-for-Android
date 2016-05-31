package com.sridevi.sample1;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by sridevi on 3/27/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        activity = getActivity();
    }

    @SmallTest
    public void testFrameLayoutNotNull(){
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.frame_container);
        assertNotNull(frameLayout);
    }

    @SmallTest
    public void testListViewClick() throws Throwable {
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.frame_container);
        assertNotNull(frameLayout);
        final ListView listView = (ListView) frameLayout.findViewById(R.id.list);
        assertNotNull(listView);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                int position_in_list = 1;
                listView.performItemClick(listView, position_in_list, listView.getItemIdAtPosition(position_in_list));
            }
        });
        getInstrumentation().waitForIdleSync();
        final TextView textView = (TextView) frameLayout.findViewById(R.id.text1);
        assertNotNull(textView);
        assertEquals('S', textView.getText().charAt(0));
    }

    @SmallTest
    public void testAnotherActivity(){

        Instrumentation instrumentation = getInstrumentation();

        // Register we are interested in the activity
        ActivityMonitor monitor =
                getInstrumentation().addMonitor(AnotherActivity.class.getName(), null, false);

        // Start the activity
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName(instrumentation.getTargetContext(), AnotherActivity.class.getName());
        instrumentation.startActivitySync(intent);

        // Wait for it to start...
        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
        assertNotNull(currentActivity);

        //verify that textview is present
        final TextView textView = (TextView) currentActivity.findViewById(R.id.text);
        assertEquals('h', textView.getText().charAt(0));

        currentActivity.finish();
        // Remove the ActivityMonitor
        instrumentation.removeMonitor(monitor);

    }
}
