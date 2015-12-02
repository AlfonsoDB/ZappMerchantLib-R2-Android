package uk.co.zapp.samplezappmerchantapp.network.rest;


import com.google.gson.GsonBuilder;

import com.zapp.core.PaymentRequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.reflect.Type;

import retrofit.Endpoint;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import uk.co.zapp.samplezappmerchantapp.configuration.AppConfigurationUtils;
import uk.co.zapp.samplezappmerchantapp.network.exception.GenericException;
import uk.co.zapp.samplezappmerchantapp.network.exception.NetworkException;
import uk.co.zapp.samplezappmerchantapp.network.exception.PaymentNotFinishedException;
import uk.co.zapp.samplezappmerchantapp.network.request.PaymentRequestSerializer;
import uk.co.zapp.samplezappmerchantapp.network.response.BaseServerResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.PaymentNotificationDeserializer;
import uk.co.zapp.samplezappmerchantapp.network.response.PaymentNotificationResponse;
import uk.co.zapp.samplezappmerchantapp.network.response.SettlementStatusDeserializer;
import uk.co.zapp.samplezappmerchantapp.network.response.SettlementStatusResponse;

/**
 * Provider for the REST network client.
 */
public class RestClientProvider {

    /**
     * The dynamic endpoint.
     */
    private static Endpoint sEndpoint;

    /**
     * Get new rest client instance.
     *
     * @return The {@link MerchantRestClient} REST client instance.
     */
    public static synchronized MerchantRestClient getRestClient(@NonNull final Context context) {
        if (sEndpoint == null) {
            sEndpoint = new Endpoint() {

                /**
                 * Application configuration utils.
                 */
                private final AppConfigurationUtils mAppConfigurationUtils = AppConfigurationUtils.getInstance(context.getApplicationContext());

                @Override
                public String getUrl() {
                    return mAppConfigurationUtils.getActiveGateway().getEndpoint();
                }

                @Override
                public String getName() {
                    return mAppConfigurationUtils.getActiveGateway().toString();
                }
            };
        }
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(sEndpoint)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(final RequestInterceptor.RequestFacade requestFacade) {
                        requestFacade.addHeader("Connection", "close");
                        requestFacade.addHeader("Accept-Charset", "utf-8");
                    }
                })
                .setConverter(new Converter() {
                    @Override
                    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
                        final GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(PaymentNotificationResponse.class, new PaymentNotificationDeserializer());
                        gsonBuilder.registerTypeAdapter(SettlementStatusResponse.class, new SettlementStatusDeserializer());
                        final Converter converter = new GsonConverter(gsonBuilder.create());
                        final Object responseObject = converter.fromBody(body, type);
                        if (responseObject instanceof BaseServerResponse) {
                            final BaseServerResponse response = (BaseServerResponse) responseObject;
                            if (!TextUtils.isEmpty(response.getErrorMessage()) && response.getErrorCode() != BaseServerResponse.ERR_CODE_NOT_VERIFIED) {
                                response.setErrorCode(BaseServerResponse.ERR_CODE_REQUEST_INVALIDATED);
                            }
                        }
                        return responseObject;
                    }

                    @Override
                    public TypedOutput toBody(final Object object) {
                        final GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(PaymentRequest.class, new PaymentRequestSerializer());
                        return new GsonConverter(gsonBuilder.create()).toBody(object);
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(@NonNull final RetrofitError cause) {

                        final Throwable error;
                        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
                            error = new NetworkException();
                        } else if (cause.getResponse().getStatus() == 418 && cause.getBody() != null) {
                            final BaseServerResponse response = (BaseServerResponse) cause.getBodyAs(BaseServerResponse.class);
                            if (response.getErrorCode() == BaseServerResponse.ERR_CODE_NOT_VERIFIED) {
                                error = new PaymentNotFinishedException();
                            } else {
                                error = new GenericException();
                            }
                        } else {
                            error = new GenericException();
                        }

                        return error;
                    }
                })
                .build();

        return restAdapter.create(MerchantRestClient.class);
    }
}
