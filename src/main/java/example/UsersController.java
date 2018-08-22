package example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    @Value("${users.service}")
    private String usersService;

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", getUsers());
        return "users";
    }

    @SuppressWarnings("unchecked")
    private List<User> getUsers() {
        return restTemplate.getForObject(usersService, List.class);
    }

    @ExceptionHandler(Exception.class)
    public final ModelAndView handleAllExceptions(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("error", ex.getMessage());
        view.setViewName("error");
        return view;
    }
}
