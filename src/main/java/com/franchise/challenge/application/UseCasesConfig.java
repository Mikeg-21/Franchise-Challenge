package com.franchise.challenge.application;

import com.franchise.challenge.domain.api.FranchiseServicePort;
import com.franchise.challenge.domain.spi.FranchisePersistencePort;
import com.franchise.challenge.domain.usecase.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    @Bean
    public FranchiseServicePort franchiseServicePort(FranchisePersistencePort franchisepersistencePort) {
        return new FranchiseUseCase(franchisepersistencePort);
    }

}