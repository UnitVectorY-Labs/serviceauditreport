package com.unitvectory.serviceauditreport.service.github.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.unitvectory.serviceauditreport.service.github.collector.GitHubOrganizationCollector;
import com.unitvectory.serviceauditreport.service.github.collector.GitHubRepositorySummaryCollector;

@Configuration
public class GitHubServiceConfig {

    @Bean
    public GitHubOrganizationCollector gitHubOrganizationCollector() {
        return new GitHubOrganizationCollector();
    }

    @Bean
    public GitHubRepositorySummaryCollector gitHubrepositorySummaryCollector() {
        return new GitHubRepositorySummaryCollector();
    }
}
