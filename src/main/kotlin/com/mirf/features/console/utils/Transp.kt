package com.mirf.features.console.utils

import kotlin.math.sqrt


class Transp {
    companion object {
        @JvmStatic
        fun transpImage(image1: FloatArray, image2: FloatArray, image3: FloatArray): Array<Array<FloatArray>> {
            val img1 = flattenToSquareImage(image1)
            val img2 = flattenToSquareImage(image2)
            val img3 = flattenToSquareImage(image3)
            val size: Int = sqrt(img1.size.toDouble()).toInt()
            val s = Array(3) { Array(size) { FloatArray(size) } }
            s[0] = img1
            s[1] = img2 // Mishavint: Fix s[1] = img1 ---> s[1] = img2
            s[2] = img3
            return transp(s)
        }

        fun flattenToSquareImage(image: FloatArray): Array<FloatArray> {
            val size: Int = sqrt(image.size.toDouble()).toInt()
            val img = Array(size) { FloatArray(size) }
            for (i in 0 until size) {
                for (j in 0 until size) {
                    img[i][j] = image[size * i + j]
                }
            }
            return img
        }

        @JvmStatic
        fun flatten(array: Array<Array<FloatArray>>): FloatArray {
            //val size : Int = sqrt(array.size.toDouble() / 3).toInt()
            val columns = array.size
            val rows: Int = array[0].size
            val depth: Int = array[0][0].size
            val arr = FloatArray(columns * rows * 3)
            for (i in 0 until depth) {
                for (j in 0 until rows) {
                    for (k in 0 until columns) {
                        arr[i + depth * (j + rows * k)] = array[k][j][i]
                    }
                }
            }
            return arr
        }

        fun transp(array: Array<Array<FloatArray>>): Array<Array<FloatArray>> {
            val f = array.size
            val s: Int = array[0].size
            val t: Int = array[0][0].size
            val newArr = Array(s) { Array(t) { FloatArray(f) } }
            for (i in 0 until f) {
                for (j in 0 until s) {
                    for (k in 0 until t) {
                        newArr[j][k][i] = array[i][j][k]
                    }
                }
            }
            return newArr
        }
    }
}