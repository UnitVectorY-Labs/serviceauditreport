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
package com.unitvectory.serviceauditreport.service.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitvectory.serviceauditreport.serviceauditcore.AccessType;
import com.unitvectory.serviceauditreport.serviceauditcore.DataEntity;
import com.unitvectory.serviceauditreport.serviceauditcore.SetIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The GitHubOrganization
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@DataEntity(accessType = AccessType.SET)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubOrganization {

    @SetIdentifier
    private long id;
    private String login;
    private String node_id;
    private String description;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String twitter_username;
    private boolean is_verified;
    private boolean has_organization_projects;
    private boolean has_repository_projects;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;
    private String archived_at;
    private String type;
}
