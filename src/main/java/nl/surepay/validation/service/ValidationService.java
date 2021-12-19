package nl.surepay.validation.service;

import nl.surepay.validation.model.ValidationResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface ValidationService {

    Set<ValidationResponse> validateCSV(InputStream inputStream) throws IOException;

    Set<ValidationResponse> validateJson(InputStream inputStream) throws IOException;
}
