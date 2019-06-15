package sanchez.sanchez.sergio.brownie.extension

fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}

/**
 * The password must satisfy the following criteria:
 * A minimum of 8 characters
 * At least one lower case letter
 * At least one Upper case letter
 * At least one number or special character
 * No more than two consecutive repeating characters or numbers
 */
fun String.isValidPassword(): Boolean {
    var result = true
    //(?=.*[a-z]) means at least one lower-case letter
    val p = "((?=.*[a-z])(?=.*[A-Z])(?=.*(\\d|[^A-Za-z0-9])).{8,})".toRegex()

    if (matches(p)) {
        var countCharsEqualAndConsecutive = 1
        for (i in 1 until length) {
            if (this[i] == this[i - 1]) {
                countCharsEqualAndConsecutive++
                if (countCharsEqualAndConsecutive == 3) {
                    result = false
                    break
                }
            } else {
                countCharsEqualAndConsecutive = 1
            }
        }
    } else {
        result = false
    }

    return result
}