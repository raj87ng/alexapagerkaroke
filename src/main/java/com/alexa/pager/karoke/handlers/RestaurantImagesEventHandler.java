package com.alexa.pager.karoke.handlers;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.UserEventHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.UserEvent;

public class RestaurantImagesEventHandler implements UserEventHandler{
	private static Logger LOG = getLogger(RestaurantImagesEventHandler.class);
	@Override
	public boolean canHandle(HandlerInput input, UserEvent userEvent) {
		LOG.info("RestaurantImagesEventHandler can handle");
		return userEvent.getToken().equalsIgnoreCase("restaurantToken");
		//return false;
	}

	@Override
	public Optional<Response> handle(HandlerInput input, UserEvent userEvent) {
String speechText = "Thank you for clicking the Restaurant Tell me bring it back!";
LOG.info("RestaurantImagesEventHandler handle method");
        return input.getResponseBuilder()
            .withSpeech(speechText)
            .withReprompt("Tell me to start over if you want me to bring the text back into view. Or, you can just say show me restaurant.")
            .build();
	}

}
