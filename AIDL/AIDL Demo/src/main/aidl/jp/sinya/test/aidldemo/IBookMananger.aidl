package jp.sinya.test.aidldemo;


import jp.sinya.test.aidldemo.Book;

interface IBookMananger {
    List<Book> getBooksItem();
    void addBook(in Book book);
}
