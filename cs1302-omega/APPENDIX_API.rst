.. |the_dog_api| replace:: TheDogApi
.. _the_dog_api: https://thedogapi.com/

Appendix: Working with RESTful JSON APIs
========================================

.. contents::

API Tools in Starter Code
*************************

.. |api_tools| replace:: ``Tools``
.. _api_tools: https://github.com/cs1302uga/cs1302-omega/blob/main/src/main/java/cs1302/api/Tools.java

All of the RESTful JSON API examples presented in the appendix utilize the |api_tools|_
class that's provided in the starter code. You are not required to use that class, but
you may find that it makes some things a little easier to work with (e.g., working with
JSON-related objects produced by Gson).

:NOTE:
   The examples utilize static imports in order to make some of the methods in the
   |api_tools|_ class available without qualifying calls with the class name (e.g.,
   make it possible to call ``get(~)`` instead of ``Tools.get(~)``). Here are the
   lines for those static imports:

   .. code:: java

      import static cs1302.api.Tools.get;
      import static cs1302.api.Tools.getJson;
      import static cs1302.api.Tools.UTF8;

API Example: Open Library Search API
************************************

The |openlib_api|_ is nice because it does not require you to
register with the API provider to get an API key -- an API key is not required. According to
the API documentation, the endpoint is this URL::

  "https://openlibrary.org/api/search.json"

Basic usage includes what looks like three mutually exclusive parameters, ``q``, ``title``,
and ``author``. The table below provides an example for each parameter as well as a link
to an interactive example of a potential response (via Code Beautify):

.. |openlib_ex1| replace:: Example 1
.. _openlib_ex1: https://codebeautify.org/jsonviewer?url=https://openlibrary.org/search.json?q=the+lord+of+the+rings

.. |openlib_ex2| replace:: Example 2
.. _openlib_ex2: https://codebeautify.org/jsonviewer?url=https://openlibrary.org/search.json?title=the+lord+of+the+rings

.. |openlib_ex3| replace:: Example 3
.. _openlib_ex3: https://codebeautify.org/jsonviewer?url=https://openlibrary.org/search.json?author=tolkien

==========  =========================  ================================  ================
Parameter   Example Value              Query String [1]_                 Example Response
==========  =========================  ================================  ================
``q``       ``the lord of the rings``  ``?q=the+lord+of+the+rings``      |openlib_ex1|_
``title``   ``the lord of the rings``  ``?title=the+lord+of+the+rings``  |openlib_ex2|_
``author``  ``tolkien``                ``?author=tolkien``               |openlib_ex3|_
==========  =========================  ================================  ================

.. [1] Remember, the ``value`` in ``param=value`` contained in a query string needs
   to be url-encoded. If you are using the |api_tools|_ class, then ``Tools.UTF8``
   is provided to make calling ``URLEncoder.encode(value, Tools.UTF8)``; unlike the
   version of ``encode`` that takes a ``String`` for the encoding, the overload that
   takes a ``Charset`` does not throw any checked exceptions.

The API documentation says the JSON response should look something like this::

  {
      "start": 0,
      "num_found": 629,
      "docs": [
          {...},
          {...},
          {...},
          ...
          {...}]
  }

To perform a regular search (using parameter ``q``) for "the lord of the rings,"
you might use something like the following:

.. code:: java

   String endpoint = "https://openlibrary.org/api/search.json";
   String url = endpoint + "?q=" + encode("the lord of the rings", UTF8));

   try {
       JsonElement root = getJson(url);
       int numFound = get(root, "numFound").getAsInt();
       System.out.printf("numFound = %d\n", numFound);
       ...
   } catch (IOException ioe) {
       ...
   } // try

The API documentaton says that each document in the list of ``docs`` should look
something like this::

  {
      "cover_i": 258027,
      "has_fulltext": true,
      "edition_count": 120,
      "title": "The Lord of the Rings",
      "author_name": [
          "J. R. R. Tolkien"
      ],
      "first_publish_year": 1954,
      ...
  }

Suppose the document in the example above is at index ``2`` in the ``docs``
array. You might access some of its members using something like the
following (assuming the same ``root`` from the previous code example):

.. code:: java

   // example 1
   boolean hasFulltext = get(root, "docs", 2, "has_fulltext").getAsBoolean();
   String title = get(root, "docs", 2, "title").getAsString();

.. code:: java

   // example 2
   JsonElement doc2 = get(root, "docs", 2);
   boolean hasFulltext = get(doc2, "has_fulltext").getAsBoolean();
   String title = get(doc2, "title").getAsString();

The starter code also includes an example using this API in the
|open_library_search_api|_ class. That example does little to no error
checking.

