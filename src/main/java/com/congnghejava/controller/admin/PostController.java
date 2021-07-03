package com.congnghejava.controller.admin;

import com.congnghejava.model.Post;
import com.congnghejava.service.PostService;
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
import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/admin/post")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "admin/post/index";
    }

    @GetMapping("/admin/post/create")
    public String create(Model model) {
        Post post = new Post();
        post.setIsActive(true);
        model.addAttribute("post", post);
        return "admin/post/create";
    }

    @PostMapping("/admin/post/store")
    public String store(@Validated Post post, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/post/create";
        }

        String nowhitespace = WHITESPACE.matcher(post.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        post.setSlug(slug.toLowerCase(Locale.ENGLISH));

        Random rand = new Random();

        String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

        String uploadDir = "uploads/post/";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = photo.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        post.setImage(fileName);
        post.setViewed(0L);
        postService.save(post);
        redirect.addFlashAttribute("success", "Created post successfully!");

        return "redirect:/admin/post";
    }

    @GetMapping("/admin/post/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "admin/post/edit";
    }

    @PostMapping("/admin/post/update/{id}")
    public String update(@PathVariable("id") Long id, Post post, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/post/edit/" + id.toString();
        }

        String nowhitespace = WHITESPACE.matcher(post.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        post.setSlug(slug.toLowerCase(Locale.ENGLISH));
        if (!photo.isEmpty()) {
            Random rand = new Random();

            String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

            String uploadDir = "uploads/post/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = photo.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            post.setImage(fileName);
        }

        postService.update(id, post);
        redirect.addFlashAttribute("success", "Updated post successfully!");

        return "redirect:/admin/post/edit/" + id.toString();
    }

	@GetMapping("/admin/post/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
        postService.delete(postService.findById(id).get());
        redirect.addFlashAttribute("success", "Deleted post successfully!");

        return "redirect:/admin/post";
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
}
