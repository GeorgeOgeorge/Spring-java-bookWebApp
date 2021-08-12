package george.bookwebapp.controllers;

import george.bookwebapp.models.Book;
import george.bookwebapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private BookService bookService;

    @Autowired
    public void setService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
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

    @RequestMapping(value = "/addToCart/{id}", method = RequestMethod.GET)
    public String addToCart(@PathVariable(name = "id") Long id, HttpServletRequest request, RedirectAttributes redirectAtt) {
        HttpSession session = request.getSession();
        if(session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<Book>());
        }
        Book book = this.bookService.getById(id);
        ArrayList<Book> cart = (ArrayList<Book>)session.getAttribute("cart");
        cart.add(book);
        redirectAtt.addAttribute("redirectMessage", " " + book.getName() + " was added to your cart");
        return "redirect:/";
    }

    @RequestMapping(value = "/yourCart", method = RequestMethod.GET)
    public String yourCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("cart") == null) {
            return "redirect:/";
        }else{
            return "cart";
        }
    }

    /*
    * if(session.getAttribute("cart") == null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("cart");
            modelAndView.addObject("cart", session.getAttribute("cart"));
            return modelAndView;
        }*/

    @RequestMapping(value = "/finishCart")
    public String finishCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }


}
