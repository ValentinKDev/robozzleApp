package com.mobilegame.robozzle

import com.mobilegame.robozzle.Env.file
import com.mobilegame.robozzle.Env.prop
import java.io.File
import java.io.FileInputStream
import java.lang.NullPointerException
import java.util.*
import kotlin.NoSuchElementException

object Env {
    val file = File( "D:\\Android\\Robuzzle_Projet_Android\\app\\src\\main\\res\\config.prop")
    val prop = Properties()
}

fun getMyEnvVariable(variableName: String): String? {
    FileInputStream(file).use {
        prop.load(it)
        return try {
            prop.stringPropertyNames()
                .associateWith { prop.getProperty(it) }
                .getValue(variableName)
        } catch (e: NoSuchElementException) {
            println(e.message)
            null
        } catch (e: NullPointerException) {
            println(e.message)
            null
        }
    }
}
