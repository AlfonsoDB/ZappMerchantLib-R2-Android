package com.zapp.core.generator;

/**
 * The type of the BDD test scenario.
 */
public enum ScenarioType {
    /**
     * The scenario is expected to pass.
     */
    POSITIVE,
    /**
     * The scenario is expected to fail.
     */
    NEGATIVE,
    /**
     * The scenario is a duplicate negative scenario hence it is skipped.
     */
    DUPLICATE_NEGATIVE
}