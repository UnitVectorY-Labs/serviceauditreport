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

/**
 * The GitHubRepositorySummary
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@Data
@NoArgsConstructor
public class GitHubRepositorySummary {
    private int id;
    private String nodeId;
    private String name;
    private String fullName;
    private Owner owner;
    private boolean isPrivate;
    private String description;
    private boolean fork;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String mirrorUrl;
    private String svnUrl;
    private String homepage;
    private String language;
    private int forksCount;
    private int stargazersCount;
    private int watchersCount;
    private int size;
    private String defaultBranch;
    private int openIssuesCount;
    private boolean isTemplate;
    private List<String> topics;
    private boolean hasIssues;
    private boolean hasProjects;
    private boolean hasWiki;
    private boolean hasPages;
    private boolean hasDownloads;
    private boolean hasDiscussions;
    private boolean archived;
    private boolean disabled;
    private String visibility;
    private String pushedAt;
    private String createdAt;
    private String updatedAt;
    private Permissions permissions;
    private String roleName;
    private String tempCloneToken;
    private boolean deleteBranchOnMerge;
    private int subscribersCount;
    private int networkCount;
    private CodeOfConduct codeOfConduct;
    private License license;
    private boolean allowForking;
    private boolean webCommitSignoffRequired;
    private SecurityAndAnalysis securityAndAnalysis;

    @Data
    @NoArgsConstructor
    public static class Owner {
        private String name;
        private String email;
        private String login;
        private int id;
        private String nodeId;
        private String avatarUrl;
        private String gravatarId;
        private String type;
        private boolean siteAdmin;
        private String starredAt;
    }

    @Data
    @NoArgsConstructor
    public static class Permissions {
        private boolean admin;
        private boolean maintain;
        private boolean push;
        private boolean triage;
        private boolean pull;
    }

    @Data
    @NoArgsConstructor
    public static class CodeOfConduct {
        private String key;
        private String name;
        private String body;
    }

    @Data
    @NoArgsConstructor
    public static class License {
        private String key;
        private String name;
        private String spdxId;
        private String nodeId;
    }

    @Data
    @NoArgsConstructor
    public static class SecurityAndAnalysis {
        private AdvancedSecurity advancedSecurity;
        private DependabotSecurityUpdates dependabotSecurityUpdates;
        private SecretScanning secretScanning;
        private SecretScanningPushProtection secretScanningPushProtection;

        @Data
        @NoArgsConstructor
        public static class AdvancedSecurity {
            private String status;
        }

        @Data
        @NoArgsConstructor
        public static class DependabotSecurityUpdates {
            private String status;
        }

        @Data
        @NoArgsConstructor
        public static class SecretScanning {
            private String status;
        }

        @Data
        @NoArgsConstructor
        public static class SecretScanningPushProtection {
            private String status;
        }
    }
}