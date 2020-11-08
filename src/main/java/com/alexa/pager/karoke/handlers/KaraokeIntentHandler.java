package com.alexa.pager.karoke.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.exception.AskSdkException;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.ExecuteCommandsDirective;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.HighlightMode;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective;
import com.amazon.ask.model.interfaces.alexa.presentation.apl.SpeakItemCommand;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.util.ResourceUtils;

import static com.amazon.ask.request.Predicates.intentName;
import static org.slf4j.LoggerFactory.getLogger;
import static com.alexa.pager.karoke.util.Util.supportsApl;

public class KaraokeIntentHandler implements RequestHandler {
	 private static Logger LOG = getLogger(KaraokeIntentHandler.class);
    @Override
    public boolean canHandle(HandlerInput input) {
    	LOG.info("In KaraokeIntentHandler");
        return input.matches(intentName("KaraokeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String noAplSpeechtext = "This is a sample for multimodal devices. Try it on an Echo Show, Echo Spot or a Fire TV device.";

        if (supportsApl(input)) {
        	LOG.info("APL Supported for karaoke Intent");
            String speechText = "This is the karaoke template!";
            try {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, Object>> documentMapType = new TypeReference<HashMap<String, Object>>() {
                };
                File file = ResourceUtils.getFile("classpath:json/karaoke.json");
                if(!file.exists()) {
                	LOG.info("karaoke.json File Not exists");
                }
                Map<String, Object> document = mapper.readValue(file, documentMapType);

                TypeReference<HashMap<String, Object>> dataSourceMapType = new TypeReference<HashMap<String, Object>>() {
                };
                File file1 = ResourceUtils.getFile("classpath:json/karaokeTemplateData.json");
                if(!file1.exists()) {
                	LOG.info("karaokeTemplateData.json File Not exists");
                }
                Map<String, Object> dataSource = mapper.readValue(file1, dataSourceMapType);

                RenderDocumentDirective documentDirective = RenderDocumentDirective.builder()
                        .withToken("karaokeToken")
                        .withDocument(document)
                        .withDatasources(dataSource)
                        .build();

                ExecuteCommandsDirective commandsDirective = ExecuteCommandsDirective.builder()
                        .withToken("karaokeToken")
                        .withCommands(Collections.singletonList(SpeakItemCommand.builder()
                                .withComponentId("karaokeSpeechText")
                                .withHighlightMode(HighlightMode.LINE)
                                .build()))
                        .build();

                return input.getResponseBuilder()
                        .withSpeech(speechText)
                        .addDirective(documentDirective)
                        .addDirective(commandsDirective)
                        .build();
            } catch (IOException e) {
                throw new AskSdkException("Unable to read or deserialize karaoke data", e);
            }
        } else {
            return input.getResponseBuilder()
                    .withSpeech(noAplSpeechtext)
                    .build();
        }
    }
}