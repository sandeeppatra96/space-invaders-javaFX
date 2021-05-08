package cs1302.api;

import static java.net.URLEncoder.encode;
import static java.lang.String.format;

import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Provides some convenience methods for working with RESTful JSON APIs.
 */
public class Tools {

    /**
     * The UTF-8 charset specified by RFC 2279. This constant is provided to
     * make the {@link java.net.URLEncoder#encode(String, Charset)} method
     * easier to use.
     * @see <a href="http://www.ietf.org/rfc/rfc2279.txt">RFC 2279</a>
     * @see <a href="http://www.unicode.org/unicode/standard/standard.html">Unicode Standard</a>
     */
    public static final Charset UTF8 = Charset.forName("UTF-8");

    /** Hide the constructor. */
    private Tools() {}

    /**
     * Return a {@link JsonElement} for the JSON response from some URL.
     * @param url URL of the desired JSON
     * @return object for the desired JSON
     * @throws IOException if something goes wrong with either the download
     *                     or parsing of the JSON
     */
    public static JsonElement getJson(String url) throws IOException {
        URL location = new URL(url);
        InputStreamReader reader = new InputStreamReader(location.openStream());
        return JsonParser.parseReader(reader);
    } // getJson

    /**
     * Return the {@link JsonElement} described by the {@code keys}.
     * Given a {@code JsonElement} that denotes a portion
     * of valid JSON, hereafter referred to as the {@code root} element, this
     * method attempts to return the element reached by starting at the root and
     * following the path described by the sequence of provided {@code keys}.
     * Here are some various examples, assuming {@code root} refers to the
     * outer-most element in the JSON response that is presented:
     *
     * <pre>
     * {                          // get(root).getAsJsonObject()
     *   numFound: 494,           // get(root, "numFound").getAsInt()
     *   docs: [                  // get(root, "docs").getAsJsonArray()
     *     {                      // get(root, "docs", 0).getAsJsonObject()
     *       title: "Some Title", // get(root, "docs", 0, "title").getAsString()
     *       person: [            // get(root, "docs", 0, "person").getAsJsonArray()
     *         "Person 1",        // get(root, "docs", 0, "person", 0).getAsString()
     *         "Person 2",        // get(root, "docs", 0, "person", 1).getAsString()
     *       ],
     *     },
     *     {                      // get(root, "docs", 1).getAsJsonObject()
     *        title: "Title 2",   // get(root, "docs", 1, "title").getAsString()
     *        ...
     *     },
     *     ...
     *   ]
     * }
     * </pre>
     *
     * @param root starting element
     * @param keys list of keys describing the path from the root to the
     *        desired element
     * @return desired element
     * @throws NullPointerException if a key is encountered that does not exist
     *         at the corresponding location in the path
     * @throws IllegalArgumentException if a key is encountered that is neither
     *         an {@code int} nor a {@code String}.
     */
    public static JsonElement get(JsonElement root, Object... keys) {
        for (Object key : keys) {
            try {
                if (key instanceof String) {
                    root = root.getAsJsonObject().get((String) key);
                } else if (key instanceof Integer) {
                    root = root.getAsJsonArray().get((int) key);
                } else {
                    String message = format("argument type not supported (%s)", key.getClass());
                    throw new IllegalArgumentException(message);
                } // if
            } catch (NullPointerException npe) {
                String message = format("key does not exist (%s)", key);
                throw new NullPointerException(message);
            } // try
        } // for
        return root;
    } //  get

} // Tools
