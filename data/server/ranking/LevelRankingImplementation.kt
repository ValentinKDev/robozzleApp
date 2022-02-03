package com.mobilegame.robozzle.data.server.ranking

import com.mobilegame.robozzle.data.server.tryGetAndCatchErrors
import com.mobilegame.robozzle.data.server.tryPostAndCatchErrors
import io.ktor.client.*

class LevelRankingImplementation(
    private val client: HttpClient
): LevelRankingService {
    override suspend fun getWinnerListJson(id: Int): String? {
        return client.tryGetAndCatchErrors<String>(
            funName = "getLevelRankingJson",
            encodedPath = ,
        )
    }

    override suspend fun postPlayerWin(playerWinJson: String) {
        client.tryPostAndCatchErrors(
            funName = "postPlayerWin",
            encodedPath = ,
            objToPost = playerWinJson
        )
    }
}