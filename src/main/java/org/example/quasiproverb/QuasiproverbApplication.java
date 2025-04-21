package org.example.quasiproverb;

import org.example.quasiproverb.bot.QuasiProverbBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class QuasiproverbApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuasiproverbApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(QuasiProverbBot bot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api;
    }
}
