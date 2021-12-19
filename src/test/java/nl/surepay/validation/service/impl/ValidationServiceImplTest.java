package nl.surepay.validation.service.impl;

import nl.surepay.validation.model.ValidationResponse;
import nl.surepay.validation.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidationServiceImplTest {

    @Autowired
    private ValidationService service;

    @Test
    public void testIfCsvFileExist() {
        String path = "./src/test/resources";
        String fileName = "records.csv";
        File file = new File(path, fileName);
        assertTrue(file.exists());
    }

    @Test
    public void testIfJsonFileExist() {
        String path = "./src/test/resources";
        String fileName = "records.json";
        File file = new File(path, fileName);
        assertTrue(file.exists());
    }

    @Test
    void validateCSV() throws IOException {

        Integer repeatedReference = 112806;

        String path = "./src/test/resources";
        String fileName = "records.csv";
        File csvFile = new File(path, fileName);
        InputStream inputStream = new FileInputStream(csvFile);

        Set<ValidationResponse> responses =  service.validateCSV(inputStream);
        assertEquals( responses.size(), 2);
        assertTrue( responses.stream().anyMatch(t->t.getTransactionReference().equals(repeatedReference)));
    }

    @Test
    void validateJson() throws IOException  {
        Integer firstIncorrectEndBalance = 165102;
        Integer secondIncorrectEndBalance = 167875;

        String path = "./src/test/resources";
        String fileName = "records.json";
        File csvFile = new File(path, fileName);
        InputStream inputStream = new FileInputStream(csvFile);

        Set<ValidationResponse> responses =  service.validateJson(inputStream);
        assertEquals( responses.size(), 2);
        assertTrue( responses.stream().anyMatch(t->t.getTransactionReference().equals(firstIncorrectEndBalance)));
        assertTrue( responses.stream().anyMatch(t->t.getTransactionReference().equals(secondIncorrectEndBalance)));
    }
}
