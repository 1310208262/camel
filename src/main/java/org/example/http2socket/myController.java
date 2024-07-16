package org.example.http2socket;


import cn.hutool.json.JSONObject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class myController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @PostMapping("/test")
    public String test(@RequestBody User user) {
        logger.info("test.....");
        final Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(user).build();
        String response = producerTemplate.send("direct:servletRoute", requestExchange)
                .getIn().getBody(String.class);
        logger.info("response = " + response);
        return "hello : " + response;
    }
}
