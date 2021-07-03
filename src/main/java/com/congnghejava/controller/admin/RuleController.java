package com.congnghejava.controller.admin;

import com.congnghejava.model.Rule;
import com.congnghejava.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class RuleController {

    @Autowired RuleService ruleService;

    @GetMapping("/admin/rule")
    public String index(Model model) {
        model.addAttribute("rules", ruleService.findAll());
        return "admin/rule/index";
    }

    @GetMapping("/admin/rule/create")
    public String create(Model model) {
        model.addAttribute("rule", new Rule());
        return "admin/rule/create";
    }

    @PostMapping("/admin/rule/store")
    public String store(@Validated Rule rule, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/rule/create";
        }

        ruleService.save(rule);
        redirect.addFlashAttribute("success", "Created rule successfully!");

        return "redirect:/admin/rule";
    }

    @GetMapping("/admin/rule/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("rule", ruleService.findById(id));
        return "admin/rule/edit";
    }

    @PostMapping("/admin/rule/update/{id}")
    public String update(@PathVariable("id") Long id, Rule rule, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/rule/edit/" + id.toString();
        }

        ruleService.update(id, rule);
        redirect.addFlashAttribute("success", "Updated rule successfully!");

        return "redirect:/admin/rule/edit/" + id.toString();
    }

	@GetMapping("/admin/rule/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
	    ruleService.delete(ruleService.findById(id).get());
        redirect.addFlashAttribute("success", "Deleted rule successfully!");

        return "redirect:/admin/rule";
    }

}
