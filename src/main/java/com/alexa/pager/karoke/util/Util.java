package com.alexa.pager.karoke.util;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;

import org.slf4j.Logger;

import com.alexa.pager.karoke.handlers.DeviceIntentHandler;
import com.amazon.ask.AlexaSkill;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.SupportedInterfaces;

public class Util {
	private static Logger LOG = getLogger(DeviceIntentHandler.class);
    public static boolean supportsApl(HandlerInput input) {
    	LOG.info("Request type in supportsApl" + input.getRequest().getType());
        SupportedInterfaces supportedInterfaces = input.getRequestEnvelope().getContext().getSystem().getDevice().getSupportedInterfaces();
        return supportedInterfaces.getAlexaPresentationAPL() != null;
    }
    
    public static boolean getSlotValue(HandlerInput input) {
    	LOG.info("Request type in supportsApl" + input.getRequest().getType());
        Map<String,Object> attr = input.getAttributesManager().getSessionAttributes();
        String day = (String) attr.get("day");
        return true;
    }

}
