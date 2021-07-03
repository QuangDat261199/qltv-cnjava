package com.congnghejava.controller.admin;

import com.congnghejava.model.Borrow;
import com.congnghejava.model.Return;
import com.congnghejava.service.BookService;
import com.congnghejava.service.BorrowService;
import com.congnghejava.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class DashboardController {
    @Autowired
    BookService bookService;

    @Autowired
    ReturnService returnService;

    @Autowired
    BorrowService borrowService;


    @GetMapping("/admin/dashboard")
    public String index(
            Model model,
            @RequestParam("month") Optional<Integer> month,
            @RequestParam("year") Optional<Integer> year
    ) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int flagMonth = localDate.getMonthValue();
        int curentMonth = month.orElse(flagMonth);

        int flagYear = localDate.getYear();
        int curentYear = year.orElse(flagYear);

        String my = curentYear + "-";
        if (curentMonth < 10) {
            my += 0;
        }
        my += curentMonth;

        Iterable<Return> re = returnService.getReturnByMonth(my);

        AtomicInteger count = new AtomicInteger();
        AtomicLong fine = new AtomicLong();
        AtomicLong indemnity = new AtomicLong();
        AtomicLong total = new AtomicLong();

        re.forEach((item)->{
            count.set(count.get() + 1);
            fine.addAndGet(item.getFine());
            indemnity.addAndGet(item.getIndemnity());
            total.addAndGet(item.getTotal());
        });

        model.addAttribute("count", count);
        model.addAttribute("fine", this.formatMoney(fine.longValue()));
        model.addAttribute("indemnity", this.formatMoney(indemnity.longValue()));
        model.addAttribute("total", this.formatMoney(total.longValue()));

        ArrayList<ArrayList> borrowGroupByDay = borrowService.getBorrowByMonthGroupByDay(my); //chinhr du lieu o day nay
        ArrayList<ArrayList> returnGroupByDay = returnService.getReturnByMonthGroupByDay(my);

        YearMonth yearMonthObject = YearMonth.of(curentYear, curentMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        // day, borrow, return
        long[][] data = new long[daysInMonth][];
        for(int i = 0; i < daysInMonth; i++) {
            data[i] = new long[3];
            data[i][0] = (long) (i + 1);
            if (borrowGroupByDay.size() > 0) {
                for (int brI = 0; brI < borrowGroupByDay.size(); brI++) {
                    ArrayList flagBorrow = borrowGroupByDay.get(brI);
                    int dayBr = (int) flagBorrow.get(0);
                    if (dayBr == (i+1)) {
                        data[i][1] = (long) flagBorrow.get(1);
                        break;
                    }
                    data[i][1] = 0;
                }
            } else {
                data[i][1] = 0;
            }

            if (returnGroupByDay.size() > 0) {
                for (int reI = 0; reI < returnGroupByDay.size(); reI++) {
                    ArrayList flagReturn = returnGroupByDay.get(reI);
                    int dayRe = (int) flagReturn.get(0);
                    if (dayRe == (i+1)) {
                        data[i][2] = (long) flagReturn.get(1);
                        break;
                    }
                    data[i][2] = 0;
                }
            } else {
                data[i][2] = 0;
            }
        }
        model.addAttribute("data", data);

        model.addAttribute("bookWarning", bookService.getBookWarning());
        model.addAttribute("bookDanger", bookService.getBookDanger());

        model.addAttribute("month", curentMonth);
        model.addAttribute("year", curentYear);

        String previousMonth = "/admin/dashboard?month=" + (curentMonth == 1 ? 12 : curentMonth - 1) + "&year=" + (curentMonth == 1 ? curentYear - 1 : curentYear);
        String nextMonth = "/admin/dashboard?month=" + (curentMonth == 12 ? 1 : curentMonth + 1) + "&year=" + (curentMonth == 12 ? curentYear + 1 : curentYear);

        model.addAttribute("nextMonth", nextMonth);
        model.addAttribute("previousMonth", previousMonth);


        return "admin/dashboard/index";
    }

    private String formatMoney(Long money) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(money);
    }
}
