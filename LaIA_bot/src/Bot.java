import java.util.List;
import java.util.ArrayList;
import static java.lang.Math.toIntExact;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String message_text = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();

			System.out.println(update.getMessage().getFrom().getFirstName() + " " + message_text);

			if (update.getMessage().getText().equals("/start")) {

				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
								+ "Hola, sóc la LaIA, la teva assistent mataronina! En qué et puc ajudar?");
				InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowIncidencia = new ArrayList<>();
				List<InlineKeyboardButton> rowInfo = new ArrayList<>();
				List<InlineKeyboardButton> rowTramits = new ArrayList<>();

				rowIncidencia.add(new InlineKeyboardButton()
						.setText(Emoji.DANGER_SIGN.toString() + "     Incidéncia     " + Emoji.DANGER_SIGN.toString())
						.setCallbackData("incidencia"));
				rowInfo.add(new InlineKeyboardButton().setText(
						Emoji.INFORMATION_SOURCE.toString() + "     Informació    " + Emoji.INFORMATION_SOURCE.toString())
						.setCallbackData("informacio"));
				rowTramits.add(new InlineKeyboardButton()
						.setText(Emoji.INFO.toString() + "      Tràmits      " + Emoji.INFO.toString()).setCallbackData("tramits"));

				// Set the keyboard to the markup

				rowsInline.add(rowIncidencia);
				rowsInline.add(rowInfo);
				rowsInline.add(rowTramits);

				// Add it to the message
				markupInline.setKeyboard(rowsInline);
				message.setReplyMarkup(markupInline);
				try {
					sendMessage(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else {

			}

		} else if (update.hasCallbackQuery()) {
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			long chat_id = update.getCallbackQuery().getMessage().getChatId();

			switch (call_data) {
			case "incidencia":
				showTipusIncidencia(update, chat_id);
				break;
			case "informacio":
				connection.sendPOST();
				break;
			case "tramits":
				getLocationIncidencia(chat_id);
				break;
			}
		}else {
			long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage() // Create a message object object
					.setChatId(chat_id).setText("gracies per enviar ubi:)");
			
			try {
				sendMessage(message);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void showTipusIncidencia(Update update, long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText("Quina incidencia vols tramitar?");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowIncidencia = new ArrayList<>();
		List<InlineKeyboardButton> rowInfo = new ArrayList<>();
		List<InlineKeyboardButton> rowTramits = new ArrayList<>();

		rowTramits.add(new InlineKeyboardButton()
				.setText(Emoji.POLICEMAN.toString() + "      Seguretat/Violència      " + Emoji.DOUBLE_EXCLAMATION.toString()).setCallbackData("seguretat"));
		
		rowInfo.add(new InlineKeyboardButton().setText(
				Emoji.INFORMATION_SOURCE.toString() + "     Accident    " + Emoji.INFORMATION_SOURCE.toString())
				.setCallbackData("accident"));
		
		rowIncidencia.add(new InlineKeyboardButton()
				.setText(Emoji.DANGER_SIGN.toString() + "     Desperfecte Via Pública     " + Emoji.DANGER_SIGN.toString())
				.setCallbackData("desperfecte"));
		
		

		// Set the keyboard to the markup

		rowsInline.add(rowIncidencia);
		rowsInline.add(rowInfo);
		rowsInline.add(rowTramits);

		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void getLocationIncidencia(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Hola, ens pots enviar la teva localitzacio pls?");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

    }

	public String getBotUsername() {
		return null;
	}

	public String getBotToken() {
		return "759391305:AAFRpJ_4kmgfx4t75XLArPtjW8D4AHzo_Qg";
	}

}
