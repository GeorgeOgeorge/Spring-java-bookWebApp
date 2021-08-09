package george.bookwebapp.controllers;

import george.bookwebapp.models.Book;
import george.bookwebapp.services.BookService;
import george.bookwebapp.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    private BookService bookService;
    private FileStorageService fileStorageService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String homePage(Model model) {
        List<Book> activeBooks = this.bookService.findAllActiveBooks();
        model.addAttribute("activeBooks", activeBooks);
        return "adminHomePage";
    }

    @RequestMapping(value = "/setBook")
    public String setBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "bookForm";
    }

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute @Valid Book book, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAtt) {
        if(errors.hasErrors()) {
            redirectAtt.addAttribute("redirectMessage","book send has some problems");
            return "bookForm";
        }else {
            book.setImgUri(book.getId() + file.getOriginalFilename());
            this.bookService.save(book);
            this.fileStorageService.save(file, book.getId());
            redirectAtt.addAttribute("redirectMessage","book registered");
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/deleteBook/{id}", method = RequestMethod.GET)
    public String deleteBook(RedirectAttributes redirectAtt, @PathVariable(name = "id") Long id) {
        bookService.delete(id);
        redirectAtt.addAttribute("redirectMessage", "book has been deleted");
        return "redirect:/admin";
    }

    @RequestMapping(value = "/editBook/{id}", method = RequestMethod.GET)
    public ModelAndView editBook(@PathVariable(name = "id") Long id) {
        Book book = this.bookService.getById(id);
        ModelAndView modelAndView = new ModelAndView("bookForm");
        modelAndView.addObject("book", book);
        return modelAndView;
    }

}
