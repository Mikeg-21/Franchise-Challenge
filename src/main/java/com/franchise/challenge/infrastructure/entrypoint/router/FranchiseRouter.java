package com.franchise.challenge.infrastructure.entrypoint.router;

import com.franchise.challenge.infrastructure.entrypoint.handler.FranchiseHandler;
import com.franchise.challenge.infrastructure.entrypoint.util.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FranchiseRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(FranchiseHandler h) {
        return route(POST(ApiConstants.FRANCHISES_CREATE).and(accept(MediaType.APPLICATION_JSON)), h::create)
                .andRoute(PUT(ApiConstants.FRANCHISE_RENAME).and(accept(MediaType.APPLICATION_JSON)), h::rename)
                .andRoute(POST(ApiConstants.BRANCH_CREATE).and(accept(MediaType.APPLICATION_JSON)), h::addBranch)
                .andRoute(PUT(ApiConstants.BRANCH_RENAME).and(accept(MediaType.APPLICATION_JSON)), h::renameBranch)
                .andRoute(POST(ApiConstants.PRODUCT_CREATE).and(accept(MediaType.APPLICATION_JSON)), h::addProduct)
                .andRoute(PUT(ApiConstants.PRODUCT_RENAME).and(accept(MediaType.APPLICATION_JSON)), h::renameProduct)
                .andRoute(DELETE(ApiConstants.PRODUCT_DELETE), h::deleteProduct)
                .andRoute(PUT(ApiConstants.PRODUCT_STOCK_UPDATE).and(accept(MediaType.APPLICATION_JSON)), h::updateStock)
                .andRoute(GET(ApiConstants.MAX_STOCK_PER_BRANCH), h::maxStock);
    }
}