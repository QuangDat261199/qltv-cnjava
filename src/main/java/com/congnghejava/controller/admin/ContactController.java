package com.congnghejava.controller.admin;

import com.congnghejava.model.Contact;
import com.congnghejava.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/admin/contact")
    public String index(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        return "admin/contact/index";
    }

	@GetMapping("/admin/contact/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
	    contactService.delete(contactService.findById(id).get());
        redirect.addFlashAttribute("success", "Deleted contact successfully!");

        return "redirect:/admin/contact";
    }

}
