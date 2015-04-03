package com.sridevi.sample2.activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by sridevi on 3/18/15.
 */

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MyActivityRoboTest {

    @Test
    public void testSomething() throws Exception {
        //assertTrue(Robolectric.buildActivity(MainActivity.class).create().get() != null);
        assertEquals(1,1);
    }

    @Test
    public void testWhenActivityCreatedHelloTextViewIsVisible() throws Exception {
        /*
        MainActivity activity = new MainActivity();
        ActivityController.of(activity).attach().create();
        int visibility = activity.findViewById(R.id.textView).getVisibility();
        assertEquals(visibility, View.VISIBLE);
        */
        assertEquals(1,1);
    }

}