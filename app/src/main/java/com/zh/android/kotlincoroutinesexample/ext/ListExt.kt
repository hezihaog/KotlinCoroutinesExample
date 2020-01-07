package com.zh.android.kotlincoroutinesexample.ext

/**
 * <b>Package:</b> com.zh.android.kotlincoroutinesexample.ext <br>
 * <b>Create Date:</b> 2020-01-07  16:36 <br>
 * <b>@author:</b> zihe <br>
 * <b>Description:</b>  <br>
 */

/**
 * MutableList生成指定个数的数据，需要传入一个block来生成对应的值
 * @param startIndex 开始位置，默认从0开始
 * @param block 生成回调
 */
fun <T> MutableList<T>.generate(
    count: Int,
    startIndex: Int = 0,
    block: (index: Int) -> T
): MutableList<T> {
    for (index in startIndex..count) {
        val value = block(index)
        this.add(value)
    }
    return this
}

/**
 * 生成Int数据，从0到指定的个数
 */
fun MutableList<Int>.generateInt(count: Int): MutableList<Int> {
    return generate(count) { it }
}