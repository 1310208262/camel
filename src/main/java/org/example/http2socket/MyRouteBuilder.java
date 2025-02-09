/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.http2socket;

import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Netty client which calls the server every half-second with a random word.
 */
@Component
public class MyRouteBuilder extends RouteBuilder {

    private String[] words = new String[]{"foo", "bar", "baz", "beer", "wine", "cheese"};
    private int counter;

    public int increment() {
        return ++counter;
    }

    public String word() {
        int ran = new Random().nextInt(words.length);
        return words[ran];
    }

    @Override
    public void configure() throws Exception {
        // lets build a special custom error message for timeout
        onException(ExchangeTimedOutException.class)
                // here we tell Camel to continue routing
                .continued(true)
                // after it has built this special timeout error message body
                .setBody(simple("#${header.corId}:${header.word}-Time out error!!!"));

        //from("timer:trigger")
         //from("direct:servletRoute")
         from("jetty:http://0.0.0.0:9095/doHelloWorld")
                .log("get request...")
                // set correlation id as unique incrementing number
                .setHeader("corId", method(this, "increment"))
                // set random word to use in request
                .setHeader("word", method(this, "word"))
                // build request message as a string body
                .setBody(simple("#${header.corId}:${header.word}"))
                // log request before
                .log("Request:  ${id}:${body}")
                // call netty server using a single shared connection and using custom correlation manager
                // to ensure we can correltly map the request and response pairs
                .to("netty:tcp://1.14.120.102:8090?sync=true")
                // log response after
                .log("Response: ${id}:${body}");
    }
}

