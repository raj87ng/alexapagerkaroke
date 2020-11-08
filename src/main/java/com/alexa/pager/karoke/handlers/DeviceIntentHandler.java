package com.alexa.pager.karoke.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.amazon.ask.request.viewport.ViewportProfile;
import com.amazon.ask.request.viewport.ViewportUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.alexa.pager.karoke.util.Util.supportsApl;
import static org.slf4j.LoggerFactory.getLogger;

public class DeviceIntentHandler implements RequestHandler {
    private static Logger LOG = getLogger(DeviceIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
    	LOG.info("In DeviceIntent");
        return input.matches(intentName("DeviceIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String noAplSpeechtext = "This is a sample for multimodal devices. Try it on an Echo Show, Echo Spot or a Fire TV device.";

        if (supportsApl(input)) {
        	LOG.info("APL Supported for Device Intent");
            ViewportProfile viewportProfile = ViewportUtils.getViewportProfile(input.getRequestEnvelope());
            String speechText = "This device is a ";
            switch (viewportProfile) {
                case HUB_LANDSCAPE_LARGE:
                    speechText += "hub landscape large";
                    break;
                case HUB_LANDSCAPE_MEDIUM:
                    speechText += "hub landscape medium";
                    break;
                case HUB_LANDSCAPE_SMALL:
                    speechText += "hub landscape small";
                    break;
                case HUB_ROUND_SMALL:
                    speechText += "hub round small";
                    break;
                case TV_LANDSCAPE_XLARGE:
                    speechText += "tv landscape extra large";
                    break;
                case MOBILE_LANDSCAPE_SMALL:
                    speechText += "mobile landscape small";
                    break;
                default:
                    speechText += "echo device!";
            }

            try {
                ObjectMapper mapper = new ObjectMapper();

                TypeReference<HashMap<String, Object>> deviceMapType = new TypeReference<HashMap<String, Object>>() {
                };
                File file = ResourceUtils.getFile("classpath:json/devices.json");
                if(!file.exists()) {
                	LOG.info("devices.json File Not exists");
                }
                
                Map<String, Object> document = mapper.readValue(file, deviceMapType);
                File file1 = ResourceUtils.getFile("classpath:json/deviceTemplateData.json");
                if(!file1.exists()) {
                	LOG.info("deviceTemplateData.json File Not exists");
                }
                JsonNode dataSources = mapper.readTree(file1);
                ObjectNode deviceTemplateProperties = (ObjectNode) dataSources.get("deviceTemplateData").get("properties");
                deviceTemplateProperties.put("deviceName", viewportProfile.toString());

                RenderDocumentDirective documentDirective = RenderDocumentDirective.builder()
                        .withDocument(document)
                        .withDatasources(mapper.convertValue(dataSources, new TypeReference<Map<String, Object>>() {
                        }))
                        .build();

                return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .addDirective(documentDirective)
                        .build();
            } catch (IOException e) {
                throw new AskSdkException("Unable to read or deserialize device data", e);
            }
        } else {
            return input.getResponseBuilder()
                    .withSpeech(noAplSpeechtext)
                    .build();
        }
    }
}