package nl.surepay.validation.service.impl;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import nl.surepay.validation.model.ValidationRequest;
import nl.surepay.validation.model.ValidationResponse;
import nl.surepay.validation.service.ValidationService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {


    @Override
    public Set<ValidationResponse> validateCSV(InputStream inputStream) throws IOException {
        final Set<ValidationRequest> duplicateTemp = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .skip(1)
                    .map(this::mapCsvToTransactionRequest)
                    .filter(t -> !validateBalance(t) || !duplicateTemp.add(t)) // Set.add() returns false if the element was already in the set.
                    .map(t -> new ValidationResponse(t.getReference(), t.getDescription()))
                    .collect(Collectors.toSet());
        }
    }

    private ValidationRequest mapCsvToTransactionRequest(String csvRow) {
        String[] cells = csvRow.split(",");
        System.out.printf("csv", csvRow);
        return new ValidationRequest(Integer.valueOf(cells[0]),
                cells[1],
                cells[2],
                new BigDecimal(cells[3]),
                new BigDecimal(cells[4]),
                new BigDecimal(cells[5]));
    }


    @Override
    public Set<ValidationResponse> validateJson(InputStream inputStream) throws IOException {
        final Set<ValidationRequest> duplicateTemp = new HashSet<>();
        Set<ValidationResponse> validationRespons = new HashSet<>();
        try (
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream))
        ) {
            reader.beginArray();
            while (reader.hasNext()) {
                ValidationRequest validationRequest = new Gson().fromJson(reader, ValidationRequest.class);
                if (!duplicateTemp.add(validationRequest) || !validateBalance(validationRequest)) {
                    validationRespons.add(new ValidationResponse(validationRequest.getReference(), validationRequest.getDescription()));
                }
            }
            reader.endArray();
            return validationRespons;
        }
    }

    private boolean validateBalance(ValidationRequest transaction) {
        return transaction.getEndBalance().equals(transaction.getStartBalance().add(transaction.getMutation()));
    }

}
