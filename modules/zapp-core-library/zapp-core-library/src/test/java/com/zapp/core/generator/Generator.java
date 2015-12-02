package com.zapp.core.generator;

import java.io.FileNotFoundException;

/**
 * Utility class to re-generate all the feature files from scratch.
 * Note: the Models and TransactionId feature file is generated manually so Models.feature and TransactionId.feature files will not be regenerated. These files have
 * different file names in order to distinguish them from the generated ones.
 *
 * @author msagi
 */
public class Generator {

    final static AbstractFeatureFileGenerator[] generators = {
            new AddressFeatureFileGenerator(),
            new BankAccountFeatureFileGenerator(),
            new BillDetailsFeatureFileGenerator(),
            new CurrencyAmountFeatureFileGenerator(),
            new DeliveryAddressFeatureFileGenerator(),
            new MerchantFeatureFileGenerator(),
            new PaymentAuthFeatureFileGenerator(),
            new PaymentRequestFeatureFileGenerator(),
            new TransactionFeatureFileGenerator(),
            new UserFeatureFileGenerator(),
    };

    public static void main(final String[] args) {
        for (AbstractFeatureFileGenerator generator : generators) {
            try {
                generator.autoGenerateFeatureFile();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
        }
    }
}
