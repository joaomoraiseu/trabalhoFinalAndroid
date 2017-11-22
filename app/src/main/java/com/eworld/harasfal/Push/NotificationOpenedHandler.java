package com.eworld.harasfal.Push;


import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import java.util.ArrayList;

/**
 * Created by evox on 03/08/16.
 */
public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {


    public NotificationOpenedHandler() {
        if ( Badge.MENU_POSITION_BADGE ==null) {
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
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
/*
        if (additionalData != null) {
            if (additionalData.has("badgeMenu")) {
                try {
                    //positionMenuBadge = additionalData.getInt("badgeMenu");
                    //Badge.MENU_POSITION_BADGE.set(positionMenuBadge,true);
                    NotificationOpen.MENU_OPEN = additionalData.getInt("badgeMenu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (additionalData.has("badgeCategorias")){
                try {
                    //idCategoriaBadge = additionalData.getInt("badgeCategorias");
                    //AGENDAMENTO_ID_BADGE.add(idCategoriaBadge);
                    NotificationOpen.CATEGORIA_ID_OPEN = additionalData.getInt("badgeCategorias");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }


}
