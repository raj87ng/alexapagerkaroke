package com.alexa.pager.karoke.servlet;

import com.alexa.pager.karoke.handlers.CancelAndStopIntentHandler;
import com.alexa.pager.karoke.handlers.DeviceIntentHandler;
import com.alexa.pager.karoke.handlers.ErrorHandler;
import com.alexa.pager.karoke.handlers.FallbackIntentHandler;
import com.alexa.pager.karoke.handlers.HelpIntentHandler;
import com.alexa.pager.karoke.handlers.KaraokeIntentHandler;
import com.alexa.pager.karoke.handlers.LaunchRequestHandler;
import com.alexa.pager.karoke.handlers.MyExceptionHandler;
import com.alexa.pager.karoke.handlers.PagerIntentHandler;
import com.alexa.pager.karoke.handlers.SessionEndedRequestHandler;
import com.alexa.pager.karoke.request.LocalizationRequestInterceptor;
import com.alexa.pager.karoke.request.LogRequestInterceptor;
import com.alexa.pager.karoke.response.LogResponseInterceptor;
import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;

public class AlexaServlet extends SkillServlet {


	private static final long serialVersionUID = 4509917368812883664L;

	public AlexaServlet() {
        super(getSkill());
    }

    @SuppressWarnings("unchecked")
	private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                		new DeviceIntentHandler(),
                        new KaraokeIntentHandler(),
                        new PagerIntentHandler(),
                        new CancelAndStopIntentHandler(),
                        new LaunchRequestHandler(),
                        new HelpIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new ErrorHandler())
                .addExceptionHandler(new MyExceptionHandler())
                .addRequestInterceptors(
                        new LogRequestInterceptor(),
                        new LocalizationRequestInterceptor())
                .addResponseInterceptors(new LogResponseInterceptor())
                // Add your skill id below
                //.withSkillId("[unique-value-here]")
                .build();
    }

}