import java.util.List;
import java.util.ArrayList;
import static java.lang.Math.toIntExact;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
								+ "Hola, s�c la LaIA, la teva assistent mataronina! En qu� et puc ajudar?");
				InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowIncidencia = new ArrayList<>();
				List<InlineKeyboardButton> rowInfo = new ArrayList<>();
				List<InlineKeyboardButton> rowTramits = new ArrayList<>();

				rowIncidencia.add(new InlineKeyboardButton()
						.setText(Emoji.DANGER_SIGN.toString() + "     Incid�ncia     " + Emoji.DANGER_SIGN.toString())
						.setCallbackData("incidencia"));
				rowInfo.add(new InlineKeyboardButton().setText(
						Emoji.INFORMATION_SOURCE.toString() + "     Informaci�    " + Emoji.INFORMATION_SOURCE.toString())
						.setCallbackData("informacio"));
				rowTramits.add(new InlineKeyboardButton()
						.setText(Emoji.INFO.toString() + "      Tr�mits      " + Emoji.INFO.toString()).setCallbackData("tramits"));

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
				showTipusIncidencia();
				break;
			case "informacio":

				break;
			case "tramits":

				break;
			}
		}
	}

	private void showTipusIncidencia(long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
						+ "Hola, s�c la LaIA, la teva assistent mataronina! En qu� et puc ajudar?");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowIncidencia = new ArrayList<>();
		List<InlineKeyboardButton> rowInfo = new ArrayList<>();
		List<InlineKeyboardButton> rowTramits = new ArrayList<>();

		rowIncidencia.add(new InlineKeyboardButton()
				.setText(Emoji.DANGER_SIGN.toString() + "     Brutissia     " + Emoji.DANGER_SIGN.toString())
				.setCallbackData("incidencia"));
		rowInfo.add(new InlineKeyboardButton().setText(
				Emoji.INFORMATION_SOURCE.toString() + "     Accident    " + Emoji.INFORMATION_SOURCE.toString())
				.setCallbackData("informacio"));
		rowTramits.add(new InlineKeyboardButton()
				.setText(Emoji.INFO.toString() + "      Seguretat      " + Emoji.INFO.toString()).setCallbackData("tramits"));

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

	public String getBotUsername() {
		return null;
	}

	public String getBotToken() {
		return "759391305:AAFRpJ_4kmgfx4t75XLArPtjW8D4AHzo_Qg";
	}

}