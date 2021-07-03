package com.congnghejava.controller.admin;

import com.congnghejava.model.Book;
import com.congnghejava.model.Borrow;
import com.congnghejava.model.BorrowDetail;
import com.congnghejava.model.User;
import com.congnghejava.service.BookService;
import com.congnghejava.service.BorrowDetailService;
import com.congnghejava.service.BorrowService;
import com.congnghejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @Autowired
    BorrowDetailService borrowDetailService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/admin/borrow")
    public String index(Model model) {
        model.addAttribute("borrows", borrowService.findAll());

        return "admin/borrow/index";
    }

    @GetMapping("/admin/borrow/create")
    public String create(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/borrow/create";
    }

    @PostMapping("/admin/borrow/store")
    public String store(
            @RequestParam("user_id") Long user_id,
            @RequestParam("total") Long total,
            @RequestParam("payment") Long payment,
            @RequestParam("deadline") String deadline,
            @RequestParam("book_id[]") Long[] book_id,
            @RequestParam("quantity[]") Integer[] quantity,
            RedirectAttributes redirect
            ) throws ParseException {
        Borrow b = new Borrow();
        User u = userService.getOne(user_id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(deadline);

        b.setDeadline(date);
        b.setPayment(payment);
        b.setTotal(total);
        b.setUser(u);
        b.setStatus(false);

        Borrow borrow = borrowService.save(b);

        for ( int i = 0; i < book_id.length ; i++) {
            BorrowDetail borrowDetail = new BorrowDetail();
            borrowDetail.setBorrow(borrow);
            Book book = bookService.getOne(book_id[i]);

            bookService.updateQuantity(book.getId(), (book.getQuantity() - quantity[i]));

            borrowDetail.setBook(book);
            borrowDetail.setQuantity(quantity[i]);
            borrowDetailService.save(borrowDetail);
        }
        redirect.addFlashAttribute("success", "Created borrow successfully!");

        return "redirect:/admin/borrow";
    }

    @GetMapping("/admin/borrow/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        model.addAttribute("borrow", borrowService.getOne(id));
        return "admin/borrow/view";
    }

    @GetMapping("/admin/borrow/delete/{id}")
    public RedirectView delete(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Borrow borrow = borrowService.getOne(id);
        if (borrow.getStatus()) {
            redirect.addFlashAttribute("error", "Không thể xóa phiếu mượn!");

            return new RedirectView("admin/borrow");
        }
        borrowService.delete(borrow);
        redirect.addFlashAttribute("success", "Deleted borrow successfully!");

        return new RedirectView("/admin/borrow");
    }
}
