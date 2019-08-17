package org.superbiz.struts;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    UserRepository repository;
    UserController(UserRepository repository){
        this.repository=repository;
    }
    @GetMapping("/addUser")
    public String addUserForm(){
        return "addUserForm";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,Model model){

        System.out.println("User          "+user);
        try{
            repository.save(user);
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "addUserForm";
        }
        return "addedUser";
    }

    @GetMapping("/findUser")
    public String findUserForm() {
        return "findUserForm";
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model){
        Optional<User> user = repository.findById(id);
        if(user.isPresent()){
             model.addAttribute("user",user.get());
        }else{
            model.addAttribute("errorMessage","User Not Found");
            return "/findUserForm";
        }
        return "/displayUser";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("users",repository.findAll());
        return "/displayUsers";

    }
}
