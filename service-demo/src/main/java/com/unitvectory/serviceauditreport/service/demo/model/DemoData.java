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
package com.unitvectory.serviceauditreport.service.demo.model;

import java.util.List;

import com.unitvectory.serviceauditreport.core.AbstractData;

import lombok.Data;

/**
 * The DemoData
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Data
public class DemoData implements AbstractData {

    private List<DemoDataPoint> data;

    @Override
    public String getDataId() {
        // There can be only one
        return "demo";
    }
    
}