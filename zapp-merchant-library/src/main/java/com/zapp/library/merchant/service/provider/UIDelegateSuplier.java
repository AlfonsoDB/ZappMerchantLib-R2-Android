package com.zapp.library.merchant.service.provider;

import com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate;

/**
 * Supplier for {@link com.zapp.library.merchant.service.delegate.IZappMerchantUIDelegate} used by the MerchantServiceImpl.
 *
 * @author vivascu on 7/7/2014.
 */
public interface UIDelegateSuplier {

    /**
     * @return the {@link IZappMerchantUIDelegate}
     */
    IZappMerchantUIDelegate getUIDelegate();
}
