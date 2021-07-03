package com.congnghejava.controller.admin;

import com.congnghejava.model.Slide;
import com.congnghejava.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@Controller
public class SlideController {

    @Autowired
    SlideService slideService;

    @GetMapping("/admin/slide")
    public String index(Model model) {
        model.addAttribute("slides", slideService.findAll());
        return "admin/slide/index";
    }

    @GetMapping("/admin/slide/create")
    public String create(Model model) {
        Slide slide = new Slide();
        slide.setIsActive(true);
        model.addAttribute("slide", slide);
        return "admin/slide/create";
    }

    @PostMapping("/admin/slide/store")
    public String store(@Validated Slide slide, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/slide/create";
        }

        Random rand = new Random();

        String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

        String uploadDir = "uploads/slide/";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = photo.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        slide.setImage(fileName);

        slideService.save(slide);
        redirect.addFlashAttribute("success", "Created slide successfully!");

        return "redirect:/admin/slide";
    }

    @GetMapping("/admin/slide/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("slide", slideService.findById(id));
        return "admin/slide/edit";
    }

    @PostMapping("/admin/slide/update/{id}")
    public String update(@PathVariable("id") Long id, Slide slide, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/slide/edit/" + id.toString();
        }
        if (!photo.isEmpty()) {
            Random rand = new Random();

            String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

            String uploadDir = "uploads/slide/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = photo.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            slide.setImage(fileName);
        }

        slideService.update(id, slide);
        redirect.addFlashAttribute("success", "Updated slide successfully!");

        return "redirect:/admin/slide/edit/" + id.toString();
    }

	@GetMapping("/admin/slide/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
        slideService.delete(slideService.findById(id).get());
        redirect.addFlashAttribute("success", "Deleted slide successfully!");

        return "redirect:/admin/slide";
    }

}
