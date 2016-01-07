#ZappMerchantLibrary SDK integration documentation
------
ZappMerchantLibrary is for Android Merchant applications to integrate Pay by Bank app payments.

##Build from source
###Since the debug and release versions of the Zapp Merchant Android Library .aar files are provided with the release, building the library from source is optional
Gradle (on Mac):
```sh
    - open a Terminal Window
    - change directory to be the root folder of "Sample" (Merchant Demo 2)
        Android Application Project
    - execute command "./gradlew :zapp-merchant-library:assembleRelease"
    - execute command "./gradlew :zapp-core-library:assemble"
    - the release Android Archive of the library (zapp-merchant-library-release.aar)
        is located in the ./zapp-merchant-library/build/outputs/aar folder
    - the jar file (zapp-core-library.jar)
        is located in the ./modules/zapp-core-library/zapp-core-library/build/libs folder
```
Gradle (on Windows):
```sh
    - open a Command Prompt Window
    - change directory to be the root folder of "Sample" (Merchant Demo 2)
        Android Application Project
    - execute command "gradlew :zapp-merchant-library:assembleRelease"
    - execute command "gradlew :zapp-core-library:assemble"
    - the release Android Archive of the library (zapp-merchant-library-release.aar)
        is located in the zapp-merchant-library/build/outputs/aar directory
    - the jar file (zapp-core-library.jar)
        is located in the modules/zapp-core-library/zapp-core-library/build/libs directory
```

###Import the debug or release version Zapp Merchant Library .aar to the Merchant Application Project. This is the recommended way of using the library
Android Studio:
```sh
    - select "File" -> "New" -> "New Module"
    - select "Import .JAR or .AAR package"
    - click "Next"
    - browse "zapp-merchant-library-release.aar" file in to the "File name" field
    - enter "zapp-merchant-libarary-2.0.0" in to the "Subproject name" field
    - click "Finish"
    - add dependency "compile project(':zapp-merchant-library-2.0.0')" in gradle file
    - in order to resolve the conflicts replace the "allowBackup" value in application 
      manifest  file as follows:
    <application
        android:allowBackup="true"
        tools:replace="android:allowBackup"
```

###Import the Zapp Core Library .jar to the Merchant Application Project. This is the recommended way of using the library
Android Studio:
```sh
    - select "File" -> "New" -> "New Module"
    - select "Import .JAR or .AAR package"
    - click "Next"
    - browse "zapp-core-library.jar" file in to the "File name" field
    - enter "zapp-core-library" in to the "Subproject name" field
    - click "Finish"
    - add dependency "compile project(':zapp-core-library')" in gradle file
```
##Add the source
###Adding the source of the Zapp Merchant Library to the Merchant Application Project is not recommended. However, it may be useful to do this during the development phase
Android Studio:
```sh
    - select "File" -> "New" -> "Import Module"
    - browse "zapp-merchant-library" subfolder of the Sample (Merchant Demo 2) application in to the "Source directory" field
    - observe that the "zapp-core-library" appears in the additional required modules list
    - click "Finish"
```

The source code structure

* com.zapp.library.merchant
    * exception - predefined errors
    * model - payment request builder
        * callback - callback listeners for delegate calls
    * service
        * delegate - the delegate interfaces to be implemented by the Merchant Application
        * impl - Zapp merchant service implementation
        * provider - Zapp merchant service interface
    * ui - Pay by Bank app popup activity
        * fragment - Pay by Bank app popup fragments (mcomm, ecomm, error)
    * util - utility classes
    * view - Zapp custom widgets and Pay by Bank app button


##Usage

###Add Pay by Bank App Button To View Layout
Add the following to your layout resource:
```xml
    <com.zapp.library.merchant.view.PBBAButton
        android:id="@+id/pbba_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

###Create a Zapp Payment Request
There are two types of possible payments **(Immediate and Deferred)**. Pay by Bank app provides the ability for a Merchant to defer the payment until the goods are ready to be fulfilled. This feature is called **Pay on Delivery**.

Here are examples of how to create **Immediate** and **Deferred** payment requests for "Quick Payment", "Paying a bill" or "SMB" journey
More examples are present in the Sample (Merchant Demo 2) Application source code.

#####Immediate Payments
**PayPlus Payment**

```java
try {
    final CurrencyAmount amount = new CurrencyAmount(1000l, CurrencyAmount.POUNDS);

    final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /* postCode */ null, Address.UK);

    //logoUrl is null because it is set in Zapp Core already
    final Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
            Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

    final Uri.Builder uriBuilder = new Uri.Builder();
    final Uri merchantCallbackUri = uriBuilder
            .scheme(getString(R.string.app_scheme))
            .authority("merchant.domain.com")
            .appendQueryParameter("param1", "value1")
            .build();

    final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
    paymentRequestBuilder
            .withRtpType(RTPType.IMMEDIATE)
            .withPaymentType(PaymentType.INSTANT_PAYMENT)
            .withCheckoutType(CheckoutType.QUICK)
            .withDeliveryType(DeliveryType.ADDRESS)
            .withAmount(amount)
            .withMerchant(merchant)
            .withMerchantCallbackUrl(merchantCallbackUri.toString());

    final PaymentRequest paymentRequest = paymentRequestBuilder.build();

    } catch (Exception e) {
        Log.e(TAG, "Failed to set up PayPlus Payment", e);
    }
