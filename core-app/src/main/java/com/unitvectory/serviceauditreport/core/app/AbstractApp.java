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
package com.unitvectory.serviceauditreport.core.app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.pmw.tinylog.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.unitvectory.serviceauditreport.serviceauditcore.JacksonObjectMapper;

/**
 * The Abstract App
 * 
 * @author Jared Hatfield (UnitVectorY Labs)
 */
public abstract class AbstractApp {

    /**
     * The application name
     * 
     * @return the application name
     */
    protected abstract String getAppName();

    /**
     * The service annotation class
     * 
     * @return the service annotation class
     */
    protected abstract Class<?> getServiceAnnotationClass();

    /**
     * Run the application
     * 
     * @param args the arguments
     */
    protected void run(String... args) {
        CommandLineParser parser = new DefaultParser();
        Options options = createOptions();

        try {
            CommandLine line = parser.parse(options, args);

            String configPath = line.getOptionValue("config");

            JsonNode rootNode = JacksonObjectMapper.OBJECT_MAPPER.readTree(new File(configPath));

            // TODO: Everything

        } catch (ParseException exp) {
            handleParseException(exp, options);
        } catch (IOException e) {
            Logger.error("Failed to read the configuration file: " + e.getMessage());
        }
    }

    /**
     * Create the options
     * 
     * @return the options
     */
    private Options createOptions() {
        Options options = new Options();

        Option configOption = Option.builder("c")
                .longOpt("config")
                .argName("config")
                .desc("the " + this.getAppName() + " configuration")
                .hasArg()
                .required(true)
                .build();

        options.addOption(configOption);
        return options;
    }

    /**
     * Handle the parse exception
     * 
     * @param exp     the exception
     * @param options the options
     */
    private void handleParseException(ParseException exp, Options options) {
        System.out.println("Failed to parse command line arguments: " + exp.getMessage());
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(this.getAppName() + " -c config.json", options);
    }
}