.. |open_library_search_api| replace:: ``OpenLibrarySearchApi``
.. _open_library_search_api: https://github.com/cs1302uga/cs1302-omega/blob/main/src/main/java/cs1302/api/OpenLibrarySearchApi.java

API Example: TheDogApi
**********************

This API requires you to register with the API provider to
get an API key -- an API key is required; see the API's |the_dog_api_auth|_
page for information on how to register for a key.

.. |the_dog_api_auth| replace:: Authentication
.. _the_dog_api_auth: :https://docs.thedogapi.com/authentication

Once you have your API key, you will want to store it in a ``.properties`` file
so that it's not hard-coded in your program. For example, you might store the
API key in ``resources/config.properties`` like this::

  thedogapi.apikey=YOUR-API-KEY

An example of how to read the values from ``resources/config.properties``
is provided in the starter code
`here <https://github.com/cs1302uga/cs1302-omega/blob/main/src/main/java/cs1302/api/PropertiesExample.java>`__.
We will assume that your code retrieves your API key and stores it in a string
using some code similar to this:

.. code:: java

   final String apiKey = config.getProperty("thedogapi.apikey");

According to the API documentation, the endpoint for TheDogApi is this URL::

  "https://api.thedogapi.com/v1"

This API provides different "methods" that are accessed via different paths
relative to the endpoint::

  "https://api.thedogapi.com/v1/method/name"

The table below provides an example for some of he methods; you should consult
the API documentation for information about other potential methods:

==================  =================  =========================  ================
Method              Description        Query String               Example Response
==================  =================  =========================  ================
``/breeds``         List the Breeds    ``?api_key=KEY``           |dogapi_ex1|_
``/breeds/search``  Search for Breeds  ``?api_key=KEY&q=golden``  |dogapi_ex2|_
==================  =================  =========================  ================

.. |dogapi_ex1| replace:: Example 1
.. _dogapi_ex1: https://codebeautify.org/jsonviewer/cbba90d7

.. |dogapi_ex2| replace:: Example 2
.. _dogapi_ex2: https://codebeautify.org/jsonviewer/cb771263

Below is an example of what the JSON response for the ``/breeds`` method looks like.
Notice how the outer-most element refers to a JSON array and not a JSON object. This
particular response contains an array of objects::

  [
    {...},
    {
      ...
      "id": 1,
      "name": "Affenpinscher",
      "bred_for": "Small rodent hunting, lapdog",
      "breed_group": "Toy",
      "life_span": "10 - 12 years",
      "temperament": "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving",
      "origin": "Germany, France",
      "reference_image_id": "BJa4kxc4X",
      "image": {
        "id": "BJa4kxc4X",
        "width": 1600,
        "height": 1199,
        "url": "https://cdn2.thedogapi.com/images/BJa4kxc4X.jpg"
      }
    },
    {...},
    ...
    {...},
  ]

To request the list of breeds using the ``/breeds`` method,
you might use something like the following:

.. code:: java

   String endpoint = "https://api.thedogapi.com/v1";
   String method = "/breeds";
   String url = endpoint + "?api_key=" + apiKey);

   try {
       JsonElement root = getJson(url);
       int numFound = get(root).getAsJsonArray().size();
       System.out.printf("numFound = %d\n", numFound);
       ...
   } catch (IOException ioe) {
       ...
   } // try

Suppose you want to access the breed object at index ``1`` in the array.
You might access some of its members using something like the
following (assuming the same ``root`` from the previous code example):

.. code:: java

   // example 1
   String breedName = get(root, 1, "name").getAsString();
   String breedOrigin = get(root, 1, "origin").getAsString();

.. code:: java

   // example 2
   JsonElement breed1 = get(root, 1);
   String breedName = get(breed1, "name").getAsString();
   String breedOrigin = get(breed1, "origin").getAsString();

.. #############################################################################

.. copyright and license information
.. |copy| unicode:: U+000A9 .. COPYRIGHT SIGN
.. |copyright| replace:: Copyright |copy| Michael E. Cotterell, Bradley J. Barnes, and the University of Georgia.
.. |license| replace:: CC BY-NC-ND 4.0
.. _license: http://creativecommons.org/licenses/by-nc-nd/4.0/
.. |license_image| image:: https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg
                   :target: http://creativecommons.org/licenses/by-nc-nd/4.0/
.. standard footer
.. footer:: |license_image|

   |copyright| This work is licensed under a |license|_ license to students
   and the public. The content and opinions expressed on this Web page do not necessarily
   reflect the views of nor are they endorsed by the University of Georgia or the University
   System of Georgia.
