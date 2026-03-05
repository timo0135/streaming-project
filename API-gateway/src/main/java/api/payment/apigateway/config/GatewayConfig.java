package api.payment.apigateway.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

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

    @Bean
    public RestTemplate gatewayRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
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
    public RouterFunction<ServerResponse> reservationServiceRoute() {
        return GatewayRouterFunctions.route("reservation-service")
                .route(req -> req.path().startsWith("/reservations"),
                        HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(URI.create(RESERVATION_SERVICE_URL)))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }
}
