Appendix: Creating Games in JavaFX
==================================

.. contents::

Game Engine in Starter Code
***************************

.. |cs1302_game_game| replace:: ``Game``
.. _cs1302_game_game: https://github.com/cs1302uga/cs1302-omega/blob/main/src/main/java/cs1302/game/Game.java

The starter code includes the |cs1302_game_game|_ class, an abstract parent class
for games written as JavaFX components. You are not required to use this class,
but you may find it useful due to the way it handles keyboard-related events.

You might think of the |cs1302_game_game|_ class as a lightweight game engine
that let's you describe a game according to repeated iterations of an internal
game loop that executes on the JavaFX Application Thread (JFXAT) -- iterations
of the game loop and the JFXAT loop are interleaved by use of a ``Timeline``
to minimize the chances of the app appearing frozen.

To create a game, you create a concrete subclass of |cs1302_game_game|_
that overrides the ``update`` method. That method describes one iteration
of the game loop. Some things you might do in this method include:

1. Inspect the current state of the game.
2. Add or remove child nodes.
3. Change the position of child nodes.

.. |jfx_region| replace:: ``Region``
.. _jfx_region: https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/layout/Region.html

The concrete subclass that you create indirectly extends the JavaFX
|jfx_region|_ class -- please read the documentation for the |jfx_region|
class to see everything that's available.

An example of a simple game that is built by extending the |cs1302_game_game|_
class is presented in the next appendix section.

Example Game: DemoGame
**********************

.. |cs1302_demogame| replace:: ``DemoGame``
.. _cs1302_demogame: https://github.com/cs1302uga/cs1302-omega/blob/main/src/main/java/cs1302/game/DemoGame.java

The |cs1302_demogame|_ class in the starter code illustrates how to create
a game by extending the |cs1302_game_game|_ class. Also, a |cs1302_demogame|
object is included in the ``OmegaApp`` class, which means that you can
run an instance of the game by running the starter code.

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
