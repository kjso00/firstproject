package com.ohgiraffers.library.dao;

import com.ohgiraffers.common.JDBCTemplate;
import com.ohgiraffers.library.dto.LibraryDTO;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class LibraryRepository {

    private Properties pros = new Properties();
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public LibraryRepository() {
        try {
            pros.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/library/mapper/library-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LibraryDTO libraryFindByBook(String name) {
        String query = pros.getProperty("libraryFindByTitle");

        con = getConnection();
        LibraryDTO lib = new LibraryDTO();

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                lib.setBook_number(rset.getString("book_number"));
                lib.setBook_title(rset.getString("book_title"));
                lib.setBook_author(rset.getString("book_author"));
                lib.setBook_publisher(rset.getString("Book_publisher"));
                lib.setBook_inventory(rset.getInt("Book_inventory"));
                lib.setBook_statement(rset.getString("Book_statement"));
                lib.setBook_register(rset.getString("Book_register"));
                lib.setBook_modify(rset.getString("Book_modify"));
                lib.setBook_delete(rset.getString("Book_delete"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        return lib;
    }
}