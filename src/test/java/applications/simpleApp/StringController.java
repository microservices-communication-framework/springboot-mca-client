package applications.simpleApp;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/string")
public class StringController {


    @GetMapping("/lowercase/{string}")
    public String lowercasePathVariable(@PathVariable String string) {
        return string.toLowerCase();
    }

    @GetMapping("/lowercase")
    public String lowercaseRequestParam(@RequestParam("string") String string) {
        return string.toLowerCase();
    }

    @GetMapping("/lowercaseResultPayload")
    public ResultPayload lowercaseRequestParamResultPayload(@RequestParam("string") String string) {
        ResultPayload resultPayload = new ResultPayload();
        resultPayload.setResult(string.toLowerCase());
        return resultPayload;

    }
}
