package com.mobilegame.robozzle.analyse

import android.util.Log
import com.mobilegame.robozzle.Extensions.Contains
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.BreadcrumbViewModel
import com.mobilegame.robozzle.domain.InGame.DivineGuideLine
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel

//fun errorLog(secondpart: String, firstpart: String = "") = Log.e(firstpart, secondpart)
//fun verbalLog(secondpart: String, firstpart: String = "") = Log.v(firstpart, secondpart)
//fun infoLog(secondpart: String, firstpart: String = "") = Log.i(firstpart, secondpart)
fun errorLog(firstpart: String = "", secondpart: String) = Log.e(firstpart, secondpart)
fun verbalLog(firstpart: String = "", secondpart: String) = Log.v(firstpart, secondpart)
fun infoLog(firstpart: String = "", secondpart: String) = Log.i(firstpart, secondpart)


fun Print_breadcrumb(breadcrumb: Breadcrumb) {
    var playerStateList = breadcrumb.playerStateList
//    var actionList = breadcrumb.actionList
    var actionList = breadcrumb.actions
    var actionTriggerStarRemove = breadcrumb.actionsTriggerStarRemoveList
    var actionTriggerRemoveStr : String = ""
    var actionTriggerPutStr : String = ""
    var starStr = ""
    var colorSw = ""

    verbalLog("resume", "BreadCrumb")
    Log.i("","playerStateList.size ${playerStateList.size} // actionList.lenght i ${actionList.instructions.length} c ${actionList.colors.length}")
    Log.v("","\t\t\t  -Position-\t\t-Direction-\t\t-Apply-")
//    for (index in playerStateList.indices ) {
    for (index in 0 until playerStateList.size) {
        if (breadcrumb.colorChangeMap.containsKey(index)) colorSw = "c"
        if (actionTriggerStarRemove.Contains(index)) starStr += "*" else starStr = " "
        Log.i("","action ${index} -> \t(${playerStateList[index].pos.line}, ${playerStateList[index].pos.column})\t\t\t'${playerStateList[index].direction.ToChar()}'\t\t\t\t'${actionList.instructions[index]}'     $starStr $colorSw" )
        Log.i("","\n")
        starStr = ""
        colorSw = ""
    }

    if (actionTriggerStarRemove.isEmpty()) { Log.i("actionsTriggerStarRemoveList", "is Empty") }
    else {
        for (index in 0 until actionTriggerStarRemove.size) { actionTriggerRemoveStr += "/ " +actionTriggerStarRemove[index].toString() + " " }
        Log.i("actionsTriggerStarRemoveList", "${actionTriggerRemoveStr}/")
    }
    infoLog("Color Change Map", "${breadcrumb.colorChangeMap}")
    infoLog("actions i", actionList.instructions)
    infoLog("actions c", actionList.colors)
//    infoLog("actionList ", breadcrumb.actionsList.value.toString())
    errorLog("breadcrumb", "resume stop")
}

fun Print_Pos_Dir_Inst(guideLine: DivineGuideLine) {
    var playerStateList = guideLine.playerStateList
    var actionList = guideLine.actionList
    var actionTriggerStarRemove = guideLine.actionsTriggerStarRemoveList
//    var actionTriggerStarPut = guideLine.actionsTriggerStarPutInList
    var actionTriggerRemoveStr : String = ""
    var actionTriggerPutStr : String = ""
    var starStr = ""

    Log.i("","playerStateList.size ${playerStateList.size} // actionList.lenght ${actionList.length}")
    Log.e("","\t\t\t  -Position-\t\t-Direction-\t\t-Apply-")
    for (index in playerStateList.indices ) {
        if (actionTriggerStarRemove.Contains(index)) starStr += "    *"
        Log.i("","action ${index} -> \t(${playerStateList[index].pos.line}, ${playerStateList[index].pos.column})\t\t\t'${playerStateList[index].direction.ToChar()}'\t\t\t\t'${actionList[index]}' $starStr" )
        Log.i("","\n")
        starStr = ""
    }

    if (actionTriggerStarRemove.isEmpty()) { Log.i("actionsTriggerStarRemoveList", "is Empty") }
    else {
        for (index in 0 until actionTriggerStarRemove.size) { actionTriggerRemoveStr += "/ " +actionTriggerStarRemove[index].toString() + " " }
        Log.i("actionsTriggerStarRemoveList", "${actionTriggerRemoveStr}/")
    }
    Log.i("Print_Pos_Dir_Inst", "END")

//    if (actionTriggerStarPut.isEmpty()) { Log.i("actionsTriggerStarPut", "is Empty") }
//    else {
//        for (index in 0 until actionTriggerStarPut.size) { actionTriggerPutStr += actionTriggerStarPut[index].toString() }
//        Log.i("actionsTriggerStarPutList", actionTriggerPutStr)
//    }
}

fun Print_List_Position(listName: String, list: MutableList<Position>) {
    Log.i(listName, "size ${list.size}")
    for (index in 0 until list.size) {
        Log.i("", "(${list[index].line}, ${list[index].column})")
    }
}
fun Print_FunInstructionList(functionInstructionsList: MutableList<FunctionInstructions>){
    for (i in 0 until functionInstructionsList.size) {
//        for (j in 0..functionInstructionsList[i].instructions.length) {
        Log.v("", "${functionInstructionsList[i].instructions}")
//        }
    }
}
fun Print_List_String(list: List<String>) {
    for (i in 0..list.size-1) {
        Log.v("", "$i - > ${list[i]}")
    }
}

fun Print_rb_Level(lvl: RobuzzleLevel) {
    Log.i("Print Level", lvl.name)
    Log.i("", "${lvl.id}")
    Log.i("", "${lvl.difficulty}")
    lvl.map.forEach {
        Log.i("", it)
    }
//    Log.i("", "${lvl}")
    Print_FunInstructionList(lvl.instructionsMenu)
    Print_FunInstructionList(lvl.funInstructionsList)
    Log.i("", "player (${lvl.playerInitial.pos.line}, ${lvl.playerInitial.pos.column})")
    Print_List_Position("stars", lvl.starsList)
}