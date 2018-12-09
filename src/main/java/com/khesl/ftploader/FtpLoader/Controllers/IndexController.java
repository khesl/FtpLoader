package com.khesl.ftploader.FtpLoader.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController /*implements ErrorController*/ {

    /**private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }*/

    /*final VisitsRepository visitsRepository;

    public IndexController(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }*/

    /**@GetMapping("/")
    public ModelAndView index() {
        Map<String, String> model = new HashMap<>();
        model.put("name", "Andrey");

        Visit visit = new Visit();
        visit.description = String.format("Visited at %s", LocalDateTime.now());
        visitsRepository.save(visit);

        System.out.println("set model");
        ModelAndView mav = new ModelAndView("F:\\files\\java\\GitHub\\FtpLoader\\src\\main\\resources\\templates\\index", "name", "Andrey");
        System.out.println(mav.getStatus().toString());//.getContentType());
        System.out.println(new File("src\\main\\resources\\templates\\index.html").canRead());
        return new ModelAndView("index", "name", "Andrey"); // resources/templates/index.html
    }*/

    @GetMapping("/hello")
    public String handle(Model model) {
        model.addAttribute("name", "Andrey!");
        return "index";
    }

    @RequestMapping("/testpage1")
    public String testHello2(){
        System.out.println("page1.html");
        return "page1";
    }

    @RequestMapping("/testrest/id={id}")
    @ResponseBody
    public String testHello2(@PathVariable String id){
        System.out.println("testHello2");
        return "[id:'" + id + "']";
    }

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Hello World";
    //spring.mvc.view.prefix: /WEB-INF/jsp/
    //spring.mvc.view.suffix: .jsp

    @GetMapping("/test2")
    public ModelAndView test2() {
        Map<String, String> model = new HashMap<>();
        model.put("name", "Andrey");

        System.out.println("set model");
        return new ModelAndView("index", model); // resources/templates/index.html
    }
}