```

####Paying a bill

```java
try {
    final CurrencyAmount amount = new CurrencyAmount(1400l, CurrencyAmount.POUNDS);

    final Date periodFrom = DATE_FORMATTER.parse("2015-08-01");
    final Date periodTo = DATE_FORMATTER.parse("2015-08-30");
    final BillDetails billDetails = new BillDetails("8500 222 66138", "Y6557802", periodFrom, periodTo);

    final Address userAddress = new Address("10 Downing Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /*postCode*/ "SW1A 2AA", Address.UK);
    final User user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ null, /*phone*/ null);
    final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /*postCode*/ null, Address.UK);

    //logoUrl is null because it is set in Zapp Core already
    final Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
            Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

    final Uri.Builder uriBuilder = new Uri.Builder();
    final Uri merchantCallbackUri = uriBuilder
            .scheme(getString(R.string.app_scheme))
            .authority("merchant.domain.com")
            .appendQueryParameter("param1", "value1")
            .build();

    final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
    paymentRequestBuilder
            .withRtpType(RTPType.IMMEDIATE)
            .withPaymentType(PaymentType.BILL_PAY)
            .withCheckoutType(CheckoutType.NORMAL)
            .withDeliveryType(DeliveryType.ADDRESS)
            .withAmount(amount)
            .withAddress(userAddress)
            .withUser(user)
            .withMerchant(merchant)
            .withBillDetails(billDetails)
            .withMerchantCallbackUrl(merchantCallbackUri.toString());

    final PaymentRequest paymentRequest = paymentRequestBuilder.build();

    } catch (Exception e) {
        Log.e(TAG, "Failed to set up Bill Payment", e);
    }
```

####SMB Journey

```java
try {
    final CurrencyAmount amount = new CurrencyAmount(1600l, CurrencyAmount.POUNDS);

    final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ null, /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /*postCode*/ null, Address.UK);

    //logoUrl is null because it is set in Zapp Core already
    final Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
            Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

    final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
    paymentRequestBuilder
            .withRtpType(RTPType.IMMEDIATE)
            .withPaymentType(PaymentType.SMB)
            .withCheckoutType(CheckoutType.NORMAL)
            .withDeliveryType(DeliveryType.ADDRESS)
            .withAmount(amount)
            .withMerchant(merchant);

    final PaymentRequest paymentRequest = paymentRequestBuilder.build();

    } catch (ZappModelValidationException e) {
        Log.e(TAG, "Failed to set up SMB Payment", e);
    }
```

#####Deferred Payments
####PayPlus

```java
try {
    final CurrencyAmount defrdRTPAgrmtAmount = new CurrencyAmount(1200l, CurrencyAmount.POUNDS); //£12
    final CurrencyAmount defrdRTPMaxAgrdAmount = null; //N/A

    final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /* postCode */ "EC1 2EC", Address.UK);

    //logoUrl is null because it is set in Zapp Core already
    final Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
            Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

    final AmountDetail serviceAmountDetail = new AmountDetail(AmountType.FEES, "Service fee", "10.00", /* rate */ null); //£10 service
    final AmountDetail vatAmountDetail = new AmountDetail(AmountType.VATX, "VAT", "20.00", "2.00"); //£2 VAT
    final AmountDetail[] amountDetails = {serviceAmountDetail, vatAmountDetail};

    final Uri.Builder uriBuilder = new Uri.Builder();
    final Uri merchantCallbackUri = uriBuilder
            .scheme(getString(R.string.app_scheme))
            .authority("merchant.domain.com")
            .appendQueryParameter("param1", "value1")
            .build();

    final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
    paymentRequestBuilder
            .withRtpType(RTPType.DEFERRED)
            .withPaymentType(PaymentType.INSTANT_PAYMENT)
            .withCheckoutType(CheckoutType.QUICK)
            .withDeliveryType(DeliveryType.ADDRESS)
            .withMerchant(merchant)
            .withMerchantCallbackUrl(merchantCallbackUri.toString())
            .withAcrType(ACRType.FUNDS_CHECK) //do funds checking but no funds guarantee.
            .withDefrdRTPAgrmtAmount(defrdRTPAgrmtAmount) //£12.00
            .withDefrdRTPMaxAgrdAmount(defrdRTPMaxAgrdAmount) //N/A
            .withDefrdAmountDetails(amountDetails);

    final PaymentRequest paymentRequest = paymentRequestBuilder.build();

    } catch (Exception e) {
        Log.e(TAG, "Failed to set up PayPlus deferred payment", e);
    }
