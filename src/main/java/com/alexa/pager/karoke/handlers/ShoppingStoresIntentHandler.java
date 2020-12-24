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

public class ShoppingStoresIntentHandler implements RequestHandler {
	private static Logger LOG = getLogger(ShoppingStoresIntentHandler.class);
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ShoppingStoresIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = null ;//"This is a sample for multimodal devices. Try it on an Echo Show, Echo Spot or a Fire TV device.";
        final RequestHelper requestHelper = RequestHelper.forHandlerInput(input);
        final String storeName;
        final String storeNumber;
        if(requestHelper.getSlotValue("storeName").get() != null) {
        	storeName = requestHelper.getSlotValue("storeName").get();
        	LOG.debug("Shoping stores Intent "+storeName);
        	String stores = getShopsAsString(storeName.toLowerCase());
        	speechText = String.format("Here are list items of store name %s: %s", storeName, stores);

        }else {
        	storeNumber = requestHelper.getSlotValue("storeNumber").get();
        }
        
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .build();
       
    }
    
    
    public static String getShopsAsString(String storeName) {
    	Map<String,List<String>> menues = new HashMap<>();
    	List<String> item = new ArrayList<>();
    	item.add("Cholle bahutre");
    	item.add("Gulab Jamun");
    	item.add("Pizza");
    	
    	List<String> item1 = new ArrayList<>();
    	item1.add("Rasogulla");
    	item1.add("Pani Puri");
    	item1.add("Pizza");
    	
    	menues.put("rajat", item);
    	menues.put("asim", item1);
    	
    	
        int i = 0;
        StringBuilder sb = new StringBuilder();
        List<String> items = menues.get(storeName);
        
        for (String storeItems : items) {
          if (i < 5) {
            sb.append(String.format("%s,",storeItems));
            i++;
          }
        }
        return sb.toString();
      }

    public static void main(String[] args) {
		String text = getShopsAsString("rajat");
		System.out.println("text " + text); 
	}
}