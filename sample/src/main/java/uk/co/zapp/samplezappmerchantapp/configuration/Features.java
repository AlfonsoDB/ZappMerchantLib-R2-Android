package uk.co.zapp.samplezappmerchantapp.configuration;

import com.zapp.core.RTPType;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Feature list model object. This object is a non-thread safe singleton.
 *
 * @author msagi
 */
public class Features {

    /**
     * Shared preferences class name.
     */
    private static final String SHARED_PREFS_NAME = Features.class.getSimpleName();

    /**
     * Tag for logging.
     */
    private static final String TAG = Features.class.getSimpleName();

    /**
     * Singleton instance.
     */
    private static Features sInstance;

    /**
     * The application context.
     */
    private final Context mAppContext;

    /**
     * The list of features.
     */
    private final List<Feature> mFeatures = new ArrayList<>();

    /**
     * Create new instance and load saved features if any.
     */
    private Features(final Context appContext) {
        if (appContext == null) {
            throw new IllegalArgumentException("appContext == null");
        }
        mAppContext = appContext;
        load();
    }

    /**
     * Get singleton instance.
     *
     * @return The singleton instance.
     */
    public synchronized static Features getInstance(final Context appContext) {
        if (sInstance == null) {
            sInstance = new Features(appContext);
        }
        return sInstance;
    }


    private synchronized void load() {
        try {
            final FileInputStream fis = mAppContext.openFileInput(SHARED_PREFS_NAME);
            final BufferedInputStream bis = new BufferedInputStream(fis);
            final ObjectInputStream ois = new ObjectInputStream(bis);
            final List<Feature> features = (List<Feature>) ois.readObject();
            ois.close();
            mFeatures.clear();
            mFeatures.addAll(features);
        } catch (Exception e) {
            Log.e(TAG, "Error loading feature list", e);
        }
        Log.e(TAG, "Feature list loaded (size: " + mFeatures.size() + ")");
    }

    private synchronized void save() {
        try {
            final FileOutputStream fos = mAppContext.openFileOutput(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mFeatures);
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "Error saving feature list", e);
        }
        Log.e(TAG, "Feature list saved (size: " + mFeatures.size() + ")");
    }

    /**
     * Get the list of features filtered by RTP type.
     * @param rtpType The payment request type.
     *
     * @return The unmodifiable list of features.
     */
    public List<Feature> getFeaturesByRtpType(final RTPType rtpType) {
        final List<Feature> filteredList = new ArrayList<>();
        for (final Feature feature : mFeatures) {
            if (feature.getPaymentRequest().getRtpType().equals(rtpType)) {
                filteredList.add(feature);
            }
        }
        return filteredList;
    }

    /**
     * Get the feature by its unique id.
     * @param id The id of the feature.
     * @return The feature by its id or null if no feature found by given id.
     */
    public Feature getFeatureById(final int id) {
        for (final Feature feature : mFeatures) {
            if (feature.getId() == id) {
                return feature;
            }
        }
        return null;
    }

    /**
     * Add new feature to the features list.
     *
     * @param feature The feature containing the feature attributes.
     */
    public void addFeature(final Feature feature) {
        if (feature == null) {
            return;
        }
        feature.setId(mFeatures.size());
        mFeatures.add(feature);
        save();
    }

    /**
     * Update an existing feature on the list. It keeps the feature position within the list.
     * @param feature The updated feature to set to the features list.
     */
    public void updateFeature(final Feature feature) {
        if (feature == null) {
            return;
        }
        final int featureIndex = mFeatures.indexOf(feature);
        if (-1 != featureIndex) {
            mFeatures.remove(featureIndex);
            mFeatures.add(featureIndex, feature);
            save();
        }
    }
}
