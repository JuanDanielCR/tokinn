package com.escom.tokinn.controller;

import static com.github.messenger4j.MessengerPlatform.CHALLENGE_REQUEST_PARAM_NAME;
import static com.github.messenger4j.MessengerPlatform.MODE_REQUEST_PARAM_NAME;
import static com.github.messenger4j.MessengerPlatform.SIGNATURE_HEADER_NAME;
import static com.github.messenger4j.MessengerPlatform.VERIFY_TOKEN_REQUEST_PARAM_NAME;

import com.escom.tokinn.constantes.Bundle;
import com.escom.tokinn.constantes.NavigationConstants;
import com.escom.tokinn.entity.Usuario;
import com.escom.tokinn.services.TokenService;
import com.escom.tokinn.services.UsuarioService;
import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.receive.events.AccountLinkingEvent.AccountLinkingStatus;
import com.github.messenger4j.receive.events.AttachmentMessageEvent.Attachment;
import com.github.messenger4j.receive.events.AttachmentMessageEvent.AttachmentType;
import com.github.messenger4j.receive.events.AttachmentMessageEvent.Payload;
import com.github.messenger4j.receive.handlers.AccountLinkingEventHandler;
import com.github.messenger4j.receive.handlers.AttachmentMessageEventHandler;
import com.github.messenger4j.receive.handlers.EchoMessageEventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.github.messenger4j.receive.handlers.MessageDeliveredEventHandler;
import com.github.messenger4j.receive.handlers.MessageReadEventHandler;
import com.github.messenger4j.receive.handlers.OptInEventHandler;
import com.github.messenger4j.receive.handlers.PostbackEventHandler;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.QuickReply;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import com.github.messenger4j.send.buttons.Button;
import com.github.messenger4j.send.templates.ButtonTemplate;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.send.templates.ReceiptTemplate;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping("/callback")
@SessionAttributes("userData")
public class MessengerController {
	
	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	private static final String RESOURCE_URL = "https://raw.githubusercontent.com/fbsamples/messenger-platform-samples/master/node/public";
	private static final Log logger = LogFactory.getLog(MessengerController.class);
    
	private final MessengerReceiveClient receiveClient;
    private final MessengerSendClient sendClient;

