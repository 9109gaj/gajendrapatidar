package Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5054") // React app URL
@RestController
@RequestMapping("/bfhl")
public class ApiController {

    @PostMapping
    public ResponseEntity<Map<String, Object>> handlePost(@RequestBody Map<String, Object> request) {
        // Extracting data and file
        List<String> data = (List<String>) request.get("data");
        String fileB64 = (String) request.get("file_b64");

        // Process input data
        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        boolean isPrimeFound = false;
        String highestLowercase = "";

        for (String item : data) {
            if (item.matches("\\d+")) {
                numbers.add(item);
                if (isPrime(Integer.parseInt(item))) {
                    isPrimeFound = true;
                }
            } else if (item.matches("[a-zA-Z]")) {
                alphabets.add(item);
                if (item.matches("[a-z]")) {
                    if (item.compareTo(highestLowercase) > 0) {
                        highestLowercase = item;
                    }
                }
            }
        }

        // Handle file validation (for the sake of completeness)
        boolean fileValid = fileB64 != null && !fileB64.isEmpty();
        String mimeType = "image/png"; // Placeholder
        int fileSizeKB = 400; // Placeholder

        Map<String, Object> response = new HashMap<>();
        response.put("is_success", true);
        response.put("user_id", "john_doe_17091999");
        response.put("email", "john@xyz.com");
        response.put("roll_number", "ABCD123");
        response.put("numbers", numbers);
        response.put("alphabets", alphabets);
        response.put("highest_lowercase_alphabet", highestLowercase.isEmpty() ? new ArrayList<>() : List.of(highestLowercase));
        response.put("is_prime_found", isPrimeFound);
        response.put("file_valid", fileValid);
        response.put("file_mime_type", mimeType);
        response.put("file_size_kb", fileSizeKB);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET endpoint to retrieve data (for example, a list of processed items or static data)
    @GetMapping
    public ResponseEntity<Map<String, Object>> handleGet() {
        // In a real scenario, this data could be fetched from a database or some other backend service.
        List<String> sampleData = new ArrayList<>();
        sampleData.add("Sample data 1");
        sampleData.add("Sample data 2");
        sampleData.add("Sample data 3");

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Data fetched successfully");
        response.put("data", sampleData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
