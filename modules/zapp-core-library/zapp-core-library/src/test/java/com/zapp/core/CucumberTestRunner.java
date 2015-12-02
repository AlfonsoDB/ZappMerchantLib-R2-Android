package com.zapp.core;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The cucumber test runner.
 * Add "zapp-core-library/" prefix to the "features" if you run the tests from IntelliJ (or remove it if you run the tests with "./gradlew build".
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "html:build/reports/cucumber"}
)
public class CucumberTestRunner {

}