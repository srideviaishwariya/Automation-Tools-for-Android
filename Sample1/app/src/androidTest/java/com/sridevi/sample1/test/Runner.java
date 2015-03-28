package com.sridevi.sample1.test;

import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;

import com.sridevi.sample1.MainActivityTest;

import junit.framework.TestSuite;

/**
 * Created by sridevi on 3/27/15.
 */
public class Runner extends InstrumentationTestRunner {
    @Override
    public TestSuite getAllTests(){
        InstrumentationTestSuite suite = new InstrumentationTestSuite(this);
        suite.addTestSuite(MainActivityTest.class);
        return suite;
    }

    @Override
    public ClassLoader getLoader() {
        return Runner.class.getClassLoader();
    }
}
