package com.johnpetitto.validator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;

public class ValidatingTextInputLayout extends TextInputLayout {
    private static final int EMAIL_VALIDATOR = 1;
    private static final int PHONE_VALIDATOR = 2;

    private Validator validator;
    private CharSequence errorLabel;

    public ValidatingTextInputLayout(Context context) {
        this(context, null);
    }

    public ValidatingTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValidatingTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setErrorEnabled(true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ValidatingTextInputLayout);

        errorLabel = a.getString(R.styleable.ValidatingTextInputLayout_errorLabel);

        int validatorValue = a.getInt(R.styleable.ValidatingTextInputLayout_validator, 0);
        switch (validatorValue) {
            case EMAIL_VALIDATOR:
                validator = Validators.EMAIL;
                break;
            case PHONE_VALIDATOR:
                validator = Validators.PHONE;
                break;
        }

        a.recycle();
    }

    @Override
    public final void setErrorEnabled(boolean enabled) {
        super.setErrorEnabled(enabled);
    }

    @Override
    public final void setError(@Nullable CharSequence error) {
        super.setError(error);
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setErrorLabel(CharSequence label) {
        errorLabel = label;
    }

    public boolean validate() {
        if (validator == null) {
            throw new IllegalStateException("A Validator must be set; call setValidator first");
        }

        CharSequence input = "";
        EditText editText = getEditText();
        if (editText != null) {
            input = editText.getText();
        }

        boolean valid = validator.isValid(input.toString());
        if (valid) {
            setError(null);
        } else {
            if (errorLabel == null) {
                throw new IllegalStateException("An error label must be set when validating an " +
                        "invalid input; call setErrorLabel or app:errorLabel first.");
            }
            setError(errorLabel);
        }

        return valid;
    }
}