// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.its.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.ServerInfo;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URL;

public class JiraClient {

  private JiraRestClient restClient;

  public JiraClient(final String baseUrl, final String username, final String password) throws Exception {
    try {
      URL url =  new URL(baseUrl);
      JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
      restClient = factory.createWithBasicHttpAuthentication(url.toURI(), username, password);
    } catch (Exception e) {
      throw new Exception("Exception during JiraRestClient contruction", e);
    }
  }

  public void logout() throws IOException {
    restClient.close();
  }

  public Issue getIssue(String issueKey) {
    return restClient.getIssueClient().getIssue(issueKey).claim();
  }

  public ServerInfo getServerInfo() {
    return restClient.getMetadataClient().getServerInfo().claim();
  }

}
