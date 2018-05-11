package com.example.mgh01.techtask.utils

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class TestSchedulerRule : TestRule {

    val testScheduler = TestScheduler()

    var enabled = true

    private val io = Schedulers.io()
    private val computation = Schedulers.computation()
    private val newThread = Schedulers.newThread()

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { if (enabled) testScheduler else io }
                RxJavaPlugins.setComputationSchedulerHandler { if (enabled) testScheduler else computation }
                RxJavaPlugins.setNewThreadSchedulerHandler { if (enabled) testScheduler else newThread }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }

    fun triggerActions() {
        this.testScheduler.triggerActions()
    }
}
