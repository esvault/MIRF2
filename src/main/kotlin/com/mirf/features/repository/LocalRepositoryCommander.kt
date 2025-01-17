package com.mirf.features.repository

import com.mirf.core.log.MirfLogFactory
import com.mirf.core.pipeline.PipelineBlock
import com.mirf.core.repository.LinkType
import com.mirf.core.repository.RepositoryCommander
import com.mirf.core.repository.RepositoryCommanderException
import org.slf4j.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Local file system commander, Link = path on a filesystem
 */
class LocalRepositoryCommander constructor(val workingDir: Path = Paths.get("")) : RepositoryCommander {

    private val log: Logger = MirfLogFactory.currentLogger
    private val createdSubCommanders: ConcurrentHashMap<Any, LocalRepositoryCommander> = ConcurrentHashMap()

    init {
        if (!Files.exists(workingDir)) {
            log.info("creating $workingDir")
            Files.createDirectory(workingDir)
        } else {
            log.info("directory $workingDir already exists")
        }
    }

    private val tempFiles = HashSet<Path>()

    override fun generateLink(type: LinkType): String {
        return UUID.randomUUID().toString()
    }

    @Throws(RepositoryCommanderException::class)
    override fun getFile(link: String): ByteArray {
        println("TRYING TO READ FILE FROM: $link")
        val filePath = workingDir.resolve(link)
        try {
            println("ALMOST STARTED")
            return Files.readAllBytes(filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RepositoryCommanderException("Failed to read file bytes", e)
        }
    }

    override fun toString(): String {
        return workingDir.toString()
    }

    override fun getSeriesFileLinks(link: String): Array<String> {
        println("CHECK path for file: " + workingDir.resolve(link).toString())
        val path = workingDir.resolve(link)

        if (File(path.toUri()).isFile) {
            println("CHECKED path for file: $path")
            return arrayOf(path.toString())
        }
        return File(path.toUri()).listFiles()!!.filter { it.isFile && !it.path.toString().contains("input") }
            .map { it.path }.toTypedArray()
    }

    @Throws(RepositoryCommanderException::class)
    override fun saveFile(file: ByteArray, link: String, filename: String): String {
        try {
            val streamLink = workingDir.resolve(link).resolve(filename).toString()
            val stream = FileOutputStream(streamLink)
            stream.write(file)
            return Paths.get(link, filename).toString()
        } catch (e: IOException) {
            throw RepositoryCommanderException("Failed to write file bytes", e)
        }
    }

    /**
     * Creates temp directory
     * @return relative directory path
     */
    fun createSubDir(): Path {
        val path = workingDir.resolve(UUID.randomUUID().toString())
        Files.createDirectory(path)

        tempFiles.add(path)
        return path
    }

    fun createSubDir(prefix: String): Path {

        val path = workingDir.resolve(prefix + "_" + UUID.randomUUID().toString().replace(Regex("-.*"), ""))
        Files.createDirectory(path)

        tempFiles.add(path)
        return path
    }

    /**
     * Removes all temp directories and files from the file system.
     * If any of the file is in use, it will be skipped
     */
    fun cleanupSafe() {
        for (path in tempFiles) {
            try {
                Files.delete(path)
            } catch (e: Exception) {
            }
        }
    }

    override fun createRepoCommanderFor(entity: Any): LocalRepositoryCommander {

        val commander = LocalRepositoryCommander(createSubDir())
        createdSubCommanders[entity] = commander

        return commander
    }

    fun createRepoCommanderForBlock(block: PipelineBlock<*, *>): LocalRepositoryCommander {

        val commander = LocalRepositoryCommander(createSubDir(block.name))
        createdSubCommanders[block] = commander

        return commander
    }


    fun getAbsolutePath(path: String): String {
        return workingDir.resolve(path).toString()
    }
}
