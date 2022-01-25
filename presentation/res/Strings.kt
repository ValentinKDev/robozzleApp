package com.mobilegame.robozzle.presentation.res

//val
val GAME_LOGIC_COROUTINE = "game_logic_coroutine"
val TAG_BUTTON_PLAY = "ButtonPlay"
val TAG_BUTTON_RESET = "ButtonReset"
val TAG_BUTTON_PREV = "ButtonPrev"
val TAG_BUTTON_NEXT = "ButtonNext"


val VERSION_KEY = "version_key"
val REGISTER_TOKEN_KEY = "version_key"
val NONE = "none"

fun TAG_MAP_CASE(line: Int, col: Int): String = "MapCase($line, $col)"
fun TAG_FUNCTION_CASE(line: Int, col: Int): String = "FunctionCase($line, $col)"
/*
Todo :
  - glitch quand on est outOfPath que lon force la suite des instructions avec le boutton Next, bug pour revenir en arriere
  x gerer les bouttons next et prev quand les action sortent de la liste
  x gerer la reapparition des etoiles quand le joueur revient en arriere
 */