```

####Standard Payment

```java
try {
    final CurrencyAmount defrdRTPAgrmtAmount = new CurrencyAmount(1200l, CurrencyAmount.POUNDS); //£12
    final CurrencyAmount defrdRTPMaxAgrdAmount = null; //N/A

    final Address userAddress = new Address("125 Old Broad St", /*line2*/ null, /*line3*/ "London", /*line4*/ null, /*line5*/ null,
    /*line6*/ null, /*postCode*/ "ECN 1AR", Address.UK);

    final User user = new User("John", "Smith", /*middle name*/ null, /*title*/ null, /*email*/ "consumer1@pbba.co.uk", 
    /*phone*/ null);

    final Address merchantStoreAddress = new Address("Zapp Super Store", "2 Puddle Dock", "London", /*line4*/ null, /*line5*/ null,
    /*line6*/ null, /*postCode*/ "EC4V 3DB", Address.UK);

    final Address merchantAddress = new Address("200 Queen Victoria Street", /*line2*/ null, /*line3*/ "London", /*line4*/ null,
    /*line5*/ null, /*line6*/ null, /*postCode*/ "EC1 2EC", Address.UK);

    //logoUrl is null because it is set in Zapp Core already
    final Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_NAME, "merchantdemo@zapp.co.uk", /*phone*/ null, merchantAddress,
                    Uri.parse("https://play.google.com/store/apps/details?id=merchant.application.package").toString(), /* logoUrl */ null);

    final Uri.Builder uriBuilder = new Uri.Builder();
    final Uri merchantCallbackUri = uriBuilder
            .scheme(getString(R.string.app_scheme))
            .authority("merchant.domain.com")
            .appendQueryParameter("param1", "value1")
            .build();

    final PaymentRequestBuilder paymentRequestBuilder = new PaymentRequestBuilder();
    paymentRequestBuilder
            .withRtpType(RTPType.DEFERRED)
            .withPaymentType(PaymentType.INSTANT_PAYMENT)
            .withCheckoutType(CheckoutType.NORMAL)
            .withDeliveryType(DeliveryType.ADDRESS)
            .withAddress(userAddress)
            .withUser(user)
            .withMerchant(merchant)
            .withMerchantCallbackUrl(merchantCallbackUri.toString())
            .withAcrType(ACRType.FUNDS_CHECK) //do funds checking but no funds guarantee.
            .withDefrdRTPAgrmtAmount(defrdRTPAgrmtAmount) //£12.00
            .withDefrdRTPMaxAgrdAmount(defrdRTPMaxAgrdAmount); //N/A

    final PaymentRequest paymentRequest = paymentRequestBuilder.build();

    } catch (ZappModelValidationException e) {
        Log.e(TAG, "Failed to set up Standard deferred payment", e);
    }
```

###Initialize Zapp Merchant Service

Initialize the delegates:

```java
    final IMerchantNetworkServiceDelegate serviceDelegate = new MerchantNetworkServiceDelegateImpl(context);
    final ZappMerchantUIDelegate uiDelegate = new MerchantUIDelegateImpl(context);
```
Pass the delegates to the Zapp Merchant Service

```java
    final IMerchantService merchantService = ZappMerchantServiceFactory.createMerchantService(serviceDelegate, uiDelegate);
