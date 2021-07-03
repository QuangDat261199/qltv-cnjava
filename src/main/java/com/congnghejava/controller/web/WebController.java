package com.congnghejava.controller.web;


import com.congnghejava.model.Book;
import com.congnghejava.model.Category;
import com.congnghejava.model.Contact;
import com.congnghejava.model.Post;
import com.congnghejava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class WebController {
    @Autowired
    private PostService postService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SlideService slideService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("books", bookService.getHighlight());
        model.addAttribute("posts", postService.getHighlight());
        model.addAttribute("slides", slideService.getActive());

        return "web/index";
    }

    @GetMapping("/category")
    public String categories (
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("bookHighlight", bookService.getHighlight());
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Book> bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "web/categories";
    }

    @GetMapping("/category/{slug}")
    public String category(
            @PathVariable("slug") String slug,
            Model model
    ) {
        Category category = categoryService.findBySlug(slug);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", category);
        model.addAttribute("books", bookService.getByCategoryId(category));
        model.addAttribute("bookHighlight", bookService.getHighlight());
        return "web/category";
    }

    @GetMapping("/post")
    public String posts (
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Post> postPage = postService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("postPage", postPage);
        model.addAttribute("postHighlight", postService.getHighlight());

        int totalPages = postPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "web/posts";
    }

    @GetMapping("/post/{slug}")
    public String post (
            @PathVariable("slug") String slug,
            Model model
    ) {
        model.addAttribute("postHighlight", postService.getHighlight());

        Post post = postService.findBySlug(slug);
        post.setViewed(post.getViewed());
        postService.save(post);

        model.addAttribute("post", post);
        return "web/post";
    }

    @GetMapping("/book/{slug}")
    public String book (
            @PathVariable("slug") String slug,
            Model model
    ) {
        model.addAttribute("bookHighlight", bookService.getHighlight());
        model.addAttribute("book", bookService.findBySlug(slug));

        return "web/book";
    }

    @GetMapping("/about")
    public String about () {
        return "web/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contact", new Contact());
        return "web/contact";
    }

    @PostMapping("/contact")
    public String storeContact(
            @Validated Contact contact,
            RedirectAttributes redirect
    ) {
        contactService.save(contact);
        redirect.addFlashAttribute("success", "Created contact successfully!");
        return "redirect:/contact";
    }

    @GetMapping("/search")
    public String search(
        @RequestParam("keyword") String keyword,
        @RequestParam("page") Optional<Integer> page,
        Model model
    ) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("bookHighlight", bookService.getHighlight());
        model.addAttribute("keyword", keyword);
        int currentPage = page.orElse(1);
        int pageSize = 6;

        Page<Book> bookPage = bookService.searchPaginated(keyword, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "web/search";
    }
}
