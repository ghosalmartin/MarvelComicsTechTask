package com.example.mgh01.techtask.extensions

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.onDefaultSchedulers(): Observable<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun <T> Single<T>.onDefaultSchedulers(): Single<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun <T> Maybe<T>.onDefaultSchedulers(): Maybe<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun <T> Flowable<T>.onDefaultSchedulers(): Flowable<T> =
        this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
