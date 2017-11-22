package com.eworld.harasfal.Utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by evox on 27/03/17.
 */


public class MaskUtil {

    private static final String CPFMask = "###.###.###-##";
    private static final String RGMask = "##.###.###";
    private static final String PINMask = "####-####-####-####";
    private static final String CEPMask = "#####-######";
    private static final String TELMask = "(##)###########";
    private static final String DATANASCMask = "##/##/####";
    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }


    public static TextWatcher insert(final EditText editText, final String maskType) {
        return new TextWatcher() {

            boolean isUpdating;
            String oldValue = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = MaskUtil.unmask(s.toString());
                String mask;

                switch (maskType) {
                    case "CPF":
                        mask = CPFMask;
                        break;
                    case "RG":
                        mask = RGMask;
                        break;
                    case "PIN":
                        mask = PINMask;
                        break;
                    case "TEL":
                        mask = TELMask;
                        break;
                    case "CEP":
                        mask = CEPMask;
                        break;
                    case "DATANASC":
                        mask = DATANASCMask;
                        break;

                    default:
                        mask = CPFMask;
                        break;
                }

                String maskAux = "";
                if (isUpdating) {
                    oldValue = value;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && value.length() > oldValue.length()) || (m != '#' && value.length() < oldValue.length() && value.length() != i)) {
                        maskAux += m;
                        continue;
                    }

                    try {
                        maskAux += value.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(maskAux);
                editText.setSelection(maskAux.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}