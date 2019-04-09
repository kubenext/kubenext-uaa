package com.github.kubenext.uaa.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.boolex.OnMarkerEvaluator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.filter.EvaluatorFilter;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.FilterReply;
import com.github.kubenext.properties.CommonProperties;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @author shangjin.li
 */
@Configuration
public class LoggingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LoggingConfiguration.class);

    private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";
    private static final String ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH";

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final String appName;
    private final String serverPort;
    private final CommonProperties commonProperties;

    public LoggingConfiguration(@Value("${spring.application.name}") String appName, @Value("${server.port}") String serverPort, CommonProperties commonProperties) {
        this.appName = appName;
        this.serverPort = serverPort;
        this.commonProperties = commonProperties;
        if (this.commonProperties.getLogging().getLogstash().isEnabled()) {
            addLogstashAppender(context);
            addContextListener(context);
        }
        if (this.commonProperties.getMetrics().getLogs().isEnabled()) {
            setMetricsMarkerLogbackFilter(context);
        }

    }

    private void addContextListener(LoggerContext context) {
        LogbackLoggerContextListener loggerContextListener = new LogbackLoggerContextListener();
        loggerContextListener.setContext(context);
        context.addListener(loggerContextListener);
    }

    private void addLogstashAppender(LoggerContext context) {
        logger.info("Initializing Logstash logging");
        LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
        logstashAppender.setName(LOGSTASH_APPENDER_NAME);
        logstashAppender.setContext(context);
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\"}";

        LogstashEncoder logstashEncoder = new LogstashEncoder();
        logstashAppender.addDestinations(new InetSocketAddress(commonProperties.getLogging().getLogstash().getHost(),commonProperties.getLogging().getLogstash().getPort()));

        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        logstashEncoder.setCustomFields(customFields);

        logstashAppender.setEncoder(logstashEncoder);
        logstashAppender.start();

        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(context);
        asyncAppender.setName(ASYNC_LOGSTASH_APPENDER_NAME);
        asyncAppender.setQueueSize(commonProperties.getLogging().getLogstash().getQueueSize());
        asyncAppender.addAppender(logstashAppender);
        asyncAppender.start();
        context.getLogger("ROOT").addAppender(asyncAppender);
    }

    private void setMetricsMarkerLogbackFilter(LoggerContext context) {
        logger.info("Filtering metrics logs from all appenders except the {} appender", LOGSTASH_APPENDER_NAME);

        OnMarkerEvaluator onMarkerEvaluator = new OnMarkerEvaluator();
        onMarkerEvaluator.setContext(context);
        onMarkerEvaluator.addMarker("metrics");
        onMarkerEvaluator.start();
        EvaluatorFilter<ILoggingEvent> eventEvaluatorFilter = new EvaluatorFilter<>();
        eventEvaluatorFilter.setContext(context);
        eventEvaluatorFilter.setEvaluator(onMarkerEvaluator);
        eventEvaluatorFilter.setOnMatch(FilterReply.DENY);
        eventEvaluatorFilter.start();

        context.getLoggerList().forEach(item -> item.iteratorForAppenders().forEachRemaining(appender -> {
            if (!appender.getName().equals(ASYNC_LOGSTASH_APPENDER_NAME)) {
                logger.debug("Filter metrics logs from the {} appender", appender.getName());
                appender.setContext(context);
                appender.addFilter(eventEvaluatorFilter);
                appender.start();
            }
        }));
    }

    class LogbackLoggerContextListener extends ContextAwareBase implements LoggerContextListener {

        @Override
        public boolean isResetResistant() {
            return true;
        }

        @Override
        public void onStart(LoggerContext context) {
            addLogstashAppender(context);
        }

        @Override
        public void onReset(LoggerContext context) {
            addLogstashAppender(context);
        }

        @Override
        public void onStop(LoggerContext context) {

        }

        @Override
        public void onLevelChange(ch.qos.logback.classic.Logger logger, Level level) {

        }
    }



}
