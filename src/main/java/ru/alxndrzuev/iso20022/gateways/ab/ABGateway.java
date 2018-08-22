package ru.alxndrzuev.iso20022.gateways.ab;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "ab", url = "https://grampus-int.alfabank.ru", configuration = FeignClientConfiguration.class)
@RequestMapping(value = "/API/v1/ISO20022")
public interface ABGateway {

    @RequestMapping(value = "/Statements", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    ResponseEntity<String> getStatement(@RequestBody String body);

    @RequestMapping(value = "/Statements/{messageId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    ResponseEntity<String> getStatementResult(@PathVariable(name = "messageId") String messageId);
}
