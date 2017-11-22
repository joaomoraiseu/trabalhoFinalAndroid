package com.eworld.harasfal.Push;

/**
 * Created by evox on 02/08/16.
 */

import android.content.SharedPreferences;

import com.eworld.harasfal.Classes.DadosEmpresa;
import com.eworld.harasfal.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NotificationExtenderBareBonesExample extends NotificationExtenderService {

    int positionMenuBadge;
    int idOrcamentoBadge;
    int idCategoriaBadge;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private void IncrementBadge() {
        preferences = getSharedPreferences(getResources().getString(R.string.sharedpreferences), 0);
        editor = preferences.edit();
        int Badges = preferences.getInt("Badges", 0);
        Badges++;
        editor.putInt("Badges", Badges);
        editor.commit();
    }

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {


        if (Badge.MENU_POSITION_BADGE == null) {
            Badge.MENU_POSITION_BADGE = new ArrayList<Boolean>();
            Badge.MENU_POSITION_BADGE.add(0, false);
            Badge.MENU_POSITION_BADGE.add(1, false);
            Badge.MENU_POSITION_BADGE.add(2, false);
            Badge.MENU_POSITION_BADGE.add(3, false);
            Badge.MENU_POSITION_BADGE.add(4, false);
            Badge.MENU_POSITION_BADGE.add(5, false);
        }
        if (Badge.ORCAMENTO_ID_BADGE == null)
            Badge.ORCAMENTO_ID_BADGE = new ArrayList<Integer>();

        if (Badge.CATEGORIA_ID_BADGE == null)
            Badge.CATEGORIA_ID_BADGE = new ArrayList<Integer>();

        JSONObject additionalData = notification.payload.additionalData;
        if (additionalData.has("badgeCategoria")) {
            try {
                idCategoriaBadge = additionalData.getInt("badgeCategoria");
                if (idCategoriaBadge!=DadosEmpresa.IdCategVendas)
                    Badge.CATEGORIA_ID_BADGE.add(idCategoriaBadge);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (additionalData.has("badgeMenu")) {
            try {
                positionMenuBadge = additionalData.getInt("badgeMenu");
                if (positionMenuBadge == 2) {

                    if (idCategoriaBadge== DadosEmpresa.IdCategVendas) {
                        positionMenuBadge = 3;
                    }

                }
                Badge.MENU_POSITION_BADGE.set(positionMenuBadge, true);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (additionalData.has("OrcamentoId")) {
                try {
                    idOrcamentoBadge = additionalData.getInt("OrcamentoId");
                    Badge.ORCAMENTO_ID_BADGE.add(idOrcamentoBadge);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       // }
        // Sets the background notification color to Green on Android 5.0+ devices.
        //return builder.setColor(new BigInteger("FF00FF00", 16).intValue());
        return false;
    }


}