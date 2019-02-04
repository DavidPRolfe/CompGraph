package compgraph

interface Expr {
    val shape: Shape
    fun calc(): Matrix
}

class MatrixInput(val matrix: Matrix): Expr {
    override val shape = matrix.shape

    override fun calc(): Matrix = matrix
}


class Add(val left: Expr, val right: Expr): Expr {
    override val shape = if (left.shape == right.shape) left.shape else {
        throw WrongShapeException(left.shape, right.shape, "Add Expr")
    }
    override fun calc(): Matrix {
        return left.calc() + right.calc()
    }
}

class Subtract(val left: Expr, val right: Expr): Expr {
    override val shape = if (left.shape == right.shape) left.shape else {
        throw WrongShapeException(left.shape, right.shape, "Subtract Expr")
    }
    override fun calc(): Matrix {
        return left.calc() - right.calc()
    }
}

class Mult(val left: Expr, val right: Expr): Expr {
    override val shape = if (left.shape.dim2 == right.shape.dim1) Shape(left.shape.dim1, right.shape.dim2) else {
        throw WrongShapeException(left.shape, right.shape, "Mult Expr")
    }
    override fun calc(): Matrix {
        return left.calc() * right.calc()
    }
}

class Apply(val expr: Expr, val operation: (Float) -> Float): Expr {
    override val shape = expr.shape
    override fun calc(): Matrix {
        return expr.calc().toEach { operation(it) }
    }
}

operator fun Expr.plus(right: Expr): Expr = Add(this, right)
operator fun Expr.minus(right: Expr): Expr = Subtract(this, right)
operator fun Expr.times(right: Expr): Expr = Mult(this, right)

infix fun Expr.apply(operation: (Float) -> Float): Expr = Apply(this, operation)

fun Matrix.toExpr(): Expr = MatrixInput(this)
