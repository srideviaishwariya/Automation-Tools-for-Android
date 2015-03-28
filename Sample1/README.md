AndroidTest framework APIs are based on the JUnit API. Android Studio automatically set up the test package to use InstrumentationTestRunner as the test case runner or you can create your own runner by extending InstrumentationTestRunner.

The test cases are run either on Android emulator or device

### Writing test cases

The test cases are written by extending the ActivityInstrumentationTestCase2.

	public class MainActivityTest
      extends ActivityInstrumentationTestCase2<MyFirstTestActivity> {


### Build and Run Your Test through commnadline

	$ ./gradlew connectedAndroidTest --info
	$ ./gradlew connectedAndroidTest

### Add your own TestRunner

Create a class that extends InstrumentationTestRunner.
And in the app build.gradle file add the intialize the testInstrumentationRunner to the class,
	    
	    android {
				...
    		defaultConfig {
     				...
					testApplicationId "com.sridevi.sample1.test"
        			testInstrumentationRunner "com.sridevi.sample1.test.Runner"

Install the instrumentation and debug build on the device,

		$ adb install /path/to/Sample1/app/build/outputs/apk/app-debug-test-unaligned.apk 
		$ adb install /path/to/Sample1/app/build/outputs/apk/app-debug-test-unaligned.apk 
		
Run the instrumentation from adb as follows,
		
		$ adb shell pm list instrumentation
		instrumentation:com.sridevi.sample1.test/.Runner (target=com.sridevi.sample1)
		
		$ adb shell am instrument -w com.sridevi.sample1.test/.Runner

### Enable coverage on the debug build 

	buildTypes {
    		debug {
        		testCoverageEnabled true
    		}
	}
	
Apply Jacoco plugin and version

	apply plugin: 'jacoco'
	jacoco {
    		version "0.7.1.201405082137"
	}
		
Run

	$ ./gradlew createDebugCoverageReport
