package com.ubap.bookslookup.providers.googlebooks;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleBooksLibraryServiceImplTests {

    @Autowired
    private GoogleBooksLibraryServiceImpl googleBooksLibraryService;

    @Test
    public void testDI() {
        Assertions.assertNotNull(this.googleBooksLibraryService);
    }


}
