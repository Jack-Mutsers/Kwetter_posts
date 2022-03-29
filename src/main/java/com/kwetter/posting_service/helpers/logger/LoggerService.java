package com.kwetter.posting_service.helpers.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerService {
    private static Logger logger = LoggerFactory.getLogger(LoggerService.class);

    private LoggerService(){}

    public static void warn(String message){
        logger.warn(message);
    }

    public static void info (String message){
        logger.info(message);
    }

    public static void debug(String message){
        logger.debug(message);
    }
}
