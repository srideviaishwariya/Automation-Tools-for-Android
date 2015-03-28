Unit Test - AndroidTest
=======================

A unit test generally tests the smallest possible unit of code (which could be a method, class, or component), without dependencies on system or network resources. AndroidTest framework APIs are based on the JUnit API. The test cases are run either on Android emulator or device

When you create the project Android Studio adds **androidTest** folder where all unit test must recide. You can change this to point to diffrent location by changing the 'androidTest.setRoot' in build.gradle

	android {
    		sourceSets {
        		...
        		androidTest.setRoot('tests')
    		}
	}

### Writing test cases

The test cases are written by extending the ActivityInstrumentationTestCase2.

	public class MainActivityTest
      		extends ActivityInstrumentationTestCase2<MyFirstTestActivity> {

The example covers,
* Testing Activiy Lifecycle
* Testing UI Components
* Test against external dependencies using Mocks (TODO)

### Build and Run Your Test through commandline

	./gradlew connectedAndroidTest --info
	./gradlew connectedAndroidTest

### Instrumentation

An Instrumentation runs various types of TestCases against an Android package (application), a default instrumentation runner is created by Android Studio you can override this by extending InstrumentationTestRunner class and pointing to it in the build.gradle file.

	public class Runner extends InstrumentationTestRunner {

_
	    
	android {
		...
    		defaultConfig {
     			...
     			testApplicationId "com.sridevi.sample1.test"
        		testInstrumentationRunner "com.sridevi.sample1.test.Runner"

Install the instrumentation build on the device,

	adb install /path/to/Sample1/app/build/outputs/apk/app-debug-test-unaligned.apk 
	adb install /path/to/Sample1/app/build/outputs/apk/app-debug-unaligned.apk 
		
List the instrumentation on the device
		
	$ adb shell pm list instrumentation
	instrumentation:com.sridevi.sample1.test/.Runner (target=com.sridevi.sample1)
	...
	
Run the instrumentation as follows from adb

	 adb shell am instrument -w com.sridevi.sample1.test/.Runner

Code Coverage
==============

### Enable Jacoco code coverage on the debug build 

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
		
Run code coverage

	./gradlew createDebugCoverageReport

The relevant generated files/folder are

	/../app/build/outputs/code-coverage/connected/coverage.ec 
	/../app/build/outputs/reports/coverage/debug/index.html 
	
![](https://github.com/srideviaishwariya/Automation-Tools-for-Android/blob/master/Sample1/screenshots/unittest_coverage.png)


###### Running the instrumentation with code coverage through adb

	adb shell am instrument -w -e coverage true -e coverageFile /sdcard/coverageresult.ec com.sridevi.sample1.test/.Runner
		
The result is written to /sdcard/coverageresult.ec

Getting code coverage data from Manual testing
===============================================

Create a task for generating the jacoco report

	task jacocoTestReportAndroidTest(type: JacocoReport, dependsOn: "connectedAndroidTest") {
    def coverageSourceDirs = [
            'src/main/java'
    ]
    group = "Reporting"
    description = "Generates Jacoco coverage reports"
    reports {
        xml{
            enabled = true
            destination "${buildDir}/reports/jacoco/jacoco.xml"
        }
        csv.enabled false
        html{
            enabled true
            destination "${buildDir}/jacocoHtml"
        }
    }
    classDirectories = fileTree(
            dir: 'build/intermediates/classes',
            excludes: ['**/R.class',
                       '**/R$*.class',
                       '**/BuildConfig.*',
                       '**/Manifest*.*',
                       '**/*Activity*.*',
                       '**/*Fragment*.*'
            ]
    )
    sourceDirectories = files(coverageSourceDirs)
    additionalSourceDirs = files(coverageSourceDirs)

    if (project.hasProperty('coverageFiles')) {
        // convert the comma separated string to an array to create an aggregate report from
        // multiple coverage.ec files
        def coverageFilesArray = coverageFiles.split(',')
        executionData = files(coverageFilesArray)
    }
    else {
        executionData = files('build/outputs/code-coverage/connected/coverage.ec')
    }
    }
    

Run the code coverage task as follows, this will create a coverage report using the default coverage.ec file

	./gradlew jacocoTestReportAndroidTest

#### Manual Testing 

Install the instrumented app on the device and manually test it, at the end of the test tap on the code coverage button in the settings menu, this will write the test results to /sdcard/coverage.ec, copy the coverage.ec file to your build machine. Whenever your instrumented code is running you can request a code coverage dump using EMMA's ctl tool.The application is using the following to request a coverage dump programmatically,

	com.vladium.emma.rt.RT.dumpCoverageData(File outFile, boolean merge, boolean stopDataCollection)
	
Run code coverage with the new coverage.ec file (copy this file to your build machine using 'adb pull /sdcard/coverage.ec')
		
	./gradlew -PcoverageFiles=/path/to/coverage.ec jacocoTestReportAndroidTest

![](https://github.com/srideviaishwariya/Automation-Tools-for-Android/blob/master/Sample1/screenshots/manual_coverage.png)

###### Merging results from multiple runs to  get aggregated coverage Report

	./gradlew -PcoverageFiles=/path/to/coverage1.ec,/path/to/coverage2.ec jacocoTestReportAndroidTest
