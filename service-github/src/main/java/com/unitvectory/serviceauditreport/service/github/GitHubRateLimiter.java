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
package com.unitvectory.serviceauditreport.service.github;

import java.time.Duration;

import io.github.bucket4j.Bucket;
import lombok.experimental.UtilityClass;

/**
 * The GitHubRateLimiter
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
@UtilityClass
public class GitHubRateLimiter {

   /**
    * Rate limit the GitHub API to average out to 1 requests per second which is
    * below the limit for GitHub authenticated requests.
    */
   private static Bucket bucket = Bucket.builder()
         .addLimit(limit -> limit.capacity(2).refillGreedy(1, Duration.ofSeconds(1)))
         .build();

   /**
    * Consume one request from the rate limiter.
    */
   public static void consumeOne() {
      try {
         bucket.asBlocking().consume(1);
      } catch (InterruptedException e) {
         throw new RuntimeException("Ratelimit exception", e);
      }
   }
}
