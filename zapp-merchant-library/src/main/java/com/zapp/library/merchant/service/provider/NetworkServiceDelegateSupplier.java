package com.zapp.library.merchant.service.provider;

import com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate;

/**
 * An interface that supplies the {@link com.zapp.library.merchant.service.delegate.IMerchantNetworkServiceDelegate} used by the MerchantServiceImpl.
 *
 * @author Vasile Chelban on 7/1/2014.
 */
public interface NetworkServiceDelegateSupplier {

    /**
     * @return the {@link IMerchantNetworkServiceDelegate}
     */
    IMerchantNetworkServiceDelegate getNetworkServiceDelegate();
}
