package com.example.pidev.service.GUIDE;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {String fromNumber="whatsapp:+14155238886"; // <= met ici TON numéro Sandbox Twilio

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;



    public void sendWhatsAppMessage(String toNumber, String body) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + toNumber),
                new PhoneNumber(fromNumber),
                body
        ).create();
    }

    public void sendWhatsAppMessageWithButtons(String phoneNumber, String message, int reservationId) {
        // Construct the message body with action buttons
        String buttonAccept = "action=accept&reservationId=" + reservationId;
        String buttonReject = "action=reject&reservationId=" + reservationId;

        // The message with buttons
        String messageWithButtons = message + "\n\n" +
                "Please select one of the options below to respond:\n" +
                "1. Accept ➡️ " + buttonAccept + "\n" +
                "2. Reject ➡️ " + buttonReject;

        // Send the WhatsApp message using Twilio's API
        Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + phoneNumber),  // Guide's phone number
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),  // Your Twilio WhatsApp number
                        messageWithButtons)
                .create();
    }
}
