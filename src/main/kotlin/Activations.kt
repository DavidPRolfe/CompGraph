package compgraph

import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.max


fun sigmoid(x: Float): Float = 1/(1 + exp(-x))

fun relu(x: Float): Float = max(0f, x)

fun tanh(x: Float): Float = 2 * sigmoid(2 * x) - 1

fun leakyRelu(x: Float): Float = if (x > 0f) x else 0.01f * x

fun softplus(x: Float): Float = ln(1 + exp(x))