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
import com.unitvectory.serviceauditreport.service.github.model.GitHubPullRequest;
import com.unitvectory.serviceauditreport.service.github.model.GitHubRepositorySummary;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.Collector;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceInput;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceOutput;

/**
 * The GitHubPullCollectorService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Collector
@ServiceInput(parent = GitHubRepositorySummary.class)
public class GitHubPullCollectorService extends AbstractService<GitHubPullRequest, GitHubConfig> {

    private static final int PERPAGE = 100;

    @Override
    public ServiceOutput<GitHubPullRequest> execute(DataManagerRead dataManager, GitHubConfig configuration) {
        GitHubClient client = new GitHubClient(configuration.getToken());

        // Load the repositories that need to be looked up
        List<GitHubRepositorySummary> repos = dataManager.load(GitHubRepositorySummary.class);

        // The output
        ServiceOutput<GitHubPullRequest> output = new ServiceOutput<>(GitHubPullRequest.class);

        // Get the organization
        for (GitHubRepositorySummary repo : repos) {

            String organization = repo.getOwner().getLogin();
            String repository = repo.getName();

            int page = 1;
            int size = 1; // Start out assming we got a page of data
            while (size > 0) {
                // Read a page of repositories from the API
                List<GitHubPullRequest> pulls = client.getPullRequests(organization, repository, PERPAGE, page);

                // Add the repositories to the output
                for (GitHubPullRequest pull : pulls) {
                    output.add(pull);
                }

                // If we have more than 0 items, then we need to read the next page
                size = pulls.size();
                page++;
            }
        }

        return output;
    }

    @Override
    public Class<GitHubPullRequest> getOutputClass() {
        return GitHubPullRequest.class;
    }

    @Override
    public Class<GitHubConfig> getConfigurationClass() {
        return GitHubConfig.class;
    }

}
