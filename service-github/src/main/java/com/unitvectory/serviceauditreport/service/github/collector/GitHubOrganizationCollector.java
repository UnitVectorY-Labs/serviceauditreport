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
import com.unitvectory.serviceauditreport.service.github.model.GitHubCollectorConfig;
import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;

/**
 * The GitHubOrganization collector
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class GitHubOrganizationCollector extends AbstractCollectorTask {

    public GitHubOrganizationCollector() {
        super(TaskInputTypes.builder().type(GitHubCollectorConfig.class).build(),
                TaskOutputTypes.builder().type(GitHubOrganization.class).build());
    }

    @Override
    public CollectorDataProduct executeCollector(CollectorDataContext dataContext) {

        CollectorDataProduct product = new CollectorDataProduct();

        GitHubCollectorConfig config = dataContext.getSingularData(GitHubCollectorConfig.class);
        if (config == null) {
            return product;
        }

        String organization = config.getOrganization();
        if (organization == null || organization.isEmpty()) {
            return product;
        }

        GitHubOrganization org = new GitHubOrganization(organization);
        product.setSingularData(GitHubOrganization.class, org);

        return product;
    }
}
