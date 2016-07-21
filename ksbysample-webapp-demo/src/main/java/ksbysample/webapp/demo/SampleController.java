package ksbysample.webapp.demo;

import ksbysample.library.simpleutils.StringListUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/sample")
public class SampleController {

    @RequestMapping
    @ResponseBody
    public String index() {
        return StringListUtils.convertStringList2LengthCsv(Arrays.asList("a", "ab", "abc"));
    }

    @RequestMapping("/sum")
    @ResponseBody
    public String sum() {
        return Integer.toString(StringListUtils.sumStringLength(Arrays.asList("a", "ab", "abc")));
    }

}
