package com.zapp.library.merchant.test;

import org.junit.runner.RunWith;

import android.test.InstrumentationTestCase;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Run "gradlew :zapp-merchant-library:connectedCheck" to run the instrumentation tests.
 * The code coverage will be generated in "zapp-merchant-library\build\outputs\reports\coverage\debug\index.html".
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features"
)
public class TestCase extends InstrumentationTestCase {

}
