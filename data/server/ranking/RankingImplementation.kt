package com.mobilegame.robozzle.data.server.ranking

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_LEVEL_ID
import com.mobilegame.robozzle.data.server.HttpRoutes.RANKING_POST_WIN
import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import com.mobilegame.robozzle.data.server.tryPostAndCatchErrors
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

    override suspend fun postPlayerWinJson(playerWinJson: String, levelId: Int) {
        infoLog("postPlayerWinJson", "send")
        client.tryPostAndCatchErrors(
            funName = "postPlayerWin",
            encodedPath = "$RANKING_POST_WIN/$levelId",
            objToPost = playerWinJson
        )
    }
}