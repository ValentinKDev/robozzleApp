package com.mobilegame.robozzle.data.server.ranking

import android.util.Log
import com.google.gson.JsonParser
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_LEVEL_ID
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_POST_LIST_WIN
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_POST_WIN
import com.mobilegame.robozzle.data.server.User.ServerRet
import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import com.mobilegame.robozzle.data.server.tryPostAndCatchErrors
import com.mobilegame.robozzle.domain.Player.MyString
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.json.JSONStringer
import java.lang.Exception


class RankingImplementation(
    private val client: HttpClient
): RankingService {
    override suspend fun getWinnerListJson(levelId: Int): String? {
        return client.tryGetAndCatchErrors<String>(
            funName = "getLevelRankingJson",
            encodedPath = "$RANKING_LEVEL_ID/$levelId",
        )
    }

    override suspend fun postPlayerWinJson(playerWin: PlayerWin, levelId: Int): String {
        infoLog("postPlayerWinJson", "send")
        return client.tryPostAndCatchErrors<PlayerWin>(
            funName = "getLevelRankingJson",
            encodedPath = "$RANKING_POST_WIN/$levelId",
            objToPost = playerWin
        )
    }

    override suspend fun postLevelWinListJson(list: String, user: User): String {
        infoLog("postPlayerWinJson", "send")
        return client.tryPostAndCatchErrors<String>(
            funName = "getLevelRankingJson",
            encodedPath = "${RANKING_POST_LIST_WIN}/${user.name}",
            objToPost = list
        )
    }

    override suspend fun getPlayerWinJson(user: User): String {
        return client.tryGetAndCatchErrors<String>(
            funName = "getPlayerWinJson",
            encodedPath = ,
        )
    }
}

