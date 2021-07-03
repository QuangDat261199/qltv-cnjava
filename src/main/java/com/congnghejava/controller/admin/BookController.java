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
import org.springframework.web.servlet.view.RedirectView;
import com.congnghejava.model.Book;
import com.congnghejava.service.BookService;
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
public class BookController {

	@Autowired BookService bookService;

	@Autowired CategoryService categoryService;

	@GetMapping("/admin/book")
    public String index(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "admin/book/index";
    }

	@GetMapping("/admin/book/create")
    public String create(Model model) {
	    model.addAttribute("categories", categoryService.findAll());
	    Book book = new Book();
	    book.setIsActive(true);
        model.addAttribute("book", book);
        return "admin/book/create";
    }

    @PostMapping("/admin/book/store")
    public String store(@Validated Book book, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/book/create";
        }

        String nowhitespace = WHITESPACE.matcher(book.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        book.setSlug(slug.toLowerCase(Locale.ENGLISH));

        Random rand = new Random();

        String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

        String uploadDir = "uploads/book/";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = photo.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        book.setImage(fileName);
        book.setIsDelete(false);
        bookService.save(book);
        redirect.addFlashAttribute("success", "Created book successfully!");

        return "redirect:/admin/book";
    }

	@GetMapping("/admin/book/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/book/edit";
    }

	@PostMapping("/admin/book/update/{id}")
    public Object udpate(@PathVariable("id") Long id, Book book, @RequestParam("photo") MultipartFile photo, BindingResult result, RedirectAttributes redirect) throws IOException {
        if (result.hasErrors()) {
            redirect.addFlashAttribute("error", result.getAllErrors().toString());
            return "redirect:/admin/book/edit/" + id.toString();
        }

        String nowhitespace = WHITESPACE.matcher(book.getName()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        book.setSlug(slug.toLowerCase(Locale.ENGLISH));

        if (!photo.isEmpty()) {
            Random rand = new Random();

            String fileName = rand.nextInt(10000) + "_" + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()).toLowerCase(Locale.ROOT));

            String uploadDir = "uploads/book/";

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = photo.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            book.setImage(fileName);
        }
        book.setIsDelete(false);
        bookService.update(id, book);

        redirect.addFlashAttribute("success", "Updated book successfully!");

        return new RedirectView("/admin/book/edit/" + id);
    }

	@GetMapping("/admin/book/delete/{id}")
    public RedirectView delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Book book = bookService.getOne(id);
        book.setIsDelete(true);
        bookService.save(book);

        redirect.addFlashAttribute("success", "Deleted book successfully!");

        return new RedirectView("admin/book");
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
}
