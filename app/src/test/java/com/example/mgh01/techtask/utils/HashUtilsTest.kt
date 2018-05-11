package com.example.mgh01.techtask.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

class HashUtilsTest {

    @Test
    fun test_md5_extension() {
        val plainString = "test"
        assertEquals(
            "098f6bcd4621d373cade4e832627b4f6",
            plainString.md5().toLowerCase())
    }
}