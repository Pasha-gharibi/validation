package nl.surepay.validation.web;

import nl.surepay.validation.model.ValidationResponse;
import nl.surepay.validation.service.ValidationService;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(@Autowired ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Set<ValidationResponse>> importCSV(HttpServletRequest request) throws IOException, FileUploadException {
        System.out.println("Streaming...");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            return ResponseEntity.badRequest().build();
        }

        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterStream = upload.getItemIterator(request);
        Set<ValidationResponse> invalids = new HashSet<>();
        while (iterStream.hasNext()) {
            System.out.println("Iterating files...");
            FileItemStream fileItemStream = iterStream.next();
            if (!fileItemStream.isFormField()) {
                String name = fileItemStream.getName();
                System.out.println("Validating file: " + name);
                if (isCsv(name)) {
                    invalids = validationService.validateCSV(fileItemStream.openStream());
                } else if (isJson(name)) {
                    invalids = validationService.validateJson(fileItemStream.openStream());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        return new ResponseEntity<>(invalids, HttpStatus.OK);
    }

    private boolean isJson(String name) {
        return name.split("\\.")[1].equals("json");
    }

    private boolean isCsv(String name) {
        return name.split("\\.")[1].equals("csv");
    }

}
