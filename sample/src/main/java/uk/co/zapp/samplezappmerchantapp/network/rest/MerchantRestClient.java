package uk.co.zapp.samplezappmerchantapp.network.rest;

import com.zapp.core.PaymentRequest;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import uk.co.zapp.samplezappmerchantapp.network.exception.GenericException;
import uk.co.zapp.samplezappmerchantapp.network.exception.NetworkException;
import uk.co.zapp.samplezappmerchantapp.network.exception.PaymentNotFinishedException;
import uk.co.zapp.samplezappmerchantapp.network.response.PaymentNotificationResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.RTPResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.SettlementStatusResponse;

/**
 * REST network client interface.
 */
public interface MerchantRestClient {

    @PUT(NetworkConsts.CREATE_RTP_URL)
    RTPResponse createRTP(@Body PaymentRequest request) throws NetworkException, GenericException;

    @GET(NetworkConsts.POLL_FOR_PAYMENT_NOTIFICATION_URL)
    PaymentNotificationResponse notifyMerchantPayment(@Path("aptrid") String aptrid) throws NetworkException, PaymentNotFinishedException, GenericException;

    @GET(NetworkConsts.SETTLEMENT_STATUS_URL)
    SettlementStatusResponse getSettlementStatus(@Path("merchantId") String merchantId, @Path("settlementRetrievalId") String settlementRetrievalId)
            throws NetworkException, GenericException;
}
