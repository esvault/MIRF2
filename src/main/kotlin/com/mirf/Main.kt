package com.mirf

import com.mirf.features.ecg.data.EcgAttributes
import com.mirf.features.ecg.data.EcgLeadType
import com.mirf.features.ecg.util.*
import com.mirf.playground.DicomImageCircleMaskApplier
import com.mirf.playground.IHD.IhdClassifierAlg
import com.mirf.playground.NiftiTest
import java.time.LocalDateTime
import java.util.*


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val ihdAlg = IhdClassifierAlg()
//        runEcgFromMitBih()
        ihdAlg.execute(listOf("ihd-kaggle.dcm"))
    }

    /**
     * Pipeline for ECG classifying test
     */
    fun runEcgPipeline() {
        val workflow: EcgClassificationWorkflow = EcgClassificationWorkflow.createFull(
            "src/main/resources/ecg/231.dat",
            "src/main/resources/ecg/231.hea",
            "",
            PatientInfo("Leslie", 74, "W", LocalDateTime.now()))

        workflow.pipe.session.newRecord.plusAssign { _, a -> println(a) }

        workflow.exec()
    }


    /**
     * ECG from MIT-BIH classifier test
     */
    fun runEcgFromMitBih() {
        val ecgData = EcgReader.readEcg("src/main/resources/ecg/100.hea", "src/main/resources/ecg/100.dat", 212)
        val beats = EcgBeatExtractor.extractBeatImages(ecgData, EcgLeadType.II)
        EcgClassifier.classify(beats)

    }

    /**
     * ECG from PTB reader test
     */
    fun runEcgFromPtb() {
        val ecgData =
            EcgReader.readEcg("src/main/resources/ecg/s0010_re.hea", "src/main/resources/ecg/s0010_re.dat", 16)

        for (i in ecgData.attributes.getAttributeValue(EcgAttributes.LEADS)[EcgLeadType.II]!!) {
            print("$i ")
        }
    }


    /**
     * Pipeline for DICOM reader module test
     */
    fun runDicom() {
        val dicomFolder = javaClass.getResource("/dicoms")!!.path.fixForCurrentPlatform()
        val resultFolder = javaClass.getResource("/reports")!!.path.fixForCurrentPlatform()
        DicomImageCircleMaskApplier().exec(dicomFolder, resultFolder)
    }

    /**
     * Pipeline for NIFTI reader module test
     */
    fun runNifti() {
        val niftiFile = javaClass.getResource("/nifti/brain.nii")!!.path
        val mhd = javaClass.getResource("/raw/brain.mhd")!!.path
        val resultFolder = javaClass.getResource("/reports")!!.path
        NiftiTest().exec(niftiFile, mhd, resultFolder)
    }
}

private fun String.fixForCurrentPlatform(): String {
    return if (OSValidator.isWindows)
        this.removePrefix("/")
    else
        this
}

object OSValidator {

    private val OS = System.getProperty("os.name").lowercase(Locale.getDefault())

    val isWindows: Boolean
        get() = OS.indexOf("win") >= 0

    val isMac: Boolean
        get() = OS.indexOf("mac") >= 0

    val isUnix: Boolean
        get() = OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0

    val isSolaris: Boolean
        get() = OS.indexOf("sunos") >= 0
}
