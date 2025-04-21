package org.example.quasiproverb.bot;

import org.example.quasiproverb.service.TxtProverbService;
import org.example.quasiproverb.service.TxtProverbService.ProverbBlock;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class QuasiProverbBot extends TelegramLongPollingBot {

    private final TxtProverbService proverbService;

    public QuasiProverbBot(TxtProverbService proverbService) {
        this.proverbService = proverbService;
    }

    @Override
    public String getBotUsername() {
        return "quasiproverb007_bot";
    }

    @Override
    public String getBotToken() {
        return "7942557790:AAF_vYfgyCPhb22Rt37bzQ2iYHBFHYF1Grs";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText().trim();

            String reply;

            switch (message) {
                case "/start" -> reply = "Привет! Я превращаю пословицы в шутки 🤓\nНапиши /proverb — или начни фразу пословицей.";
                case "/proverb" -> reply = proverbService.getRandomOriginalAndOneFunny();
                default -> {
                    var match = proverbService.findByFirstWordMatch(message);
                    if (match.isPresent()) {
                        ProverbBlock block = match.get();
                        StringBuilder sb = new StringBuilder("🧠 " + block.original + "\n");
                        for (String f : block.funny) {
                            sb.append("🤣 ").append(f).append("\n");
                        }
                        reply = proverbService.getFunnyByOriginalStart(message);

                    } else {
                        reply = "Не нашёл такую пословицу 🤔\nПопробуй точнее или напиши /proverb.";
                    }
                }
            }

            try {
                execute(new SendMessage(chatId, reply));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
