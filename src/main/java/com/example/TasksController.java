package com.example;

import com.example.model.Task;
import com.example.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("/tasks")
    public String index(Model model) {
        model.addAttribute("tasks", getTasks());
        return "tasks";
    }

    @SuppressWarnings("unchecked")
    private List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ModelAndView handleAllExceptions(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("error", ex.getMessage());
        view.setViewName("error");
        return view;
    }
}
