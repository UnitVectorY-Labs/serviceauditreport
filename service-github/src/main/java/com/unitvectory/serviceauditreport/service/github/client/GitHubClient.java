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
package com.unitvectory.serviceauditreport.service.github.client;

import java.io.IOException;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.unitvectory.serviceauditreport.service.github.model.GitHubOrganization;
import com.unitvectory.serviceauditreport.service.github.model.GitHubRepositorySummary;
import com.unitvectory.serviceauditreport.serviceauditcore.JacksonObjectMapper;

import lombok.NonNull;

/**
 * The GitHubClient class.
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class GitHubClient {

    private final String token;

    public GitHubClient(@NonNull String token) {
        this.token = token;
    }

    private static final String ORGANIZATIONURL = "https://api.github.com/orgs/%s";

    public GitHubOrganization getOrganization(String organization) throws IOException, ParseException {
        String url = String.format(ORGANIZATIONURL, organization);
        return get(GitHubOrganization.class, url);
    }

    private static final String REPOURL = "https://api.github.com/orgs/%s/repos?per_page=%d&page=%d";
    
    public List<GitHubRepositorySummary> getRepositories(String organization, int perPage, int page) throws IOException, ParseException {
        String url = String.format(REPOURL, organization, perPage, page);
        return getList(GitHubRepositorySummary.class, url);
    }

    private <T> T get(Class<T> responseType, String url) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + this.token);

            HttpClientResponseHandler<T> responseHandler = response -> {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + statusCode);
                }
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return JacksonObjectMapper.OBJECT_MAPPER.readValue(jsonResponse, responseType);
            };

            return httpClient.execute(request, responseHandler);
        }
    }

    private <T> List<T> getList(Class<T> responseType, String url) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + this.token);

            HttpClientResponseHandler<List<T>> responseHandler = response -> {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + statusCode);
                }
                String jsonResponse = EntityUtils.toString(response.getEntity());
                return JacksonObjectMapper.OBJECT_MAPPER.readValue(jsonResponse, 
                    JacksonObjectMapper.OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, responseType));
            };

            return httpClient.execute(request, responseHandler);
        }
    }
}
