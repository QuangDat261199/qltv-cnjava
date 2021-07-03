package com.congnghejava.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.congnghejava.model.Category;
import com.congnghejava.service.CategoryService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
public class CategoryController {
	
	@Autowired CategoryService categoryService;

	@GetMapping("/admin/category")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/category/index";
    }
	
	@GetMapping("/admin/category/create")
    public String create(Model model) {
        Category category = new Category();
	    category.setIsActive(true);
        model.addAttribute("category", category);
        return "admin/category/create";
    }

	@PostMapping("/admin/category/store")
    public String store(@Validated Category category, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/category/create";
        }

        String nowhitespace = WHITESPACE.matcher(category.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        category.setSlug(slug.toLowerCase(Locale.ENGLISH));

        Random rand = new Random();

        String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

        String uploadDir = "uploads/category/";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = photo.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        category.setImage(fileName);

        categoryService.save(category);
        redirect.addFlashAttribute("success", "Created category successfully!");

        return "redirect:/admin/category";
    }
	
	@GetMapping("/admin/category/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category/edit";
    }

	@PostMapping("/admin/category/update/{id}")
    public String update(@PathVariable("id") Long id, Category category,@RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/category/edit/" + id.toString();
        }

        String nowhitespace = WHITESPACE.matcher(category.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        category.setSlug(slug.toLowerCase(Locale.ENGLISH));
        if (!photo.isEmpty()) {
            Random rand = new Random();

            String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

            String uploadDir = "uploads/category/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = photo.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            category.setImage(fileName);
        }
        categoryService.update(id, category);
        redirect.addFlashAttribute("success", "Update category successfully!");

        return "redirect:/admin/category/edit/" + id.toString();
    }
	
	@GetMapping("/admin/category/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
	    categoryService.delete(categoryService.findById(id).get());

	    redirect.addFlashAttribute("success", "Delete category successfully!");
        return "redirect:/admin/category";
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
}
