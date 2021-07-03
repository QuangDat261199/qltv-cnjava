package com.congnghejava.controller.admin;

import com.congnghejava.model.*;
import com.congnghejava.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReturnController {
    @Autowired
    BorrowService borrowService;

    @Autowired
    ReturnService returnService;

    @Autowired
    ReturnDetailService returnDetailService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/admin/return")
    public String index(Model model) {
        model.addAttribute("returns", returnService.findAll());

        return "admin/return/index";
    }

    @GetMapping("/admin/return/{id}/borrow")
    public String create(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Borrow borrow = borrowService.getOne(id);
        if (borrow.getStatus()) {
            redirect.addFlashAttribute("error", "It was returned");
            return "redirect:/admin/borrow";
        }
        model.addAttribute("borrow", borrow);
        return "admin/return/create";
    }

    @PostMapping("/admin/return/{id}/store")
    public String store(
        @PathVariable("id") Long borrowId,
        @RequestParam("user_id") Long userId,
        @RequestParam("indemnity") Long indemnity,
        @RequestParam("fine") Long fine,
        @RequestParam("total") Long total,
        @RequestParam("book_id[]") Long[] bookId,
        @RequestParam("old_quantity[]") Integer[] quantityBorrow,
        @RequestParam("quantity[]") Integer[] quantity,
        RedirectAttributes redirect
    ) {
        Borrow borrow = borrowService.getOne(borrowId);
        borrow.setStatus(true);
        borrowService.save(borrow);
        User user = userService.getOne(userId);
        Return re = new Return();

        re.setIndemnity(indemnity);
        re.setBorrow(borrow);
        re.setFine(fine);
        re.setTotal(total);
        re.setUser(user);
        re.setDeadline(borrow.getDeadline());

        Return r = returnService.save(re);


        for (int i = 0; i < bookId.length; i++) {
            Book book = bookService.getOne(bookId[i]);
            bookService.updateQuantity(book.getId(), (book.getQuantity() + quantity[i]));

            ReturnDetail returnDetail = new ReturnDetail();
            returnDetail.setQuantity(quantity[i]);
            returnDetail.setQuantityBorrow(quantityBorrow[i]);

            Integer indemnityItem = quantityBorrow[i] - quantity[i];
            returnDetail.setIndemnity(indemnityItem*book.getPrice());
            returnDetail.setBook(book);
            returnDetail.setReturnBorrow(r);
            returnDetailService.save(returnDetail);
        }

        redirect.addFlashAttribute("success", "Created return successfully!");

        return "redirect:/admin/return";
    }

    @GetMapping("/admin/return/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("return", returnService.getOne(id));
        return "admin/return/view";
    }
}
