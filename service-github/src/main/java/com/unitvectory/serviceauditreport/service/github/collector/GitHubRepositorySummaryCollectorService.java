/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.unitvectory.serviceauditreport.service.github.collector;

import java.util.List;

import com.unitvectory.serviceauditreport.service.github.client.GitHubClient;
import com.unitvectory.serviceauditreport.service.github.model.GitHubConfig;
import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;
import com.unitvectory.serviceauditreport.service.github.model.GitHubRepositorySummary;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.Collector;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceInput;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceOutput;

/**
 * The GitHubRepositorySummaryCollectorService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Collector
@ServiceInput(GitHubOrganization.class)
public class GitHubRepositorySummaryCollectorService extends AbstractService<GitHubRepositorySummary, GitHubConfig> {

    private static final int PERPAGE = 1;

    @Override
    public ServiceOutput<GitHubRepositorySummary> execute(DataManagerRead dataManager, GitHubConfig configuration) {

        GitHubClient client = new GitHubClient(configuration.getToken());

        // Load the organizations that we are looking up for (in practice this will only
        // be 1 item)
        List<GitHubOrganization> orgs = dataManager.load(GitHubOrganization.class);

        // The output
        ServiceOutput<GitHubRepositorySummary> output = new ServiceOutput<>(GitHubRepositorySummary.class);

        // Get the organization
        for (GitHubOrganization org : orgs) {

            String organization = org.getLogin();
            int page = 1;
            int size = 1;
            while (size > 0) {
                // Read a page of repositories from the API
                List<GitHubRepositorySummary> repositories = client.getRepositories(organization, PERPAGE, page);

                // Add the repositories to the output
                for (GitHubRepositorySummary repository : repositories) {
                    output.add(repository);
                }

                // If we have more than 0 items, then we need to read the next page
                size = repositories.size();
                page++;
            }
        }

        return output;
    }

    @Override
    public Class<GitHubConfig> getConfigurationClass() {
        return GitHubConfig.class;
    }
}
