package cs1302.api;

import static java.net.URLEncoder.encode;
import static cs1302.api.Tools.get;
import static cs1302.api.Tools.getJson;
import static cs1302.api.Tools.UTF8;

import java.io.IOException;
import java.util.Optional;

import java.nio.charset.Charset;
import com.google.gson.JsonElement;

/**
 * Example using Open Library Search API.
 *
 * <p>
 * To run this example on Odin, use the following commands:
 *
 * <pre>
 * $ mvn clean compile
 * $ mvn exec:java -Dexec.mainClass=cs1302.api.OpenLibrarySearchApi
 * </pre>
 */
public class OpenLibrarySearchApi {

    private static final String ENDPOINT = "https://openlibrary.org/search.json";

    public static void main(String[] args) {
        search("the lord of the rings").ifPresent(response -> example1(response));
    } // main

    /**
     * An example of some things you can do with a response.
     * @param response the root element of a JSON response
     */
    private static void example1(JsonElement response) {
        // use the "get" method defined later in this class for easier access
        var numFound = get(response, "numFound").getAsInt();
        var first_title = get(response, "docs", 0, "title").getAsString();
        var first_chars = get(response, "docs", 0, "person").getAsJsonArray();
        // print what we found
        System.out.printf("numFound = %d\n", numFound);
        System.out.printf("first title = %s\n", first_title);
        System.out.printf("first characters = %s\n", first_chars);
    } // example1

    /**
     * Return an {@code Optional} describing the root element of the JSON
     * response for a "search" query.
     * @param q query string
     * @return an {@code Optional} describing the root element of the response
     */
    public static Optional<JsonElement> search(String q) {
        try {
            String url = ENDPOINT + "?q=" + encode(q, UTF8);
            return Optional.<JsonElement>ofNullable(getJson(url));
        } catch (IOException ioe) {
            return Optional.<JsonElement>empty();
        } // try
    } // search

    /**
     * Return an {@code Optional} describing the root element of the JSON
     * response for a "searchTitle" query.
     * @param title title string
     * @return an {@code Optional} describing the root element of the response
     */
    public static Optional<JsonElement> searchTitle(String title) {
        try {
            String url = ENDPOINT + "?title=" + encode(title, UTF8);
            return Optional.<JsonElement>ofNullable(getJson(url));
        } catch (IOException ioe) {
            return Optional.<JsonElement>empty();
        } // try
    } // searchTitle

    /**
     * Return an {@code Optional} describing the root element of the JSON
     * response for a "searchAuthor" query.
     * @param author author string
     * @return an {@code Optional} describing the root element of the response
     */
    public static Optional<JsonElement> searchAuthor(String author) {
        try {
            String url = ENDPOINT + "?author=" + encode(author, UTF8);
            return Optional.<JsonElement>ofNullable(getJson(url));
        } catch (IOException ioe) {
            return Optional.<JsonElement>empty();
        } // try
    } // searchAuthor

} // OpenLibrarySearchApi
