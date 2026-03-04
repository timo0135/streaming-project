package api.payment.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

/**
 * Configuration des routes du Gateway.
 *
 * Les requêtes entrantes sont routées vers les microservices selon le préfixe de chemin :
 *   /users/**        → service Utilisateurs  (port 8081)
 *   /films/**        → service Films         (port 8082)
 *   /reservations/** → service Réservations  (port 8083)
 */
@Configuration
public class GatewayConfig {

    private static final String USER_SERVICE_URL        = "localhost:8081";
    private static final String FILM_SERVICE_URL        = "localhost:8082";
    private static final String RESERVATION_SERVICE_URL = "localhost:8083";

    /**
     * Route vers le service Utilisateurs.
     * GET/POST/PUT/DELETE /users/** → http://localhost:8081/users/**
     */
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user-service")
                .route(req -> req.path().startsWith("/users"),
                        HandlerFunctions.http())
                .filter(FilterFunctions.setRequestHostHeader(USER_SERVICE_URL))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    /**
     * Route vers le service Films.
     * GET/POST/PUT/DELETE /films/** → http://localhost:8082/films/**
     */
    @Bean
    public RouterFunction<ServerResponse> filmServiceRoute() {
        return GatewayRouterFunctions.route("film-service")
                .route(req -> req.path().startsWith("/films"),
                        HandlerFunctions.http())
                .filter(FilterFunctions.setRequestHostHeader(FILM_SERVICE_URL))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }

    /**
     * Route vers le service Réservations.
     * GET/POST/PUT/DELETE /reservations/** → http://localhost:8083/reservations/**
     */
    @Bean
    public RouterFunction<ServerResponse> reservationServiceRoute() {
        return GatewayRouterFunctions.route("reservation-service")
                .route(req -> req.path().startsWith("/reservations"),
                        HandlerFunctions.http())
                .filter(FilterFunctions.setRequestHostHeader(RESERVATION_SERVICE_URL))
                .filter(FilterFunctions.addRequestHeader("X-Gateway-Source", "api-gateway"))
                .build();
    }
}
