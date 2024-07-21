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

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitvectory.serviceauditreport.serviceauditcore.AccessType;
import com.unitvectory.serviceauditreport.serviceauditcore.DataEntity;
import com.unitvectory.serviceauditreport.serviceauditcore.SetIdentifier;

/**
 * The GitHubRepositorySummary
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@DataEntity(accessType = AccessType.SET, parent = GitHubOrganization.class)
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositorySummary {

    @SetIdentifier
    private String id; // This is actually an integer in the GitHub API

    private String node_id;
    private String name;
    private String full_name;
    private Owner owner;
    private boolean is_private;
    private String description;
    private boolean fork;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String mirror_url;
    private String svn_url;
    private String homepage;
    private String language;
    private int forks_count;
    private int stargazers_count;
    private int watchers_count;
    private int size;
    private String default_branch;
    private int open_issues_count;
    private boolean is_template;
    private List<String> topics;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_wiki;
    private boolean has_pages;
    private boolean has_downloads;
    private boolean has_discussions;
    private boolean archived;
    private boolean disabled;
    private String visibility;
    private String pushed_at;
    private String created_at;
    private String updated_at;
    private Permissions permissions;
    private String role_name;
    private String temp_clone_token;
    private boolean delete_branch_on_merge;
    private int subscribers_count;
    private int network_count;
    private CodeOfConduct code_of_conduct;
    private License license;
    private boolean allow_forking;
    private boolean web_commit_signoff_required;
    private SecurityAndAnalysis security_and_analysis;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {
        private String name;
        private String email;
        private String login;
        private int id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String type;
        private boolean site_admin;
        private String starred_at;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Permissions {
        private boolean admin;
        private boolean maintain;
        private boolean push;
        private boolean triage;
        private boolean pull;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CodeOfConduct {
        private String key;
        private String name;
        private String body;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class License {
        private String key;
        private String name;
        private String spdx_id;
        private String node_id;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SecurityAndAnalysis {
        private AdvancedSecurity advanced_security;
        private DependabotSecurityUpdates dependabot_security_updates;
        private SecretScanning secret_scanning;
        private SecretScanningPushProtection secret_scanning_push_protection;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AdvancedSecurity {
            private String status;
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class DependabotSecurityUpdates {
            private String status;
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SecretScanning {
            private String status;
        }

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SecretScanningPushProtection {
            private String status;
        }
    }
}