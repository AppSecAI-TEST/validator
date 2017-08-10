package com.johnpetitto.validator;

import android.util.Patterns;

public final class Validators {
    public static final Validator EMAIL;
    public static final Validator PHONE;

    static {
        EMAIL = new Validator() {
            @Override
            public boolean isValid(String input) {
                return Patterns.EMAIL_ADDRESS.matcher(input).matches();
            }
        };

        PHONE = new Validator() {
            @Override
            public boolean isValid(String input) {
                return Patterns.PHONE.matcher(input).matches();
            }
        };
    }

    private Validators() {
        throw new AssertionError("No instances");
    }

    public static boolean validate(ValidatingTextInputLayout... layouts) {
        boolean allInputsValid = true;

        for (ValidatingTextInputLayout layout : layouts) {
            if (!layout.validate()) {
                allInputsValid = false;
            }
        }

        return allInputsValid;
    }

    public static Validator minimum(final int length) {
        return minimum(length, false);
    }

    public static Validator minimum(final int length, final boolean trim) {
        return new Validator() {
            @Override
            public boolean isValid(String input) {
                if (trim) {
                    input = input.trim();
                }

                return length <= input.length();
            }
        };
    }
}