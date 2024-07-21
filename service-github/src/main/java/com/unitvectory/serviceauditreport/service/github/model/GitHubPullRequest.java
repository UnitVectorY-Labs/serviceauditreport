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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitvectory.serviceauditreport.serviceauditcore.AccessType;
import com.unitvectory.serviceauditreport.serviceauditcore.DataEntity;
import com.unitvectory.serviceauditreport.serviceauditcore.SetIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The GitHubPullRequest
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@DataEntity(accessType = AccessType.SET, parent = GitHubRepositorySummary.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubPullRequest {

    @SetIdentifier
    private String id; // This is actually an integer in the GitHub API

    private String node_id;
    private Integer number;
    private String state;
    private Boolean locked;
    private String title;
    private User user;
    private String body;
    private List<Label> labels;
    private Milestone milestone;
    private String active_lock_reason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private String created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private String updated_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private String closed_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private String merged_at;
    private String merge_commit_sha;
    private User assignee;
    private List<User> assignees;
    private List<User> requested_reviewers;
    private List<Team> requested_teams;
    private Head head;
    private Base base;
    private Links _links;
    private String author_association;
    private AutoMerge auto_merge;
    private Boolean draft;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private String name;
        private String email;
        private String login;
        private Long id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String type;
        private Boolean site_admin;
        private String starred_at;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Label {
        private Long id;
        private String node_id;
        private String name;
        private String description;
        private String color;
        private Boolean default_label;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Milestone {
        private Integer id;
        private String node_id;
        private Integer number;
        private String state;
        private String title;
        private String description;
        private User creator;
        private Integer open_issues;
        private Integer closed_issues;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private String created_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private String updated_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private String closed_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private String due_on;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Team {
        private Integer id;
        private String node_id;
        private String name;
        private String slug;
        private String description;
        private String privacy;
        private String notification_setting;
        private String permission;
        private Permissions permissions;
        private Team parent;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Permissions {
        private Boolean pull;
        private Boolean triage;
        private Boolean push;
        private Boolean maintain;
        private Boolean admin;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Head {
        private String label;
        private String ref;
        private Repository repo;
        private String sha;
        private User user;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Base {
        private String label;
        private String ref;
        private Repository repo;
        private String sha;
        private User user;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Repository {
        private Long id;
        private String node_id;
        private String name;
        private String full_name;
        private License license;
        private Integer forks;
        private Permissions permissions;
        private User owner;
        private Boolean is_private;
        private Boolean fork;
        private Boolean has_downloads;
        private Boolean has_issues;
        private Boolean has_projects;
        private Boolean has_wiki;
        private Boolean has_pages;
        private String homepage;
        private String language;
        private Boolean archived;
        private Boolean disabled;
        private Boolean allow_rebase_merge;
        private String temp_clone_token;
        private Boolean allow_squash_merge;
        private Boolean allow_auto_merge;
        private Boolean delete_branch_on_merge;
        private Boolean allow_update_branch;
        private Boolean use_squash_pr_title_as_default;
        private String squash_merge_commit_title;
        private String squash_merge_commit_message;
        private String merge_commit_title;
        private String merge_commit_message;
        private Boolean allow_merge_commit;
        private Boolean allow_forking;
        private Boolean web_commit_signoff_required;
        private Integer open_issues;
        private Integer watchers;
        private String master_branch;
        private String starred_at;
        private Boolean anonymous_access_enabled;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class License {
            private String key;
            private String name;
            private String spdx_id;
            private String node_id;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private Link comments;
        private Link commits;
        private Link statuses;
        private Link issue;
        private Link review_comments;
        private Link review_comment;
        private Link self;

        @Data
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Link {
            private String href;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AutoMerge {
        private User enabled_by;
        private String merge_method;
        private String commit_title;
        private String commit_message;
    }
}