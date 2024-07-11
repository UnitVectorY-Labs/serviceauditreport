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

import com.unitvectory.serviceauditreport.service.github.model.GitHubConfig;
import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;
import com.unitvectory.serviceauditreport.service.github.model.GitHubRepositorySummary;
import com.unitvectory.serviceauditreport.serviceauditcore.AbstractService;
import com.unitvectory.serviceauditreport.serviceauditcore.DataManagerRead;
import com.unitvectory.serviceauditreport.serviceauditcore.ServiceInput;

/**
 * The GitHubRepositorySummaryCollectorService
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@ServiceInput(GitHubOrganization.class)
public class GitHubRepositorySummaryCollectorService extends AbstractService<GitHubRepositorySummary, GitHubConfig> {

    @Override
    public GitHubRepositorySummary execute(DataManagerRead dataManager, GitHubConfig configuration) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
