package org.example.http2socket;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
//public class servletRoute extends RouteBuilder {
//    Logger logger = LoggerFactory.getLogger(getClass());
//    @Override
//    public void configure() {
//        //from方法为创建一个从指定URI开始的route，相当于在此处声明了自定义的URI
//        from("direct:servletRoute")
//                .log("get request...")
//                .setHeader(Exchange.HTTP_METHOD, simple("POST"))
//                .setHeader("Content-Type", constant("application/json"))
//                .setHeader("Accept", constant("application/json"))
//                .convertBodyTo(String.class)
//                .to("netty:tcp://localhost:8090?sync=true&producerPoolEnabled=false")
//                .log("Response: ${body}")
//                .process(exchange -> {
//                    Message message = exchange.getMessage();
//                    logger.info("message = " + message.getBody(String.class));
//                })
//                .process();
//    }
//}
