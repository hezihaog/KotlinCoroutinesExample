package com.zh.android.kotlincoroutinesexample.ext

import java.io.*

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-08  14:34 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

/**
 * 单个文件复制
 */
fun copyFile(srcFile: File, destFile: File) {
    val fis = FileInputStream(srcFile);
    val fos = FileOutputStream(destFile)
    val bis = BufferedInputStream(fis)
    val bos = BufferedOutputStream(fos)
    val buffer = ByteArray(1024)
    var len: Int
    while (true) {
        len = bis.read(buffer)
        if (len == -1) break
        bos.write(buffer, 0, len)
    }
    fis.close()
    fos.close()
}

/**
 * 带文件夹复制
 */
fun copyDirToDir(srcFile: File, destFile: File) {
    val fileList = (srcFile.listFiles() ?: arrayOf()).filterNotNull()
    for (file in fileList) {
        //是文件就拷贝
        val newFile = File(destFile.absolutePath, file.name)
        if (file.isFile) {
            copyFile(file, newFile)
        } else {//如果是目录就递归复制
            //如果目标文件不存在，则创建
            if (!newFile.exists()) {
                if (!newFile.mkdir()) return
            }
            copyDirToDir(file, newFile)
        }
    }
}