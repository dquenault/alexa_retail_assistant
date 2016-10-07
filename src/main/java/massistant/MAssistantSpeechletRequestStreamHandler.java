package massistant;

/**
 * Created by davidq on 07/10/2016.
 */

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;


public final class MAssistantSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.928b5847-6ea7-4e5b-af50-3305d1892d88");
    }

    public MAssistantSpeechletRequestStreamHandler() {
        super(new MAssistantSpeechlet(), supportedApplicationIds);
    }
}