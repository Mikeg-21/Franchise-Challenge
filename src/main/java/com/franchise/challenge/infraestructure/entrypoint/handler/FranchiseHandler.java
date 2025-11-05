package com.franchise.challenge.infraestructure.entrypoint.handler;

import com.franchise.challenge.domain.api.FranchiseServicePort;
import com.franchise.challenge.infraestructure.entrypoint.dto.*;
import com.franchise.challenge.infraestructure.entrypoint.mapper.FranchiseDtoMapper;
import com.franchise.challenge.infraestructure.entrypoint.util.ApiConstants;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final FranchiseServicePort service;
    private final FranchiseDtoMapper mapper;
    private final Validator validator;

    private <T> Mono<T> validate(T body) {
        var violations = validator.validate(body);
        return violations.isEmpty()
                ? Mono.just(body)
                : Mono.error(new IllegalArgumentException(violations.iterator().next().getMessage()));
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(FranchiseRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.create(b.name()))
                .flatMap(fr -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> rename(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        return req.bodyToMono(RenameRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.rename(id, b.newName()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> addBranch(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        return req.bodyToMono(BranchRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.addBranch(id, b.name()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> renameBranch(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        String oldName = req.pathVariable(ApiConstants.PATH_VAR_BRANCH);
        return req.bodyToMono(RenameRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.renameBranch(id, oldName, b.newName()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> addProduct(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        String branch = req.pathVariable(ApiConstants.PATH_VAR_BRANCH);
        return req.bodyToMono(ProductRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.addProduct(id, branch, b.name(), b.stock()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> renameProduct(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        String branch = req.pathVariable(ApiConstants.PATH_VAR_BRANCH);
        String product = req.pathVariable(ApiConstants.PATH_VAR_PRODUCT);
        return req.bodyToMono(RenameRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.renameProduct(id, branch, product, b.newName()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        String branch = req.pathVariable(ApiConstants.PATH_VAR_BRANCH);
        String product = req.pathVariable(ApiConstants.PATH_VAR_PRODUCT);
        return service.deleteProduct(id, branch, product)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        String branch = req.pathVariable(ApiConstants.PATH_VAR_BRANCH);
        String product = req.pathVariable(ApiConstants.PATH_VAR_PRODUCT);
        return req.bodyToMono(StockUpdateRequest.class)
                .flatMap(this::validate)
                .flatMap(b -> service.updateStock(id, branch, product, b.stock()))
                .flatMap(fr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(fr));
    }

    public Mono<ServerResponse> maxStock(ServerRequest req) {
        String id = req.pathVariable(ApiConstants.PATH_VAR_ID);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getMaxStockPerBranch(id), String.class);
    }
}