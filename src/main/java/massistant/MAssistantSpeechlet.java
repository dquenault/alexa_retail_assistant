package massistant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

/**
 * Created by davidq on 07/10/2016.
 */
public class MAssistantSpeechlet implements Speechlet {
    private final Logger log = LoggerFactory.getLogger(MAssistantSpeechlet.class);
    private static final String CARD_TITLE = "Morrisons Assistant";
    private static final String SLOT_LOCATION = "location";

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("BestIntent".equals(intentName)) {
            return getWhoIsTheBestResponse();
        } else if ("StoreIntent".equals(intentName)) {
            return getStoreResponse();
        } else if ("StoreIntent_ByLocation".equals(intentName)) {
            return getStoreByLocationResponse(intent);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        }else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    private SpeechletResponse getStoreByLocationResponse(final Intent intent)
            throws SpeechletException {
        String speechText = "The closest store to " + intent.getSlot(SLOT_LOCATION).getValue() + " is our London Store";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(CARD_TITLE);
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    private SpeechletResponse getStoreResponse() {
        String speechText = "Your closest store is in Thornbury which is 3 miles from your current location ";

       // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(CARD_TITLE);
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }
    // Slot categorySlot = intent.getSlot(SLOT_CATEGORY);

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to Morrisons, what would you like to know?";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(CARD_TITLE);
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWhoIsTheBestResponse() {
        String speechText = "Who is the best? Let's see... Today it is ";

        String names[] ={"Stuart","John","David","Tom"};
        Random random = new Random();

        speechText = speechText + names[random.nextInt(names.length)];
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(CARD_TITLE);
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "This skill will allow you to find out where your closest store is. Try saying find my closest store";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(CARD_TITLE);
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}


