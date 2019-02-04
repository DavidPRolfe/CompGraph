package compgraph

import java.lang.Exception
import kotlin.math.absoluteValue

const val EPSILON = 0.000001

class WrongShapeException(left: Shape, right: Shape, operation: String):
    Exception("Failed to perform $operation. Matrices have wrong shapes. left: $left, right: $right")

data class Shape(val dim1: Int, val dim2: Int)

fun fuzzyEquals(num1: Float, num2: Float): Boolean = (num1 - num2).absoluteValue < EPSILON