package com.github.kubenext.uaa.config;

import com.github.kubenext.config.SpringProfiles;
import com.github.kubenext.properties.CommonProperties;
import io.undertow.UndertowOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

import static com.github.kubenext.config.H2ConfigurationHelper.initH2Console;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 *
 * @author shangjin.li
 * @date Created in 08:22 2019-04-06
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private static final Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    private final CommonProperties commonProperties;

    public WebConfigurer(Environment env, CommonProperties commonProperties) {
        this.env = env;
        this.commonProperties = commonProperties;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = commonProperties.getCors();
        if (configuration.getAllowedOrigins() != null && !configuration.getAllowedOrigins().isEmpty()) {
            logger.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", configuration);
            source.registerCorsConfiguration("/management/**", configuration);
            source.registerCorsConfiguration("/v2/api-docs", configuration);
        }
        return new CorsFilter(source);
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     * @param server
     */
    @Override
    public void customize(WebServerFactory server) {
        setMimeMappings(server);
        if (commonProperties.getHttp().getVersion().equals(CommonProperties.Http.Version.V_2_0)) {
            if (server instanceof UndertowServletWebServerFactory) {
                ((UndertowServletWebServerFactory) server)
                        .addBuilderCustomizers(builder ->
                                builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
            }
        }
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (!StringUtils.isEmpty(env.getActiveProfiles())) {
            logger.info("Web application configuration, using profiles: {}", env.getActiveProfiles());
        }
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        if (env.acceptsProfiles(Profiles.of(SpringProfiles.SPRING_PROFILE_DEVELOPMENT))) {
            logger.debug("Initialize H2 console");
            initH2Console(servletContext);
        }
        logger.info("Web application fully configured");
    }

    private void setMimeMappings(WebServerFactory server) {
        if (server instanceof ConfigurableServletWebServerFactory) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            // CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
            servletWebServer.setMimeMappings(mappings);
        }
    }


}
