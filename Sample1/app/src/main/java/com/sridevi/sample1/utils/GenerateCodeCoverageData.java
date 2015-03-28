package com.sridevi.sample1.utils;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This generates the code coverage report using
 * http://www.eclemma.org/jacoco/trunk/doc/api/com/vladium/emma/rt/RT.html
 *
 * @author sridevi
 *
 */
public class GenerateCodeCoverageData {

    private final static String DEFAULT_COVERAGE_FILE_NAME = "/sdcard/coverage.ec";

	/**
	 * Dumps the code coverage .ec file to the sdcard
	 */
    public static void dump() {
        java.io.File coverageFile = new java.io.File(DEFAULT_COVERAGE_FILE_NAME);
        try {
            Class<?> emmaRTClass = Class.forName("com.vladium.emma.rt.RT");
            Method dumpCoverageMethod = emmaRTClass.getMethod("dumpCoverageData",
                    coverageFile.getClass(), boolean.class, boolean.class);
            dumpCoverageMethod.invoke(null, coverageFile, false, false);
        } catch (ClassNotFoundException |
		        SecurityException |
		        NoSuchMethodException |
		        IllegalArgumentException |
		        IllegalAccessException |
		        InvocationTargetException e) {
	        Log.e("GenerateCodeCoverageData", "Failed to generate code coverage report", e);
        }
    }
}
