package ru.gb.provider;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Optional;

@Service
public class IssueProvider {
    private final WebClient webClient;

    public IssueProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public Optional<IssueProvider> getIssueById(Long id) {
        IssueProvider issue = webClient.get()
                .uri("http://issues-client/issue/{id}", id)
                .retrieve()
                .bodyToMono(IssueProvider.class)
                .block();

        return Optional.ofNullable(issue);
    }
}
