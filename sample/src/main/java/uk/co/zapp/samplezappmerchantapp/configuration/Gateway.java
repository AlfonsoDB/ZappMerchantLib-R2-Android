package uk.co.zapp.samplezappmerchantapp.configuration;

import android.text.TextUtils;

/**
 * Gateway data class.
 *
 * @author msagi
 */
public class Gateway {

    /**
     * The name of the gateway to be displayed (e.g. in chooser)
     */
    private String name;

    /**
     * The version of the gateway.
     */
    private String version;

    /**
     * The domain of the gateway.
     */
    private String domain;

    /**
     * Create new instance.
     *
     * @param name    The name of the gateway.
     * @param domain  The domain of the gateway.
     * @param version The version of the gateway.
     */
    public Gateway(final String name, final String domain, final String version) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty");
        }
        this.name = name;
        if (TextUtils.isEmpty(domain)) {
            throw new IllegalArgumentException("domain is empty");
        }
        this.domain = domain;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    /**
     * Get the endpoint built from the domain and version fields.
     */
    public String getEndpoint() {
        return String.format("http://%s%s", domain, version);
    }

    @Override
    public String toString() {
        return String.format("%s (v%s)", name, version);
    }
}
