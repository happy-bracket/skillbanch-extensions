package ru.substancial.extensions.datasource

import kotlin.random.Random

class RandomInts {

    fun next(): Int = Random.nextInt()

}

fun RandomInts.getEven(): Int? =
    next().takeIf { it % 2 == 0 }

fun sampleRandom() {

    val gen = RandomInts()

    val a: Int? = gen.getEven()
    val b = gen.getEven()
    val c = gen.getEven()

    val result1 =
        if (a != null && b != null && c != null) {
            a + b + c
        } else {
            null
        }

    val result2 =
        a?.let { aa ->
            b?.let { bb ->
                c?.let { cc ->
                    aa + bb + cc
                }
            }
        }

    val result3: Int? =
        nullable {
            a.bind() + b.bind() + c.bind()
        }

}

class NullableComprehension {

    fun <T> T?.bind(): T {
        return this ?: throw Interrupt()
    }

    class Interrupt : Exception()

}

inline fun <R> nullable(block: NullableComprehension.() -> R): R? {
    val context = NullableComprehension()
    return try {
        context.block()
    } catch(e: NullableComprehension.Interrupt) {
        null
    }
}