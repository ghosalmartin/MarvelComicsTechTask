package com.example.mgh01.techtask.utils

import java.security.MessageDigest

fun String.md5() = hashString("MD5", this)

/**
 * Supported algorithms on Android:
 *
 * Algorithm	Supported API Levels
 * MD5          1+
 * SHA-1	    1+
 * SHA-224	    1-8,22+
 * SHA-256	    1+
 * SHA-384	    1+
 * SHA-512	    1+
 */
private fun hashString(type: String, input: String): String =
    MessageDigest
        .getInstance(type)
        .digest(input.toByteArray()).joinToString(separator = "") {
            String.format("%02X", it)
        }

