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
package com.unitvectory.serviceauditreport.collector;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * The Collector Command line implementation
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public class CollectorCommand implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(CollectorCommand.class);

    public static void main(String[] args) {
        SpringApplication.run(CollectorCommand.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        try {
            CommandLine line = parser.parse(options, args);

            String configValue = line.getOptionValue("config");

            // TODO: Everything

        } catch (ParseException exp) {
            handleParseException(exp, options);
        }
    }

    private static Options createOptions() {
        Options options = new Options();

        Option configOption = Option.builder("c")
                .longOpt("config")
                .argName("config")
                .desc("the collector configuration")
                .hasArg()
                .required(true)
                .build();

        options.addOption(configOption);
        return options;
    }

    private static void handleParseException(ParseException exp, Options options) {
        System.out.println("Failed to parse command line arguments: " + exp.getMessage());
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("collector -c config.json", options);
    }
}
