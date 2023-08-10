package com.databasemanager.DatabaseManager.api;

import com.databasemanager.DatabaseManager.model.User;
import com.databasemanager.DatabaseManager.services.UserNotFoundException;
import com.databasemanager.DatabaseManager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public String listAllUsers(Model model){
       List<User> listUsers = userService.listAllUser();
       model.addAttribute("listUsers",listUsers);
       return "users";
    }

    @GetMapping(path ="/users/new")
    public String addNewUser(Model model){

        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Add new user");

        return "user_form";
    }

    @PostMapping(path = "/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){

        userService.save(user);
        redirectAttributes.addFlashAttribute("message","User " + user.getFirstName()+" has been added successfully!");
        return "redirect:/users";

    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){

        try {
            User user = userService.editUser(id);
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Editing user details ");
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/users";
        }

    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message","User has been deleted successfully!");


        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());

        }
        return "redirect:/users";
    }




}