    /**
     * Constructs the {@code MessengerPlatformCallbackHandler} and initializes the {@code MessengerReceiveClient}.
     * @param appSecret   the {@code Application Secret} obtained from  project config
     * @param verifyToken the {@code Verification Token} that has been provided by you during the setup of the {@code Webhook}
     * @param sendClient  the initialized {@code MessengerSendClient}
     */
    @Autowired
    public MessengerController(@Value("${messenger4j.appSecret}") final String appSecret,
                               @Value("${messenger4j.verifyToken}") final String verifyToken,
                               final MessengerSendClient sendClient) {
        logger.debug("Initializing MessengerReceiveClient - appSecret: "+appSecret+" | verifyToken: "+ verifyToken);
        this.receiveClient = MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken)
                .onTextMessageEvent(newTextMessageEventHandler())
                .onAttachmentMessageEvent(newAttachmentMessageEventHandler())
                .onQuickReplyMessageEvent(newQuickReplyMessageEventHandler())
                .onPostbackEvent(newPostbackEventHandler())
                .onAccountLinkingEvent(newAccountLinkingEventHandler())
                .onOptInEvent(newOptInEventHandler())
                .onEchoMessageEvent(newEchoMessageEventHandler())
                .onMessageDeliveredEvent(newMessageDeliveredEventHandler())
                .onMessageReadEvent(newMessageReadEventHandler())
                .fallbackEventHandler(newFallbackEventHandler())
                .build();
        this.sendClient = sendClient;
    }

    /**
     * Webhook verification endpoint.
     * The passed verification token (as query parameter) must match the configured verification token.
     * In case this is true, the passed challenge string must be returned by this endpoint.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) final String mode,
                                                @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) final String verifyToken,
                                                @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) final String challenge) {
        logger.debug("Received Webhook verification request - mode: "+mode+" | verifyToken: "+verifyToken+" | challenge: "+challenge);
        try {
            return ResponseEntity.ok(this.receiveClient.verifyWebhook(mode, verifyToken, challenge));
        } catch (MessengerVerificationException e) {
            logger.warn("Webhook verification failed: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Callback endpoint responsible for processing the inbound messages and events.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> handleCallback(@RequestBody final String payload,
                                               @RequestHeader(SIGNATURE_HEADER_NAME) final String signature) {
        logger.debug("Received Messenger Platform callback - payload: "+payload+" | signature: "+ signature);
        try {
            this.receiveClient.processCallbackPayload(payload, signature);
            logger.debug("Processed callback payload successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            logger.warn("Processing of callback payload failed: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private void sendTokenMessage(String recipientId, String text, String tipoToken) {
        try {
        	final Recipient recipient = Recipient.newBuilder().recipientId(recipientId).build();
            final NotificationType notificationType = NotificationType.REGULAR;
            final String metadata = "DEVELOPER_DEFINED_METADATA";
            
        	Usuario entidad = usuarioService.findByIdMessenger(recipientId);
        	if(entidad != null) {
        		this.sendClient.sendTextMessage(recipient, notificationType,tokenService.generateToken(entidad.getIdFacebook(), tipoToken), metadata);
        	}else {
        		this.sendClient.sendTextMessage(recipient, notificationType, Bundle.MSG_SIN_VINCULACION, metadata);
        	}
        } catch (MessengerApiException | MessengerIOException e) {
            handleSendException(e);
        }
    }
    
    private TextMessageEventHandler newTextMessageEventHandler() {
        return event -> {
            final String messageId = event.getMid();
            final String messageText = event.getText();
            final String senderId = event.getSender().getId();
            final Date timestamp = event.getTimestamp();
            System.out.println("messageId: "+messageId+" senderId: "+senderId+" received messageText: "+messageText+" at: "+timestamp);
            try {
            	if(messageText.startsWith("vink_")) {
            		if(usuarioService.vincularIdMessenger(messageText.substring(5), event.getSender().getId())) {
            			sendTextMessage(senderId, Bundle.MSG_VINCULACION);
            		}else{
            			sendTextMessage(senderId, Bundle.MSG_ERROR_VINCULACION);
            		}
            	}else {
            		switch (messageText.toLowerCase()) {
	                case "token_inicio":
	                    sendTokenMessage(senderId, messageText, NavigationConstants.TOKEN_INICIO);
	                    break;
	                case "token_estado":
	                    sendTokenMessage(senderId, messageText, NavigationConstants.TOKEN_ESTADO);
	                    break;
	                case "token_transaccion":
	                    sendTokenMessage(senderId, messageText, NavigationConstants.TOKEN_TRANSACCION);
	                    break;
                    case "image":
                        sendImageMessage(senderId);
                        break;
                    case "gif":
                        sendGifMessage(senderId);
                        break;
                    case "audio":
                        sendAudioMessage(senderId);
                        break;
                    case "video":
                        sendVideoMessage(senderId);
                        break;
                    case "file":
                        sendFileMessage(senderId);
                        break;
                    case "button":
                        sendButtonMessage(senderId);
                        break;
                    case "generic":
                        sendGenericMessage(senderId);
                        break;
                    case "receipt":
                        sendReceiptMessage(senderId);
                        break;
                    case "quick reply":
                        sendQuickReply(senderId);
                        break;
                    case "read receipt":
                        sendReadReceipt(senderId);
                        break;
                    case "typing on":
                        sendTypingOn(senderId);
                        break;
                    case "typing off":
                        sendTypingOff(senderId);
                        break;
                    case "Fin":
                    	sendTextMessage(senderId, "¡Gracias y continúa disfrutando de EXPO-ESCOM!");
                        break;
                    default:
                        sendTextMessage(senderId, messageText);
            		}
            	}
                
            } catch (MessengerApiException | MessengerIOException e) {
                handleSendException(e);
            }
        };
    }

    private void sendImageMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendImageAttachment(recipientId, RESOURCE_URL + "/assets/rift.png");
    }

    private void sendGifMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendImageAttachment(recipientId, "https://media.giphy.com/media/11sBLVxNs7v6WA/giphy.gif");
    }

    private void sendAudioMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendAudioAttachment(recipientId, RESOURCE_URL + "/assets/sample.mp3");
    }

    private void sendVideoMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendVideoAttachment(recipientId, RESOURCE_URL + "/assets/allofus480.mov");
    }

    private void sendFileMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendFileAttachment(recipientId, RESOURCE_URL + "/assets/test.txt");
    }

    private void sendButtonMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<Button> buttons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/rift/").toList()
                .addPostbackButton("Trigger Postback", "DEVELOPER_DEFINED_PAYLOAD").toList()
                .addCallButton("Call Phone Number", "+16505551234").toList()
                .build();

        final ButtonTemplate buttonTemplate = ButtonTemplate.newBuilder("Tap a button", buttons).build();
        this.sendClient.sendTemplate(recipientId, buttonTemplate);
    }

    private void sendGenericMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<Button> riftButtons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/rift/").toList()
                .addPostbackButton("Call Postback", "Payload for first bubble").toList()
                .build();

        final List<Button> touchButtons = Button.newListBuilder()
                .addUrlButton("Open Web URL", "https://www.oculus.com/en-us/touch/").toList()
                .addPostbackButton("Call Postback", "Payload for second bubble").toList()
                .build();


        final GenericTemplate genericTemplate = GenericTemplate.newBuilder()
                .addElements()
                    .addElement("rift")
                        .subtitle("Next-generation virtual reality")
                        .itemUrl("https://www.oculus.com/en-us/rift/")
                        .imageUrl(RESOURCE_URL + "/assets/rift.png")
                        .buttons(riftButtons)
                        .toList()
                    .addElement("touch")
                        .subtitle("Your Hands, Now in VR")
                        .itemUrl("https://www.oculus.com/en-us/touch/")
                        .imageUrl(RESOURCE_URL + "/assets/touch.png")
                        .buttons(touchButtons)
                        .toList()
                    .done()
                .build();

        this.sendClient.sendTemplate(recipientId, genericTemplate);
    }

    private void sendReceiptMessage(String recipientId) throws MessengerApiException, MessengerIOException {
        final String uniqueReceiptId = "order-" + Math.floor(Math.random() * 1000);

        final ReceiptTemplate receiptTemplate = ReceiptTemplate.newBuilder("Peter Chang", uniqueReceiptId, "USD", "Visa 1234")
                .timestamp(1428444852L)
                .addElements()
                    .addElement("Oculus Rift", 599.00f)
                        .subtitle("Includes: headset, sensor, remote")
                        .quantity(1)
                        .currency("USD")
                        .imageUrl(RESOURCE_URL + "/assets/riftsq.png")
                        .toList()
                    .addElement("Samsung Gear VR", 99.99f)
                        .subtitle("Frost White")
                        .quantity(1)
                        .currency("USD")
                        .imageUrl(RESOURCE_URL + "/assets/gearvrsq.png")
                        .toList()
                    .done()
                .addAddress("1 Hacker Way", "Menlo Park", "94025", "CA", "US").done()
                .addSummary(626.66f)
                    .subtotal(698.99f)
                    .shippingCost(20.00f)
                    .totalTax(57.67f)
                    .done()
                .addAdjustments()
                    .addAdjustment().name("New Customer Discount").amount(-50f).toList()
                    .addAdjustment().name("$100 Off Coupon").amount(-100f).toList()
                    .done()
                .build();

        this.sendClient.sendTemplate(recipientId, receiptTemplate);
    }

    private void sendQuickReply(String recipientId) throws MessengerApiException, MessengerIOException {
        final List<QuickReply> quickReplies = QuickReply.newListBuilder()
                .addTextQuickReply("Action", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_ACTION").toList()
                .addTextQuickReply("Comedy", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_COMEDY").toList()
                .addTextQuickReply("Drama", "DEVELOPER_DEFINED_PAYLOAD_FOR_PICKING_DRAMA").toList()
                .addLocationQuickReply().toList()
                .build();

        this.sendClient.sendTextMessage(recipientId, "What's your favorite movie genre?", quickReplies);
    }

    private void sendReadReceipt(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.MARK_SEEN);
    }

    private void sendTypingOn(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.TYPING_ON);
    }

    private void sendTypingOff(String recipientId) throws MessengerApiException, MessengerIOException {
        this.sendClient.sendSenderAction(recipientId, SenderAction.TYPING_OFF);
    }

    private AttachmentMessageEventHandler newAttachmentMessageEventHandler() {
        return event -> {
            logger.debug("Received AttachmentMessageEvent: "+ event);

            final String messageId = event.getMid();
            final List<Attachment> attachments = event.getAttachments();
            final String senderId = event.getSender().getId();
            final Date timestamp = event.getTimestamp();

            logger.info("Received message '"+messageId+"' with attachments from user '"+senderId+"' at '"+timestamp+"'");

            attachments.forEach(attachment -> {
                final AttachmentType attachmentType = attachment.getType();
                final Payload payload = attachment.getPayload();

                String payloadAsString = null;
                if (payload.isBinaryPayload()) {
                    payloadAsString = payload.asBinaryPayload().getUrl();
                }
                if (payload.isLocationPayload()) {
                    payloadAsString = payload.asLocationPayload().getCoordinates().toString();
                }

                logger.info("Attachment of type '"+attachmentType+"' with payload '"+payloadAsString+"'");
            });

            sendTextMessage(senderId, "Message with attachment received");
        };
    }

    private QuickReplyMessageEventHandler newQuickReplyMessageEventHandler() {
        return event -> {
            logger.debug("Received QuickReplyMessageEvent: "+ event);

            final String senderId = event.getSender().getId();
            final String messageId = event.getMid();
            final String quickReplyPayload = event.getQuickReply().getPayload();

            logger.info("Received quick reply for message '"+messageId+"' with payload '"+quickReplyPayload+"'");

            sendTextMessage(senderId, "Quick reply tapped");
        };
    }

    private PostbackEventHandler newPostbackEventHandler() {
        return event -> {
            logger.debug("Received PostbackEvent: "+ event);

            final String senderId = event.getSender().getId();
            final String recipientId = event.getRecipient().getId();
            final String payload = event.getPayload();
            final Date timestamp = event.getTimestamp();

            logger.info("Received postback for user '"+senderId+"' and page '"+recipientId+"' with payload '"+payload+"' at '"+timestamp+"'");

            sendTextMessage(senderId, "Postback called");
        };
    }

    private AccountLinkingEventHandler newAccountLinkingEventHandler() {
        return event -> {
            logger.debug("Received AccountLinkingEvent: "+ event);

            final String senderId = event.getSender().getId();
            final AccountLinkingStatus accountLinkingStatus = event.getStatus();
            final String authorizationCode = event.getAuthorizationCode();

            logger.info("Received account linking event for user '"+senderId+"' with status '"+accountLinkingStatus+"' and auth code '"+authorizationCode+"'");
        };
    }

    private OptInEventHandler newOptInEventHandler() {
        return event -> {
            logger.debug("Received OptInEvent: "+ event);

            final String senderId = event.getSender().getId();
            final String recipientId = event.getRecipient().getId();
            final String passThroughParam = event.getRef();
            final Date timestamp = event.getTimestamp();

            logger.info("Received authentication for user '"+senderId+"' and page '"+recipientId+"' with pass through param '"+passThroughParam+"' at '"+timestamp+"'");

            sendTextMessage(senderId, "Authentication successful");
        };
    }

    private EchoMessageEventHandler newEchoMessageEventHandler() {
        return event -> {
            logger.debug("Received EchoMessageEvent: "+ event);

            final String messageId = event.getMid();
            final String recipientId = event.getRecipient().getId();
            final String senderId = event.getSender().getId();
            final Date timestamp = event.getTimestamp();

            logger.info("Received echo for message '"+messageId+"' that has been sent to recipient '"+recipientId+"' by sender '"+senderId+"' at '"+timestamp+"'");
        };
    }

    private MessageDeliveredEventHandler newMessageDeliveredEventHandler() {
        return event -> {
            logger.debug("Received MessageDeliveredEvent: "+ event);

            final List<String> messageIds = event.getMids();
            final Date watermark = event.getWatermark();
            final String senderId = event.getSender().getId();

            if (messageIds != null) {
                messageIds.forEach(messageId -> {
                    logger.info("Received delivery confirmation for message: "+ messageId);
                });
            }

            logger.info("All messages before '"+watermark+"' were delivered to user '"+senderId+"'");
        };
    }

    private MessageReadEventHandler newMessageReadEventHandler() {
        return event -> {
            logger.debug("Received MessageReadEvent: "+ event);

            final Date watermark = event.getWatermark();
            final String senderId = event.getSender().getId();

            logger.info("All messages before '"+watermark+"' were read by user '"+senderId+"'");
        };
    }

    /**
     * This handler is called when either the message is unsupported or when the event handler for the actual event type
     * is not registered. In this showcase all event handlers are registered. Hence only in case of an
     * unsupported message the fallback event handler is called.
     */
    private FallbackEventHandler newFallbackEventHandler() {
        return event -> {
            logger.debug("Received FallbackEvent: "+ event);

            final String senderId = event.getSender().getId();
            logger.info("Received unsupported message from user: "+ senderId);
        };
    }

    private void sendTextMessage(String recipientId, String text) {
        try {
            final Recipient recipient = Recipient.newBuilder().recipientId(recipientId).build();
            final NotificationType notificationType = NotificationType.REGULAR;
            final String metadata = "DEVELOPER_DEFINED_METADATA";
            
            this.sendClient.sendTextMessage(recipient, notificationType, text, metadata);
        } catch (MessengerApiException | MessengerIOException e) {
            handleSendException(e);
        }
    }

    private void handleSendException(Exception e) {
        logger.error("Message could not be sent. An unexpected error occurred.", e);
    }
}