package george.bookwebapp.controllers;

import george.bookwebapp.models.Book;
import george.bookwebapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private BookService bookService;

    @Autowired
    public void setService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = {"/index" , "/"}, method = RequestMethod.GET)
    public String homePage(Model model, HttpServletResponse response) {
        List<Book> activeBooks = this.bookService.findAllActiveBooks();
        model.addAttribute("activeBooks", activeBooks);
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss:SSS");
        Date date = new Date();
        Cookie cookie = new Cookie("visit", date_formatter.format(date));
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "index";
    }

}
