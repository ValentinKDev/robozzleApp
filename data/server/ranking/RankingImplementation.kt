package com.mobilegame.robozzle.data.server.ranking

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_GET_LIST_WIN
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_LEVEL_ID
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_POST_LIST_WIN
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_POST_WIN
import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import com.mobilegame.robozzle.data.server.tryPostAndCatchErrors
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.User
import io.ktor.client.*


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

    //todo: badlyformated, crashing the server
    override suspend fun postLevelWinListJson(listJson: String, user: User): String {
        infoLog("postPlayerWinJson", "send")
        return client.tryPostAndCatchErrors<String>(
            funName = "getLevelRankingJson",
            encodedPath = "${RANKING_POST_LIST_WIN}/${user.name}",
            objToPost = listJson
        )
    }

    override suspend fun getPlayerWinJson(user: User): String? {
        return client.tryGetAndCatchErrors<String>(
            funName = "getPlayerWinJson",
            encodedPath = "${RANKING_GET_LIST_WIN}/${user.name}",
        )
    }
}

