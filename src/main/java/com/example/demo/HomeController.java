package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newMessage(Model model){
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processMessage(@Valid Message message,
                               BindingResult result,
                                 @RequestParam("posteddate") String date){
        Date date1 = new Date();
        try{
            date1 = new SimpleDateFormat("yyyy-MM-d").parse(date);
            message.setPosteddate(date1);
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/messageform";
        }
        if (result.hasErrors()){
            return "messageform";
        }
        messageRepository.save(message);
        return "redirect:/";
    }

}
