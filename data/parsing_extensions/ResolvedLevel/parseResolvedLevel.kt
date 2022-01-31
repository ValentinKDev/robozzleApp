package com.mobilegame.robozzle.data.parsing_extensions.ResolvedLevel

import com.mobilegame.robozzle.Extensions.toMutableListOfFunctionInstructions
//import com.mobilegame.robozzle.data.base.ResolvedLevel.ResolvedLevelData
//import com.mobilegame.robozzle.data.remote.dto.ResolvedLevelRequest
import com.mobilegame.robozzle.data.remote.dto.UltimateUser.WinDetailsRequest
import com.mobilegame.robozzle.domain.LevelResolved.WinDetails

//fun ResolvedLevelData.toResolvedLevel(): ResolvedLevel {
//    return ResolvedLevel(
//        lvl_id = this.id,
//        lvl_difficulty = this.difficulty,
//        details = this.stats.toWinDetails(),
//    )
//}

fun String.toWinDetails(): WinDetails {
    val splitedStr = this.split("[[").filterNot { it == "" }

    return WinDetails(
        instructionsNumber = splitedStr[0].replace("[", "")
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "")
            .toInt()
        ,
        actionsNumber = splitedStr[1]
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "")
            .toInt()
        ,
        solutionFound = splitedStr[2].toMutableListOfFunctionInstructions(),
    )
}

//fun ResolvedLevelRequest.toResolvedLeveData(): ResolvedLevelData {
//    var stats = ""
//    println("windetail : ${this.details.toString()}X")
//    stats = this.details.toStr()
//    return ResolvedLevelData(
//        id = this.lvl_id,
//        difficulty = this.lvl_difficulty,
//        stats = this.details.toStr(),
//    )
//}


fun WinDetailsRequest.toStr(): String {
//    return "[[[${this.instructionsNumber}]], [[${this.actionsNumber}]], $solutionFound]"
    return "[[[${this.instructionsNumber}]], [[${this.actionsNumber}]]]"
}
