package tutorial;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {
    Set<String> films = new HashSet<String>();

    public String getBotUsername() {
        return "TutorialBot";
    }

    @Override
    public String getBotToken() {
        return "7641801953:AAFNbzraA6O41AZPCdscuQQiSLdl--ZKAfs";
    }


    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String message = msg.getText();//сообщение пользователя
        User user = msg.getFrom(); //сам пользователь
        Long id = user.getId(); // id пользователя
        String userName = user.getUserName(); //юзернейм пользователя

        //это ответ бота
        String answer = "";


        // /добавить "название_фильма"
        // /список - показываем все его фильмы
        if (message.equalsIgnoreCase("/список")) {
            answer = "Вот список ваших фильмов" + films;
        } else if (message.startsWith("/добавить")) {
            String[] commendAndFilm = message.split(" ");
            films.add(commendAndFilm[1]);
        } else if (message.startsWith("/удалить")) {
            String[] commendAndFilm = message.split(" ");
            films.remove(commendAndFilm[1]);
        }
        

        //оставляем как есть
        SendMessage sm = SendMessage.builder()
                .chatId(id.toString())
                .text(answer) //что будет отвечать бот
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
