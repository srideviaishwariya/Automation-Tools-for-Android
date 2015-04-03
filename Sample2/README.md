Roboelectric And Code Coverage
===============================

Robolectric and Android Studio/Gradle do not work together out of the box, so I am using JC&K Solution's Gradle plugin.

Include the JC&K Gradle plugin to the root build.gradle file,

    // Robolectric Gradle plugin from JC&K Solutions
    classpath 'com.github.jcandksolutions.gradle:android-unit-test:2.1.1'
    
 And add the dependencies as follows in build.gradle

    // Docs: https://github.com/JCAndKSolutions/android-unit-test
    apply plugin: 'android-unit-test'
    
    dependencies {
        // core android studio module
        compile project(':core')
        // roboelectric dependencies
        testCompile 'org.robolectric:robolectric:2.4'
        testCompile 'junit:junit:4.+'
    }

I am placing all test case in src/test, so point androidTest variable to this location

    sourceSets {
        ...
        androidTest.setRoot('src/test')
    }

Run the test cases as follows,

    ./gradlew check

#### Roboelectric - Runs on JVM

Robolectric does not require an emulator or actual device to run the code. Once it’s set up, we can write jUnit-style tests against our classes without needing the baggage of the emulator. It does this using “Shadow Classes” or mock implementations of Android core libraries.

#### Adding Code Coverage 

Add a code coverage task in the app's build.gradle file as follows,

    apply plugin: 'jacoco'
    jacoco {
        toolVersion = '0.7.1.201405082137'
    }
    task generateCoverageReport(type: JacocoReport, dependsOn: "testDebug") {
      description 'Generate Jacoco test coverage report for the Debug build'
      reports {
          xml.enabled = true
          xml.destination = "$buildDir/coverage-report/coverage.xml"
          html.enabled = true
          html.destination = "$buildDir/coverage-report/html"
      }
      classDirectories = fileTree(
            dir: 'build/intermediates/classes/debug',
            excludes: ['**/R.class',
                       '**/R$*.class',
                       '**/BuildConfig.class',
                       '**/*$InjectAdapter*',
                       '**/*$ModuleAdapter*',
                       '**/*$StaticInjection*'
            ]
        )
      sourceDirectories = files(['src/main/java'])
      executionData = files('build/jacoco/testDebug.exec')
    }
    

