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

import com.unitvectory.serviceauditreport.collector.AbstractCollectorTask;
import com.unitvectory.serviceauditreport.collector.CollectorDataContext;
import com.unitvectory.serviceauditreport.collector.CollectorDataProduct;
import com.unitvectory.serviceauditreport.core.TaskInputTypes;
import com.unitvectory.serviceauditreport.core.TaskOutputTypes;
import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;
import com.unitvectory.serviceauditreport.service.github.model.GitHubRepositorySummary;

/**
 * The GitHubRepositorySummary collector
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class GitHubRepositorySummaryCollector extends AbstractCollectorTask {

    public GitHubRepositorySummaryCollector() {
        super(TaskInputTypes.builder().type(GitHubOrganization.class).build(), TaskOutputTypes.builder().type(GitHubRepositorySummary.class).build());
    }

    @Override
    public CollectorDataProduct executeCollector(CollectorDataContext dataContext) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeCollector'");
    }

}
