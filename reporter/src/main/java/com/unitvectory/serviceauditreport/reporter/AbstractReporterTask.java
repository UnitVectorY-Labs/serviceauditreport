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
package com.unitvectory.serviceauditreport.reporter;

import com.unitvectory.serviceauditreport.core.AbstractTask;
import com.unitvectory.serviceauditreport.core.DataContext;
import com.unitvectory.serviceauditreport.core.DataProduct;
import com.unitvectory.serviceauditreport.core.TaskInputTypes;
import com.unitvectory.serviceauditreport.core.TaskOutputTypes;

import lombok.AllArgsConstructor;

/**
 * The abstract reporter task
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public abstract class AbstractReporterTask implements AbstractTask {

    private TaskInputTypes inputTypes;

    private TaskOutputTypes outputTypes;

    @Override
    public final DataProduct execute(DataContext dataContext) {
        // TODO: Scope the data context to the input types
        ReporterDataContext reporterDataContext = new ReporterDataContext();

        return this.executeReporter(reporterDataContext);
    }

    public abstract ReporterDataProduct executeReporter(ReporterDataContext dataContext);

    @Override
    public final TaskInputTypes getInputTypes() {
        return this.inputTypes;
    }

    @Override
    public final TaskOutputTypes getOutputTypes() {
        return this.outputTypes;
    }
}
