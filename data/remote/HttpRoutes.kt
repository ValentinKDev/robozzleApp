package com.mobilegame.robozzle.data.remote

object HttpRoutes {
    const val PORT = 8888
    const val HOST = "192.168.0.13"
//    const val HOST = "192.168.0.8"
    private const val BASE_URL = "http://$HOST:$PORT"
    const val Auth_test = "$BASE_URL/me"
    const val APP_CONFIG = "/config/data"
    const val LEVEL_PATH = "/level_list"
    const val LEVELS_NUMBER_PATH = "/all_levels/size"
    const val PLAYER_PATH = "/players"
    const val USER_PATH = "/users"
    const val USER_REGISTRATION_PATH = "/newuser643/register"
    const val USER_ULTIMATE_PATH = "/user"
    const val TEST_TOKEN = "/test/token"
    const val TRY_ME = "/me"
    const val LOGIN_PATH = "/login"
    const val WIN_LEVELS = "/win_levels"
}