package uk.co.zapp.samplezappmerchantapp.configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.zapp.samplezappmerchantapp.BuildConfig;
import uk.co.zapp.samplezappmerchantapp.R;
import uk.co.zapp.samplezappmerchantapp.util.AutoCloseUtils;

/**
 * Application configuration utility implementation.
 *
 * It reads its configuration from the assets folder (named app-config.json). Parses and validates each fields then initialises
 * the gateway list and the application scheme.
 *
 * The json file structure supported (all fields are mandatory):
 * <pre>
 *  {
 *      "gateway" : {
 *          "env" : [
 *              {
 *                  "name" : "Demo",
 *                  "domain" : "demo.retailmine.co.uk/cfi-gateway",
 *                  "version" : "2.0"
 *                  "clientid" : "MerchantDemo2"
 *              },
 *              ...
 *           ]
 *      },
 *      "app" : {
 *          "appUrlScheme" : "yourbank"
 *      }
 *  }
 * </pre>
 */
public class AppConfigurationUtils {

    /**
     * Format pattern for the 'required key is missing' strings.
     */
    private static final String REQUIRED_KEY_IS_MISSING_PATTERN = "Required key is missing from configuration: '%s'";

    /**
     * The file name of the app configuration file.
     */
    private static final String CONFIG_FILE_NAME = "app-config.json";

    /**
     * The file name of the shared preferences file.
     */
    private static final String PREFERENCES_NAME = AppConfigurationUtils.class.getSimpleName() + ".preferences";

    /**
     * The key to the gateway index in shared preferences.
     */
    private static final String KEY_GATEWAY_INDEX = "gateway.index";

    /**
     * Singleton class instance.
     */
    private static AppConfigurationUtils sInstance = null;

    /**
     * The (application) context to use.
     */
    private Context mContext = null;

    /**
     * The app scheme (initialized from the configuration)
     */
    private String mAppScheme = null;

    /**
     * The list of available gateways (initialized form the configuration).
     */
    private final List<Gateway> mGateways = new ArrayList<>();

    /**
     * The index of the active gateway in the list of available gateways. Default: -1 (means: not initialised).
     */
    private int mActiveGatewayIndex = -1;

    /**
     * Create new instance.
     *
     * @param context The context to use.
     * @see #getActiveGateway()
     */
    private AppConfigurationUtils(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context.getApplicationContext();

        AssetManager assetManager = null;
        InputStream configInputStream = null;
        try {
            assetManager = context.getAssets();
            configInputStream = assetManager.open(CONFIG_FILE_NAME);

            final byte[] buffer = new byte[configInputStream.available()];
            final int read = configInputStream.read(buffer);
            if (read != buffer.length) {
                throw new IllegalArgumentException("Error reading configuration data");
            }
            final JSONObject jsonConfigObject = new JSONObject(new String(buffer, "UTF-8"));

            final JSONObject jsonAppConfig = jsonConfigObject.optJSONObject("app");
            if (jsonAppConfig == null) {
                throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "app"));
            }
            final String jsonAppUrlScheme = jsonAppConfig.optString("appUrlScheme");
            if (TextUtils.isEmpty(jsonAppUrlScheme)) {
                throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "app.appUrlScheme"));
            }
            final String stringsAppUrlScheme = mContext.getString(R.string.app_scheme);
            if (jsonAppUrlScheme.equals(stringsAppUrlScheme)) {
                mAppScheme = jsonAppUrlScheme;
            } else {
                throw new IllegalArgumentException(CONFIG_FILE_NAME + ":app.appUrlScheme != strings.xml:app_scheme");
            }

            final JSONObject jsonGatewayConfig = jsonConfigObject.optJSONObject("gateway");
            if (jsonGatewayConfig == null) {
                throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway"));
            }
            final JSONArray jsonGatewayEnvConfig = jsonGatewayConfig.optJSONArray("env");
            if (jsonGatewayEnvConfig == null || jsonGatewayEnvConfig.length() == 0) {
                throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway.env"));
            }
            for (int index = 0; index < jsonGatewayEnvConfig.length(); index++) {
                final JSONObject jsonGatewayEnvConfigItem = jsonGatewayEnvConfig.getJSONObject(index);
                final String name = jsonGatewayEnvConfigItem.optString("name");
                if (TextUtils.isEmpty(name)) {
                    throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway.env[].name"));
                }
                final String domain = jsonGatewayEnvConfigItem.optString("domain");
                if (TextUtils.isEmpty(name)) {
                    throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway.env[].domain"));
                }
                final String version = jsonGatewayEnvConfigItem.optString("version");
                if (TextUtils.isEmpty(name)) {
                    throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway.env[].version"));
                }
                final String clientId = jsonGatewayEnvConfigItem.optString("clientid");
                if (TextUtils.isEmpty(clientId)) {
                    throw new IllegalArgumentException(String.format(REQUIRED_KEY_IS_MISSING_PATTERN, "gateway.env[].clientid"));
                }
                final Gateway gateway = new Gateway(name, domain, version, clientId);
                mGateways.add(gateway);

            }
            setDefaultGateway();
        } catch (JSONException je) {
            throw new IllegalArgumentException("Cannot parse JSON configuration data from assets", je);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Cannot load configuration file from assets", ioe);
        } finally {
            AutoCloseUtils.autoClose(configInputStream);
        }
    }

    /**
     * Get singleton instance.
     *
     * @param context The context to use.
     * @return The singleton instance.
     */
    public static synchronized AppConfigurationUtils getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new AppConfigurationUtils(context);
        }
        return sInstance;
    }

    /**
     * Get the application scheme.
     *
     * @return The application scheme {@link String}
     */
    public String getAppScheme() {
        return mAppScheme;
    }

    /**
     * Get the active gateway.
     *
     * @return The active gateway or null if no gateway configuration found.
     */
    public Gateway getActiveGateway() {

        if (mActiveGatewayIndex == -1) {
            final SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            mActiveGatewayIndex = preferences.getInt(KEY_GATEWAY_INDEX, 0);
        }

        //mGateways are always contains at least one item if the instance is initialised.
        return mGateways.get(mActiveGatewayIndex);
    }

    /**
     * Get the clients id.
     *
     * @return The client id.
     */
    public String getClientId() {
        return getActiveGateway().getClientId();
    }

    /**
     * Get an unmodifiable list of available gateways.
     *
     * @return The list of available gateways.
     */
    public List<Gateway> getAvailableGateways() {
        return Collections.unmodifiableList(mGateways);
    }

    /**
     * Set the active gateway in the configuration.
     *
     * @param gateway The new active gateway (from the list of available gateways).
     */
    public void setActiveGateway(final Gateway gateway) {
        final int gatewayIndex = mGateways.indexOf(gateway);
        if (gatewayIndex == -1) {
            throw new IllegalArgumentException("Unknown gateway");
        }

        mActiveGatewayIndex = gatewayIndex;
        mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_GATEWAY_INDEX, mActiveGatewayIndex)
                .apply();
    }

    private void setDefaultGateway() {
        final boolean isGatewayNotSet = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(KEY_GATEWAY_INDEX, -1) == -1;
        if (isGatewayNotSet) {
            for (final Gateway gw : mGateways) {
                if (!BuildConfig.DEBUG && "Demo".equalsIgnoreCase(gw.getName())) {
                    setActiveGateway(gw);
                    break;
                }
            }
        }
    }
}
