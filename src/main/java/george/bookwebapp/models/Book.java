package george.bookwebapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 50, message = "input a value between 2-50")
    private String name;
    @Size(min = 2, max = 50, message = "input a value between 2-50")
    private String publisher;
    @Size(min = 2, max = 50, message = "input a value between 2-50 ")
    private String author;
    @Min(value = 1450, message = "input a value bigger then 2")
    private Short year;
    @Size(min = 2, max = 50, message = "input a value between 2-50 ")
    private String genre;
    private String imgUri;
    private Short pageNumber;
    private Date deleted = null;
}
