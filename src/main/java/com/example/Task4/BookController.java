package com.example.Task4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookService services;
    //All GetMapping Methods


    //Get all books method
    @GetMapping("/getallbooks")
    public ResponseEntity<API_Response<List<Book>>>getallBooks(){
        try{
            List<Book> books = bookRepository.findAll();
            API_Response<List<Book>> apiResponse = new API_Response<>(200,"Books Retrived",books);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }catch (Exception e){
            API_Response<List<Book>>errorResponse = new API_Response<>(500,"Internal Server Issue",null);
            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/filterbybook")
    public ResponseEntity<List<Book>>getBookByCategory(@RequestParam(required = false)String category ){
        List<Book> books;
        if (category !=null){
            books = bookRepository.findBookByCategory(category);
        }else{

            books = bookRepository.findAll();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/getall")
    public ResponseEntity<API_Response<List<AuthorWithBook_Response>>>getAllAuthorAndBooks(){
        try {
            List<Author> authors = authorRepository.findAll();
            List<AuthorWithBook_Response> authorResponse = new ArrayList<>();

            for (Author author: authors){
                List<Book> books = bookRepository.findByAuthor(author);
                AuthorWithBook_Response response = new AuthorWithBook_Response(author,books);
                authorResponse.add(response);
            }
            API_Response<List<AuthorWithBook_Response>> apiResponse = new API_Response<>(200,"data Retrieved", authorResponse);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }catch (Exception e){
            API_Response<List<AuthorWithBook_Response>> errorresposnse = new API_Response<>(500,"An Error Occurred", null);
            return new ResponseEntity<>(errorresposnse, HttpStatus.OK);
        }
    }
//    public ResponseEntity<List<Book>>getallbooks(){
//        List<Book> books = bookRepository.findAll();
//        return new ResponseEntity<>(books, HttpStatus.OK);
//    }

    // All PostMapping Methods

    @PostMapping
    public ResponseEntity<String> CreateBookWithAuthor (@RequestBody BookWithAuthorRequest request){
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setCategory(request.getCategory());
        book.setPrice(request.getPrice());

        Author author = new Author();
        author.setName(request.getName());
        author.setNationality(request.getNationality());

        book.setAuthor(author);

        bookRepository.save(book);
        authorRepository.save(author);

        return ResponseEntity.ok("Book and Author added Successfully");
    }

    @PostMapping ("/addbook")
    public ResponseEntity<API_Response<Author>>addAuthorWithBooks(@RequestBody BookandAuthorDTO request){
        //Create DTO to add two entities
        Author author = request.getAuthor();
        List<Book> books = request.getBooks();
        //return values to Bookservice
        return services.saveAuthorWithBooks(author,books);
    }

    @PostMapping("/{authorId}/addBook")
    public ResponseEntity<API_Response<List<Book>>> addBookToAuthor(@PathVariable Long authorId, @RequestBody List<Book> books) {
        try {
            // Check if the author exists
            Optional<Author> authorOptional = authorRepository.findById(authorId);
            if (authorOptional.isEmpty()) {
                throw new NoSuchElementException("Author not found.");
            }

            Author author = authorOptional.get();
            //using Books as an Arraylist
            List<Book> savedbooks = new ArrayList<>();

            for (Book book :books) {
                // Check if a book with the same title already exists for the author
                Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(book.getTitle(), author);
                if (existingBook.isPresent()) {
                    throw new DuplicateKeyException("A book with the same title already exists for the author.");
                }

                // Add the book to the author
                book.setAuthor(author);
                Book savedBook = bookRepository.save(book);
                savedbooks.add(savedBook);
            }

            API_Response<List<Book>> apiResponse = new API_Response<>(201, "Book added successfully", savedbooks);

            //apiResponse.setPayload(existingBooks);

            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            API_Response<List<Book>> errorResponse = new API_Response<>(404, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException e) {
            API_Response<List<Book>> errorResponse = new API_Response<>(400, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            API_Response<List<Book>> errorResponse = new API_Response<>(500, "An error occurred while adding the book", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatebook/{bookId}")
    public ResponseEntity<API_Response<Book>>updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook){
        try{
            // Check if the author with the given ID exists
            Optional<Book>existingBookOptional = bookRepository.findById(bookId);
            if (existingBookOptional.isEmpty()){
                throw new NoSuchElementException("Book not found");
            }

            // Get the existing book from the database
            Book existingBook = existingBookOptional.get();

            // Update the existing book with the values from the updated book
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setCategory(updatedBook.getCategory());
            existingBook.setPrice(updatedBook.getPrice());

            // Save the updated book
            Book savedBook = bookRepository.save(existingBook);
            API_Response<Book> apiResponse = new API_Response<>(200, "Book updated successfully", savedBook);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            API_Response<Book> errorResponse = new API_Response<>(404, e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            API_Response<Book> errorResponse = new API_Response<>(500, "An error occurred while updating the Book", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/deletebook/{bookId}")
    public ResponseEntity<API_Response<Book>> deleteAuthor(@PathVariable Long bookId) {
        try {
            // Check if the book with the given ID exists
            Optional<Book> existingBookOptional = bookRepository.findById(bookId);
            if (existingBookOptional.isPresent()) {
                Book book = existingBookOptional.get();

                //delete the Book
                bookRepository.deleteById(bookId);

                API_Response<Book> apiResponse = new API_Response<>(204, "Author and associated books deleted successfully", null);
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            } else {
                // If the author does not exist, return a 404 Not Found response
                API_Response<Book> errorResponse = new API_Response<>(404, "Author not found", null);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            API_Response<Book> errorResponse = new API_Response<>(500, "An error occurred while deleting the author", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
