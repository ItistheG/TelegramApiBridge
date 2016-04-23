package org.javagram.core;

import org.telegram.api.engine.TelegramApi;

/**
 * Created by Danya on 28.02.2016.
 */
public class StaticContainer
{
    private static TelegramApi telegramApi;

    public synchronized static TelegramApi getTelegramApi() {
        return telegramApi;
    }

    public synchronized static void setTelegramApi(TelegramApi telegramApi) {
        if(telegramApi != null && StaticContainer.telegramApi != null) {
            throw new IllegalStateException("Only one ApiBridge allowed at time");
        }
        StaticContainer.telegramApi = telegramApi;
    }
}
