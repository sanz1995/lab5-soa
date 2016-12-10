package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        String[] part=q.split("max:");
        Map<String, Object> headers = new HashMap<String, Object>();
        if(part.length>1) {
            headers.put("CamelTwitterCount", Integer.parseInt(part[1]));
        }
        headers.put("CamelTwitterKeywords", part[0]);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);

    }
}