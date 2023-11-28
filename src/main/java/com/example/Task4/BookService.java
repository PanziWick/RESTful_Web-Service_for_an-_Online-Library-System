package com.example.Task4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    //used to add Author with Books

    public ResponseEntity<API_Response<Author>>saveAuthorWithBooks(Author author, List<Book> books) {

        try {
            //Checking if an Author with the same name already exists
            if (authorRepository.findByName(author.getName()).isPresent()) {
                throw new DuplicateKeyException("Author already exists");
            }
            //Save the Author

            Author savedAuthor = authorRepository.save(author);

            //Link books with the saved Author and also check if there are duplicate book titles

            for (Book book : books) {
                Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(book.getTitle(),savedAuthor);

                if (existingBook.isPresent()){
                    //Handiling the duplicate titles
                    throw new DuplicateKeyException("A book with the title'"+ book.getTitle()+ "'already exists");
                }else{
                    //add books with the saved author
                    book.setAuthor(savedAuthor);
                    bookRepository.save(book); }
            }

            //Fetch existing books saved on saved Author

            List<Book> existingBooks = bookRepository.findByAuthor(savedAuthor);

            //Construct the response with Code, Message and Payload

            API_Response<Author> response = new API_Response<>(200,"Author and Books Created",savedAuthor);

            response.setPayload(existingBooks);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

            // return new ResponseEntity<>(new ApiResponse<>(200, "Author and Books Created", savedAuthor), HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new API_Response<>(500, e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new API_Response<>(400, "An Error Occured",null ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}