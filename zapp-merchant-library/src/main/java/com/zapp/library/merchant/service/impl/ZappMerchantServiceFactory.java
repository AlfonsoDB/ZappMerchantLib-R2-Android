package com.zapp.library.merchant.service.impl;

import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;
import com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate;
import com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate;
import com.zapp.library.merchant.service.provider.IMerchantService;
import com.zapp.library.merchant.service.provider.NetworkServiceDelegateSupplier;
import com.zapp.library.merchant.service.provider.UIDelegateSuplier;

/**
 * Main Zapp Merchant Library entry point.
 * <br>
 * Contains factory methods for all the publicly available services.
 * <br>
 *
 * @author Vasile Chelban on 6/27/2014.
 */
public final class ZappMerchantServiceFactory {

    /**
     * Private c-tor. This class can't be instantiated.
     * Let the library client keep the reference to any provider it needs.
     */
    private ZappMerchantServiceFactory() {
    }

    /**
     * Creates a new {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService}
     * instance using the provided {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate delegates}.
     *
     * @param networkServiceDelegate Mandatory {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate service network delegate}
     *                               param. All merchant-specific network calls will be executed using
     *                               this {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate delegate}.
     * @param uiDelegate             Mandatory {@link com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate UI delegate} param.
     *                               Only {@link com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate#showPaymentConfirmationScreen
     *                               ZappMerchantUIDelegate#showPaymentConfirmationScreen}
     *                               is required to be implemented, <code>showBRNScreen(...)</code> having a default implementation in Merchant Library.
     * @return a new {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService} instance.
     */
    public static IMerchantService createMerchantService(final IMerchantNetworkServiceDelegate networkServiceDelegate, final ZappMerchantUIDelegate uiDelegate) {
        checkDelegates(networkServiceDelegate, uiDelegate);
        return MerchantServiceImpl.newInstance(networkServiceDelegate, uiDelegate);
    }


    /**
     * Get an existing instance of {@link com.zapp.library.merchant.service.provider.IMerchantService MerchantService}.
     * <br>
     * If no instance was created using {@link com.zapp.library.merchant.service.impl.ZappMerchantServiceFactory#createMerchantService(com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate,
     * com.zapp.library.merchant.service.delegate.ZappMerchantUIDelegate)}
     * factory method, then <code>null</code> is returned.
     *
     * @return An existing instance or <code>null</code> as stated above.
     */
    public static IMerchantService getMerchantService() {
        return MerchantServiceImpl.getInstance();
    }

    /**
     * Returns an instance of {@link com.zapp.library.merchant.service.provider.NetworkServiceDelegateSupplier} instance.
     *
     * @return an existing instance or <code>null</code> if it's not created yet.
     */
    public static NetworkServiceDelegateSupplier getNetworkServiceDelegateSupplier() {
        return MerchantServiceImpl.getCurrentInstance();
    }

    /**
     * Returns an instance of {@link com.zapp.library.merchant.service.provider.UIDelegateSuplier} instance.
     *
     * @return an existing instance or <code>null</code> if it's not created yet.
     */
    public static UIDelegateSuplier getUIDelegateSupplier() {
        return MerchantServiceImpl.getCurrentInstance();
    }

    /**
     * Validates the {@link com.zapp.library.merchant.service.delegate.IZappMerchantServiceDelegate} param passed to factory method.
     *
     * @param serviceDelegate {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate network delegate} to validate
     * @param uiDelegate      {@link com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate UI delegate } to validate
     */
    private static void checkDelegates(final IMerchantNetworkServiceDelegate serviceDelegate, final IZappMerchantUIDelegate uiDelegate)
            throws IllegalArgumentException {
        if (serviceDelegate == null) {
            throw new IllegalArgumentException("Null serviceDelegate parameter passed. Only non-null parameter value accepted.");
        }
        if (uiDelegate == null) {
            throw new IllegalArgumentException("Null UIDelegate parameter passed. Only non-null parameter value accepted.");
        }
    }
}
