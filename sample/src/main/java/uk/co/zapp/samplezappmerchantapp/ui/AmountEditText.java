package uk.co.zapp.samplezappmerchantapp.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * A custom edit text which prepends a pound sign to the text.
 */
public class AmountEditText extends EditText {

    /**
     * The max length value.
     */
    private static final int MAX_LENGTH = 18;

    /**
     * The unicode value of pound sign.
     */
    private static final String POUND = "\u00A3";

    /**
     * Used as a hint when the input field is empty.
     */
    public static final String PLACEHOLDER = "N/A";

    /**
     * True if the view has the focus.
     */
    private boolean mHasFocus;

    /**
     * A flag which indicates if a placeholder is needed to work as a hint.
     */
    private boolean mIsPlaceholderRequired;

    /**
     * The edit texts value.
     */
    private String mValue;

    /**
     * Creates a new AmountEditText.
     *
     * @param context The view context.
     */
    public AmountEditText(final Context context) {
        super(context);
        setFilters();
    }

    /**
     * Creates a new AmountEditText with AttributeSet.
     *
     * @param context The view context.
     * @param attrs   The styled attributes.
     */
    public AmountEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setFilters();
    }

    /**
     * Creates a new AmountEditText.
     *
     * @param context The view context.
     * @param attrs   The styled attributes.
     */
    public AmountEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFilters();
    }

    @Override
    protected void onSelectionChanged(final int selStart, final int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //don't allow the cursor to go before pound sign
        if (selStart == 0 && getText().length() > 0) {
            setSelection(1, 1);
        }
    }

    @Override
    protected void onFocusChanged(final boolean focused, final int direction, final Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        mHasFocus = focused;
        if (mHasFocus) {
            if (mIsPlaceholderRequired && !TextUtils.isEmpty(mValue) && PLACEHOLDER.equals(mValue)) {
                setText("");
            }
            addPoundPrefix();
        } else if (POUND.equals(mValue)) {
            setText("");
            if (mIsPlaceholderRequired && TextUtils.isEmpty(mValue)) {
                setText(PLACEHOLDER);
            }
        } else if (mIsPlaceholderRequired && TextUtils.isEmpty(mValue)) {
            setText(PLACEHOLDER);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mValue = text.toString();
        if (mHasFocus) {
            addPoundPrefix();
        }
    }

    /**
     * Add pound prefix if it doesn't exists before amount value.
     */
    public void addPoundPrefix() {
        if (!mValue.contains(PLACEHOLDER)) {
            if (!mValue.contains(POUND) || !POUND.equals(String.valueOf(mValue.charAt(0)))) {
                mValue = POUND + mValue;
                setText(mValue);
            }
        }
    }

    /**
     * Sets the mIsPlaceholderRequired value.
     *
     * @param isPlaceholderRequired True if required.
     */
    public void setIsPlaceholderRequired(final boolean isPlaceholderRequired) {
        this.mIsPlaceholderRequired = isPlaceholderRequired;
    }

    /**
     * Removes the placeholder and pound sign if they exists and returns only the amount value.
     *
     * @return The amount mValue.
     */
    public String getSimpleText() {
        if (TextUtils.isEmpty(mValue)) {
            return null;
        }
        if (mValue.contains(PLACEHOLDER)) {
            return removePrefix(PLACEHOLDER);
        } else if (mValue.contains(POUND)) {
            return removePrefix(POUND);
        }
        return mValue;
    }

    /**
     * Removes the incoming prefix from value and returns the result.
     *
     * @param prefix The string which should be removed from the value.
     * @return value without prefix.
     */
    @NonNull
    private String removePrefix(@NonNull final String prefix) {
        return mValue.substring(mValue.indexOf(prefix) + prefix.length(), mValue.length());
    }

    /**
     * Sets the view's filters.
     */
    private void setFilters() {
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
    }
}
