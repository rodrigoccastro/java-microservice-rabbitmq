package microservice.read.client;

import microservice.read.client.queue.QueueSender;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Service {

    @Autowired
    private QueueSender queueSender;

    @RequestMapping(value = "/service1", method = RequestMethod.GET)
    public Object listDevices(@RequestParam(value="id", required=false) String id,
                              @RequestParam(value="name", required=false) String name) {

        Map mapAll = new HashMap<>();
        try {
            Map mapData = new HashMap<>();
            mapData.put("id", id);
            mapData.put("name", name);
            mapAll.put("data", mapData);

            // call endpoint
            var str = mapData.toString();
            System.out.println("send Message " + str);
            queueSender.send(str);

            mapAll.put("message", "Data sent to message queue!");
            return new ResponseEntity<>(mapAll, HttpStatus.OK);
        } catch (Exception ex) {
            mapAll.put("message", "Data not sent to message queue!");
            mapAll.put("error",  ExceptionUtils.getStackTrace(ex));
            return new ResponseEntity<>(mapAll, HttpStatus.OK);
        }
    }

}
