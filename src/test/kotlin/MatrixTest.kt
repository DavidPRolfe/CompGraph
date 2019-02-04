package compgraph

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row


class MatrixTest: StringSpec({
    "Matrix times test" {
        forall(
            row(Matrix(arrayOf( arrayOf( 1f ))),
                Matrix(arrayOf( arrayOf( 2f ))),
                Matrix(arrayOf( arrayOf( 2f )))),
            row(Matrix(arrayOf( arrayOf( 1f, 1f, 1f ))),
                Matrix(arrayOf( arrayOf( 2f ),
                                arrayOf( 3f ),
                                arrayOf( 4f ))),
                Matrix(arrayOf( arrayOf( 9f )))),
            row(Matrix(arrayOf( arrayOf( 1f ),
                                arrayOf( 2f ),
                                arrayOf( 3f ))),
                Matrix(arrayOf( arrayOf( 4f, 5f, 6f ))),
                Matrix(arrayOf( arrayOf( 4f, 5f, 6f ),
                                arrayOf( 8f, 10f, 12f ),
                                arrayOf( 12f, 15f, 18f )))),
            row(Matrix(arrayOf( arrayOf( 1f, 2f ),
                                arrayOf( 3f, 4f ))),
                Matrix(arrayOf( arrayOf( 5f, 6f ),
                                arrayOf( 7f, 8f ))),
                Matrix(arrayOf( arrayOf( 19f, 22f ),
                                arrayOf( 43f, 50f ))))
        ) { left: Matrix, right: Matrix, expected: Matrix ->
            left * right shouldBe expected
        }

        // Checking that illegal shapes raise an exception
        forall(
            row(Matrix(arrayOf( arrayOf(1f, 2f))),
                Matrix(arrayOf( arrayOf(1f, 2f, 3f)))),
            row(Matrix(arrayOf( arrayOf(1f),
                                arrayOf(2f))),
                Matrix(arrayOf( arrayOf(1f),
                                arrayOf(2f),
                                arrayOf(3f))))
        ) { left: Matrix, right: Matrix ->
            shouldThrow<WrongShapeException> { left * right }
        }
    }

    "Matrix add test" {
        forall(
            row(Matrix(arrayOf( arrayOf(1f, 2f, 3f))),
                Matrix(arrayOf( arrayOf(4f, 5f, 6f))),
                Matrix(arrayOf( arrayOf(5f, 7f, 9f)))),
            row(Matrix(arrayOf( arrayOf(1f),
                                arrayOf(2f),
                                arrayOf(3f))),
                Matrix(arrayOf( arrayOf(4f),
                                arrayOf(5f),
                                arrayOf(6f))),
                Matrix(arrayOf( arrayOf(5f),
                                arrayOf(7f),
                                arrayOf(9f))))
        ) { left: Matrix, right: Matrix, expected: Matrix ->
            left + right shouldBe expected
        }

        // Checking that illegal shapes raise an exception
        forall(
            row(Matrix(arrayOf( arrayOf(1f, 2f))),
                Matrix(arrayOf( arrayOf(1f, 2f, 3f)))),
            row(Matrix(arrayOf( arrayOf(1f),
                arrayOf(2f))),
                Matrix(arrayOf( arrayOf(1f),
                    arrayOf(2f),
                    arrayOf(3f))))
        ) { left: Matrix, right: Matrix ->
            shouldThrow<WrongShapeException> { left + right }
        }
    }

    "Matrix transpose test" {
        forall(
            row(Matrix(arrayOf( arrayOf( 1f, 2f, 3f))),
                Matrix(arrayOf( arrayOf( 1f ),
                                arrayOf( 2f ),
                                arrayOf( 3f ))))
        ) { input: Matrix, expected: Matrix ->
            input.transpose shouldBe expected
        }
    }
})