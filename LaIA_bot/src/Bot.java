import java.util.List;

import java.util.ArrayList;
import static java.lang.Math.toIntExact;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Location;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

	String imgURL;
	String descripcio;
	String location;
	String tipus;
	long chat_id;

	int cua = 0;

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText() && cua==0) {
			String message_text = update.getMessage().getText();
			chat_id = update.getMessage().getChatId();

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
				rowInfo.add(new InlineKeyboardButton().setText(Emoji.INFORMATION_SOURCE.toString()
						+ "     Informaci�    " + Emoji.INFORMATION_SOURCE.toString()).setCallbackData("informacio"));
				rowTramits.add(new InlineKeyboardButton()
						.setText(Emoji.INFO.toString() + "      Tr�mits      " + Emoji.INFO.toString())
						.setCallbackData("tramits"));

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
                showTipusInformacio(update, chat_id);
                break;
            case "tramits":
                showTramits(chat_id);
                break;
            case "seguretat":
            	tipus = "seguretat";
            	showDescripcio(chat_id);
                break;
            case "accident":
            	tipus = "accident";
                showDescripcio(chat_id);
                break;
            case "desperfecte":
            	tipus = "desperfecte";
            	showDescripcio(chat_id);
                break;
            case "autobus":
                showAutobusOpcions(update, chat_id);
                break;
            case "horaris":
                showHoraris(update, chat_id);
                break;
            case "parades":
                showParada(update, chat_id);
                break;
            case "zonablava":
                showZonaBlava(update, chat_id);
                break;
            case "taxi":
                showZonaTaxi(update, chat_id);
                break;
            case "equipaments":
                showEquipaments(update, chat_id);
                break;
            }
		} else {
			chat_id = update.getMessage().getChatId();
			if(cua == 1) {
				descripcio = update.getMessage().getText();
				cua++;
				showLocation(chat_id);
			}
			else if (cua == 2) {
				location = (update.getMessage().getLocation().getLatitude().toString() + ":" + update.getMessage().getLocation().getLongitude().toString());
				cua++;
				showFoto(chat_id);
			}else if (cua == 3) {
				imgURL = update.getMessage().getPhoto().toString();
				cua++;
				showOk(chat_id);
				
			}
		}
		
		if(cua==4) {
			cua = 0;
			connection.sendPOST(tipus, descripcio, location, imgURL);
			System.out.println("incidencia ended "+cua);
		}else 
			System.out.println(cua);
	}
	
	private void showEquipaments(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Aquests s�n els equipaments/instal�lacions m�s propers: \n"+
                        "    - Fundaci� Tecnocampus Matar�-Maresme: https://tinyurl.com/y2cn97cw \n"+
                        "    - Eurecat - Centre Tecnol�gic de Catalunya: https://tinyurl.com/y6xj4c6z \n");

        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	private void showHoraris(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Aquests s�n els horaris de l'autob�s de Matar�: \n"+
                        "http://www.matarobus.cat/ca/horaris-i-recorreguts");

        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	private void showParada(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "La parada m�s propera a la teva ubicaci� [Tecnocampus-Matar�] �s: \n"+
                        "http://maps.google.com/maps?saddr=(41.5285636%2C2.4340940)&daddr=Porta%20Laietana-Tecnocampus&geocode=FfOseQIdLiQlAA%3D%3D%3BFWO0eQId7ColACkj38Oq5rSkEjEOPDsSMl6dhQ%3D%3D&dirflg=w");

        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	private void showZonaBlava(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Aquesta �s la zona blava m�s propera a la teva ubicaci� [Tecnocampus-matar�]: \n"+
                        "");

        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	private void showZonaTaxi(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Aquesta �s la zona de taxis m�s propera a la teva ubicaci� [Tecnocampus-matar�]: \n"+
                        "");

        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	

	private void showTipusIncidencia(Update update, long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText("Quina incid�ncia vols tramitar?");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowIncidencia = new ArrayList<>();
		List<InlineKeyboardButton> rowInfo = new ArrayList<>();
		List<InlineKeyboardButton> rowTramits = new ArrayList<>();

		rowTramits.add(new InlineKeyboardButton().setText(
				Emoji.POLICEMAN.toString() + "      Seguretat/Viol�ncia      " + Emoji.DOUBLE_EXCLAMATION.toString())
				.setCallbackData("seguretat"));

		rowInfo.add(new InlineKeyboardButton()
				.setText(
						Emoji.INFORMATION_SOURCE.toString() + "     Accident    " + Emoji.INFORMATION_SOURCE.toString())
				.setCallbackData("accident"));

		rowIncidencia.add(new InlineKeyboardButton().setText(
				Emoji.DANGER_SIGN.toString() + "     Desperfecte Via P�blica     " + Emoji.DANGER_SIGN.toString())
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
	
	public void showDescripcio(long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
						+ "Escriu una breu descripci� de la incid�ncia (en un mateix missatge).");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		cua = 1;
	}
	
	public void showLocation(long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
						+ "Envia'm la ubicaci� de la incid�ncia.");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showFoto(long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
						+ "Envia'm una foto de la incid�ncia.");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showOk(long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
						+ "Gr�cies per reportar la incid�ncia, es far� la gesti� corresponent");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
	
	private void showTipusInformacio(Update update, long chat_id) {
		SendMessage message = new SendMessage() // Create a message object object
				.setChatId(chat_id)
				.setText("Necessites saber on est� la parada de bus m�s propera? "
						+ "Vols saber on es trobar la zona blava m�s a prop? "
						+ "O vols saber on es troba la zona de taxis m�s propera?\n" + "");
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowAutob�s = new ArrayList<>();
		List<InlineKeyboardButton> rowZonaBlava = new ArrayList<>();
		List<InlineKeyboardButton> rowZonaTaxi = new ArrayList<>();
		List<InlineKeyboardButton> rowEquipaments = new ArrayList<>();

		rowAutob�s.add(new InlineKeyboardButton()
				.setText(Emoji.POLICEMAN.toString() + "      Autob�s      " + Emoji.DOUBLE_EXCLAMATION.toString())
				.setCallbackData("autobus"));

		rowZonaBlava.add(new InlineKeyboardButton().setText(
				Emoji.INFORMATION_SOURCE.toString() + "     Zona Blava    " + Emoji.INFORMATION_SOURCE.toString())
				.setCallbackData("zonablava"));

		rowZonaTaxi.add(new InlineKeyboardButton()
				.setText(Emoji.DANGER_SIGN.toString() + "     Zona Taxi     " + Emoji.DANGER_SIGN.toString())
				.setCallbackData("taxi"));

		rowEquipaments.add(new InlineKeyboardButton()
				.setText(Emoji.DANGER_SIGN.toString() + "     Equipament     " + Emoji.DANGER_SIGN.toString())
				.setCallbackData("equipaments"));

		// Set the keyboard to the markup

		rowsInline.add(rowAutob�s);
		rowsInline.add(rowZonaBlava);
		rowsInline.add(rowZonaTaxi);
		rowsInline.add(rowEquipaments);

		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void showAutobusOpcions(Update update, long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Vols consultar els horaris? O potser vols consultar la parada m�s propera? ");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowHoraris = new ArrayList<>();
        List<InlineKeyboardButton> rowParades = new ArrayList<>();

        rowHoraris.add(new InlineKeyboardButton()
                .setText(Emoji.POLICEMAN.toString() + "      Horaris      " + Emoji.DOUBLE_EXCLAMATION.toString())
                .setCallbackData("horaris"));

        rowParades.add(new InlineKeyboardButton()
                .setText(Emoji.INFORMATION_SOURCE.toString() + "     Parades    " + Emoji.INFORMATION_SOURCE.toString())
                .setCallbackData("parades"));

        // Set the keyboard to the markup

        rowsInline.add(rowHoraris);
        rowsInline.add(rowParades);

        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        try {
            sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
	
	private void showSeguretat(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Actualment aquest servei no est� disponible a LaIA. En cas d'haver patit un problema relacionat amb la seguretat al teu barri o de car�cter violent, preguem et comuniquis r�pidament amb el 112.");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

    }
	
	private void showInforme(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "La info no est� disponible");

        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> btnSi = new ArrayList<>();
		List<InlineKeyboardButton> btnNo = new ArrayList<>();

		btnSi.add(new InlineKeyboardButton()
				.setText("Si")
				.setCallbackData("seguretat"));
		
		btnNo.add(new InlineKeyboardButton().setText("No")
				.setCallbackData("accident"));
		
		

		// Set the keyboard to the markup

		rowsInline.add(btnSi);
		rowsInline.add(btnNo);

		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
        
        
		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

    }
		
	private void showTramits(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Aquest servei no est� disponible a LaIA actualment. El nostre equip de desenvolupadors est� treballant per tenir-lo disponible el m�s aviat possible. ?? Molt aviat podr�s realitzar tr�mits com demanar perm�s obres, demanar perm�s d'estacionament per a mudances o reservar espais p�blics amb LaIA. De mentre, que desitges fer?");

		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

    }
	
	private void showDesperfecte(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Envia un breu resum de la incid�ncia, adjunta una fotografia i situa't prop del desperfecte per compartir la ubicaci�. En breus, mobilitzarem els mitjans necessaris per solucionar-ho");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> btnSi = new ArrayList<>();
		List<InlineKeyboardButton> btnNo = new ArrayList<>();

		btnSi.add(new InlineKeyboardButton()
				.setText("Si")
				.setCallbackData("seguretat"));
		
		btnNo.add(new InlineKeyboardButton().setText("No")
				.setCallbackData("accident"));
		
		

		// Set the keyboard to the markup

		rowsInline.add(btnSi);
		rowsInline.add(btnNo);

		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);

        
        
		try {
			sendMessage(message); // Sending our message object to user
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

    }
	
	private void showAccidents(long chat_id) {

        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id).setText(Emoji.HAPPY_PERSON_RAISING_ONE_HAND.toString()
                        + "Actualment aquest servei no est� disponible a LaIA. En cas d'haver patit un accident o un incendi, preguem que et posis en contacte r�pidament amb el 112.");

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
		return "756164509:AAEUFxbu8lEzs06jvf5UUrl5qUxT3t9PeeM";
	}

}
