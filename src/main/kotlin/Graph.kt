package compgraph

fun main() {
    val input = Matrix(arrayOf(arrayOf(1f, 2f, 3f)))

    val weights = Matrix(Array(3){ Array(2) {1f}})

    val bias = Matrix(arrayOf(arrayOf(1f, 2f)))

    val inputExpr = input.toExpr()
    val weightsExpr = weights.toExpr()
    val biasExpr = bias.toExpr()

    val graph = (inputExpr * weightsExpr + biasExpr) apply { it*2 }

    println(graph.calc())
}



class FullyConnectedLayer(val input: Expr, val size: Int, var weights: Expr, var bias: Expr, val activation: (Float) -> Float): Expr {

    constructor(input: Expr, size: Int, weights: Matrix, bias: Matrix, activation: (Float) -> Float):
        this(input, size, weights.toExpr(), bias.toExpr(), activation)

    val expressions = (input * weights + bias) apply activation
    override val shape = expressions.shape

    override fun calc(): Matrix = expressions.calc()
}