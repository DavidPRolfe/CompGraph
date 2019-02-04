package compgraph


data class Matrix(val data: Array<Array<Float>>) {
    val shape = Shape(data.size, data[0].size)

    /**
     * Returns the transpose of the existing matrix. This creates a new matrix.
     */
    val transpose: Matrix
        get() {
            val out = Matrix.zeroes(Shape(shape.dim2, shape.dim1))

            for (i in 0 until shape.dim1) {
                for (j in 0 until shape.dim2) {
                    out.data[j][i] = data[i][j]
                }
            }

            return out
        }

    companion object {
        fun zeroes(shape: Shape) = Matrix(Array(shape.dim1) { Array(shape.dim2) { 0f } })
    }

    /**
     * Adds two matrices. They must be the same shape.
     * @throws WrongShapeException if the matrix shapes aren't equal
     */
    operator fun plus(right: Matrix): Matrix {
        if (shape != right.shape) throw WrongShapeException(shape, right.shape, "plus")

        val out = Matrix.zeroes(shape)

        for (i in 0 until shape.dim1) {
            for (j in 0 until shape.dim2) {
                out.data[i][j] = data[i][j] + right.data[i][j]
            }
        }

        return out
    }

    /**
     * Subtracts two matrices. They must be the same shape.
     * @throws WrongShapeException if the matrix shapes aren't equal
     */
    operator fun minus(right: Matrix): Matrix {
        if (shape != right.shape) throw WrongShapeException(shape, right.shape, "minus")

        val out = Matrix.zeroes(shape)

        for (i in 0 until shape.dim1) {
            for (j in 0 until shape.dim2) {
                out.data[i][j] = data[i][j] - right.data[i][j]
            }
        }

        return out
    }

    /**
     * Multiplies two matrices
     * @throws WrongShapeException if the left matrix dim2 does't match the right matrix dim1
     */
    operator fun times(right: Matrix): Matrix {
        if (this.shape.dim2 != right.shape.dim1) throw WrongShapeException(shape, right.shape, "times")

        val out = Matrix.zeroes(Shape(this.shape.dim1, right.shape.dim2))

        for (i in 0 until out.shape.dim1) {
            for (j in 0 until out.shape.dim2) {
                var temp = 0f
                for (k in 0 until shape.dim2) {
                    temp += this[i, k] * right[k, j]
                }
                out[i, j] = temp
            }
        }
        return out
    }

    /**
     * Applies a given function to every cell
     */
    fun toEach(operation: (Float) -> Float): Matrix {
        val out = copy()

        for (i in 0 until shape.dim1) {
            for (j in 0 until shape.dim2) {
                out[i, j] = operation(out[i, j])
            }
        }

        return out
    }


    /**
     * This is used for testing. Might not be completely accurate ¯\_(ツ)_/¯
     */
    override fun equals(other: Any?): Boolean = when (other) {
            is Matrix -> {
                if (shape == other.shape) {
                    var out = true
                    for (i in 0 until shape.dim1) {
                        for (j in 0 until shape.dim2) {
                            if (!fuzzyEquals(this[i, j], other[i, j])) out = false
                        }
                    }
                    out
                } else false
            }
            else -> false
        }

    /**
     * Pretty prints the matrix in with set width values for each cell in the matrix.
     */
    override fun toString(): String {
        var out = "["

        for (i in 0 until shape.dim1) {
            out += if (i == 0) "[" else "\n ["
            for (j in 0 until shape.dim2) {
                out += if (j == 0) " " else ", "
                out += "%.2f".format(this[i, j])
            }
            out += if (i == shape.dim1 - 1) " ]" else " ],"
        }
        out += "]"
        return out
    }

    inline operator fun get(x: Int, y: Int): Float = data[x][y]

    inline operator fun set(x: Int, y: Int, value: Float) {
        data[x][y] = value
    }
}