```
Implement the **IMerchantNetworkServiceDelegate** and **ZappMerchantUIDelegate** in order to communicate with the Merchant in-house system to enable the user to see the payment status in the Merchant application.

####Delegates Implementation
The Merchant application should implement **IMerchantNetworkServiceDelegate** and extend the **ZappMerchantUIDelegate** which are used by the Zapp Merchant Library in order to communicate with the user and the backend.

####IMerchantNetworkServiceDelegate:
```java
/**
 * Initiates a payment using the provided {@link com.zapp.core.Transaction} details
 * which describes all the details about a payment, like {@link com.zapp.core.PaymentType PaymentType}
 * or {@link com.zapp.core.BillDetails BillDetails}.
 * <br>
 * Implementers are completely responsible for handling network or DB calls. <br>
 * Implementation can execute blocking calls (network/DB calls) in current Thread or have an
 * asynchronous execution in place.
 * <br>
 * This method is invoked only after a successful validation of payment details and a new RTP(request-to-pay)
 * is to be created.
 * <br>
 * Caller should be notified through {@link com.zapp.library.merchant.model.callback.OnResponseListener responseListener}
 * in case of success or failure.
 *
 * @param paymentRequest      All details required to initiate a merchant payment journey, like
 *                           {@link com.zapp.core.PaymentType PaymentType}
 *                           or {@link com.zapp.core.BillDetails BillDetails}.
 *                           You can use {@link com.zapp.library.merchant.model.PaymentRequestBuilder} to
 *                           build a {@link PaymentRequest} object you need.
 * @param onResponseListener {@link OnResponseListener OnResponseListener}
 *                           providing callbacks for success and error scenarios while initiating
 *                           an RTP using the provided {@link com.zapp.core.Transaction} details or any
 *                           network-related {@link com.zapp.library.merchant.exception.Error error}.
 */
void initiatePayment(PaymentRequest paymentRequest, OnResponseListener<Transaction> onResponseListener);

/**
 * Checks a payment status using the provided {@link com.zapp.core.Transaction} details
 * which should contain, at least, a non-null {@link com.zapp.core.TransactionId transactionId}
 * containing an <code>secureToken</code> which uniquely identifies a {@link com.zapp.core.Transaction}.
 * <br>
 * Implementers are completely responsible for handling network or DB calls. <br>
 * Implementation can execute blocking calls (network/DB calls) in current Thread or have an
 * asynchronous execution in place.
 * <br>
 * Caller should be notified through {@link com.zapp.library.merchant.model.callback.OnResponseListener responseListener}
 * in case of success or failure.
 *
 * @param transaction        Payment details containing a non-null {@link com.zapp.core.TransactionId transactionId}
 *                           containing an <code>secureToken</code> which uniquely identifies a
 *                           pending {@link com.zapp.core.Transaction}.
 *                           <br>
 * @param onResponseListener {@link OnResponseListener OnResponseListener}
 *                           providing callbacks for success and error scenarios while checking
 *                           payment status using the provided <code>aptrID</code> or any
 *                           network-related {@link com.zapp.library.merchant.exception.Error error}.
 *                           If an error with errorCode
 *                           {@link com.zapp.library.merchant.exception.ErrorType#PAYMENT_NOT_CONFIRMED ErrorType#PAYMENT_NOT_CONFIRMED}
 *                           is received on failure, then merchant library automatically send
 *                           another call for checking the payment status using the current method.
 */
void notifyMerchantPayment(Transaction transaction, OnResponseListener<Transaction> onResponseListener);

/**
 * Retrieves the current settlement status in Zapp using the merchantId and settlementRetrievalId.
 * <br>
 * <br>
 * Caller should be notified through {@link OnResponseListener responseListener} in case of success or failure.
 *
 * @param merchantId            The merchant's id.
 * @param settlementRetrievalId The settlement retrieval id.
 * @param responseListener      {@link OnResponseListener OnResponseListener} providing callbacks for success and error scenarios.
 */
void getSettlementStatus(String merchantId, String settlementRetrievalId, OnResponseListener<SettlementStatus> responseListener);
```


####ZappMerchantUIDelegate:
```java
/**
 * Shows a payment confirmation screen to the user with the status whether it has been successful or rejected.
 * Also it allows the merchant app to display the payment details to the user.
 *
 * @param transaction Transaction details to be displayed.
 * @param error       The error message in case that payment has failed.
 */
void showPaymentConfirmationScreen(Transaction transaction, com.zapp.library.merchant.exception.Error error);

/**
 * Shows the screen with the Pay by Bank app code to the user for Small-Micro Business (SMB) transactions.
 *
 * @param transaction The SMB payment transaction details.
 */
void showSMBScreen(Transaction transaction);
```

###Sample implementations

1. "Merchant Demo" implementation which gives and example of how to use the SDK is at uk.co.zapp.samplezappmerchantapp.
2. Sample implementation of the **IMerchantNetworkServiceDelegate** is at ***uk.co.zapp.samplezappmerchantapp.network.delegate / MerchantNetworkServiceDelegateImpl.java***.
3. Sample implementation of the ***ZappMerchantUIDelegate*** is at ***uk.co.zapp.samplezappmerchantapp.ui / MerchantUIDelegateImpl.java***.
