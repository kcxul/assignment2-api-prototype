package com.csc340.api_prototype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestApiController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/test")
    public String test() {
        return "This is a test for the RestApiController.";
    }

    @GetMapping("/countries")
    public Object getCountries() {
        String url = "https://restcountries.com/v3.1/all";
        try {
            // Fetch JSON data from the URL
            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            // Parse JSON response into a list of Country objects
            List<Country> countryList = new ArrayList<>();

            // Loop through the JSON object and extract name and region.
            for (JsonNode rt : root) {

                // Get name of the country
                JsonNode nameNode = rt.get("name").path("common");
                String name = nameNode.asText();

                // Get region of the country
                String region = rt.get("region").asText();


                // Extract currencies used in the country
                JsonNode currenciesNode = rt.get("currencies");
                StringBuilder currencies = new StringBuilder();
                if (currenciesNode != null && currenciesNode.isObject()) {
                    Iterator<String> fieldNames = currenciesNode.fieldNames();
                    while (fieldNames.hasNext()) {
                        String fieldName = fieldNames.next();
                        JsonNode currencyNode = currenciesNode.get(fieldName).path("name");
                        if (!currencies.isEmpty()) {
                            currencies.append(", ");
                        }
                        currencies.append(currencyNode.asText());
                    }
                }
                Country country = new Country(name, region, currencies.toString());
                countryList.add(country);
            }
            return countryList;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE,
                    null, ex);
            return "error in /countries";
        }
    }
}
