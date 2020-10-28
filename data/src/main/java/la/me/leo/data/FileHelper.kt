package la.me.leo.data

import java.io.File
import java.nio.file.Paths

private val projectRoot = Paths.get("").toAbsolutePath().toString()

fun readFile(file: String) = File(projectRoot + file).readText()
