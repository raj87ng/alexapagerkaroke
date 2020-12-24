package com.alexa.pager.karoke.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.AutoPageCommand;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.ExecuteCommandsDirective;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.amazon.ask.request.RequestHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.util.ResourceUtils;

import static com.amazon.ask.request.Predicates.intentName;
import static org.slf4j.LoggerFactory.getLogger;
import static com.alexa.pager.karoke.util.Util.supportsApl;

public class StoreIntentHandler implements RequestHandler {
	private static Logger LOG = getLogger(StoreIntentHandler.class);
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("StoreIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = null ;//"This is a sample for multimodal devices. Try it on an Echo Show, Echo Spot or a Fire TV device.";
        
        	String stores = getStoreAsString();
        	speechText = String.format("Here are list items of store name %s", stores);
        
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .build();
       
    }
    
    
    public static String getStoreAsString() {
    	
    	List<String> items = new ArrayList<>();
    	items.add("rajat");
    	items.add("asim");
    	  	
    	
        int i = 0;
        StringBuilder sb = new StringBuilder();
        
        
        for (String storeItems : items) {
          if (i < 5) {
            sb.append(String.format("%s,",storeItems));
            i++;
          }
        }
        return sb.toString();
      }

    public static void main(String[] args) {
		String text = getStoreAsString();
		System.out.println("text " + text); 
	}
}