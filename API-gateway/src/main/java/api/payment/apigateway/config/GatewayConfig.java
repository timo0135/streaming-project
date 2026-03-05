package api.payment.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.List;

/**
 * Configuration des routes du Gateway.
 *
 * Les requêtes entrantes sont routées vers les microservices selon le préfixe de chemin :
 *   /users/**        → service Utilisateurs
 *   /films/**        → service Films
 *   /reservations/** → service Réservations
 */
@Configuration
public class GatewayConfig {

    private static final String USER_SERVICE_URL        = "http://info-tpsi.univ-brest.fr:10045";
    private static final String FILM_SERVICE_URL        = "http://info-tpsi.univ-brest.fr:10091";
    private static final String RESERVATION_SERVICE_URL = "http://info-tpsi.univ-brest.fr:10056";
    private static final String AUTH_SERVICE_URL        = "http://info-tpsi.univ-brest.fr:10090";

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user-service")
                .route(req -> req.path().startsWith("/users"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(URI.create(USER_SERVICE_URL)))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> filmServiceRoute() {
        return GatewayRouterFunctions.route("film-service")
                .route(req -> req.path().startsWith("/films"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(URI.create(FILM_SERVICE_URL)))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return GatewayRouterFunctions.route("auth-service")
                .route(req -> req.path().startsWith("/auth"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(URI.create(AUTH_SERVICE_URL)))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> reservationServiceRoute() {
        return GatewayRouterFunctions.route("reservation-service")
                .route(req -> req.path().startsWith("/reservations"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(URI.create(RESERVATION_SERVICE_URL)))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }
}
