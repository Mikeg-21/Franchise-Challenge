package com.franchise.challenge.infraestructure.entrypoint.router;

import com.franchise.challenge.infraestructure.entrypoint.handler.FranchiseHandler;
import com.franchise.challenge.infraestructure.entrypoint.util.ApiConstants;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FranchiseRouter {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = ApiConstants.FRANCHISES_CREATE, method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class, beanMethod = "create"
            ),
            @RouterOperation(
                    path = ApiConstants.FRANCHISE_RENAME, method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class, beanMethod = "rename"
            ),
            @RouterOperation(
                    path = ApiConstants.BRANCH_CREATE, method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class, beanMethod = "addBranch"
            ),
            @RouterOperation(
                    path = ApiConstants.BRANCH_RENAME, method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class, beanMethod = "renameBranch"
            ),
            @RouterOperation(
                    path = ApiConstants.PRODUCT_CREATE, method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class, beanMethod = "addProduct"
            ),
            @RouterOperation(
                    path = ApiConstants.PRODUCT_RENAME, method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class, beanMethod = "renameProduct"
            ),
            @RouterOperation(
                    path = ApiConstants.PRODUCT_DELETE, method = RequestMethod.DELETE,
                    beanClass = FranchiseHandler.class, beanMethod = "deleteProduct"
            ),
            @RouterOperation(
                    path = ApiConstants.PRODUCT_STOCK_UPDATE, method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class, beanMethod = "updateStock"
            ),
            @RouterOperation(
                    path = ApiConstants.MAX_STOCK_PER_BRANCH, method = RequestMethod.GET,
                    beanClass = FranchiseHandler.class, beanMethod = "maxStock"
            )
    })
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
