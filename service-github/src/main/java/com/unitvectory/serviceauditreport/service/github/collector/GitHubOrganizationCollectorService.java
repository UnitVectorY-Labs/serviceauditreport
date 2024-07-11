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

import com.unitvectory.serviceauditreport.service.github.client.GitHubClient;
import com.unitvectory.serviceauditreport.service.github.model.GitHubConfig;
import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;

/**
 * The GitHubOrganizationCollectorService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class GitHubOrganizationCollectorService extends AbstractService<GitHubOrganization, GitHubConfig> {

    @Override
    public GitHubOrganization execute(DataManagerRead dataManager, GitHubConfig configuration) {

        GitHubClient client = new GitHubClient(configuration.getToken());

        // TODO: The AbstractService interface needs to be refactored to allow for 0, 1,
        // or n instances of a class to be returned
        for (String org : configuration.getOrganization()) {
            // client.getOrganization(org);

        }

        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
