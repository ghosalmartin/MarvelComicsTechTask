package com.example.mgh01.techtask

import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import com.example.mgh01.techtask.adapters.MarvelComicSearchAdapter
import com.example.mgh01.techtask.utils.EqualSpacingItemDecoration
import com.example.mgh01.techtask.viewmodels.MarvelComicSearchViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit



class MainActivity : AppCompatActivity() {

    private val adapterMarvel: MarvelComicSearchAdapter = MarvelComicSearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        marvel_comic_search_recycler_view.run {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.GRID))
            adapter = adapterMarvel
        }
        marvel_comic_search_text_field.setShowAlways(true)

        val viewModel = ViewModelProviders.of(this).get(MarvelComicSearchViewModel::class.java)

        LiveDataReactiveStreams.fromPublisher(viewModel.getComicResultsPublisher(getSearchFlowable()))
            .observe(this, Observer {
                marvel_comic_search_recycler_view.visibility = View.VISIBLE
                adapterMarvel.update(it)
            })

        LiveDataReactiveStreams.fromPublisher(viewModel.getPreviousSearches())
            .observe(this, Observer {
                marvel_comic_search_text_field.setAdapter(ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, it))
            })
    }

    private fun getSearchFlowable(): Flowable<String> =
        RxTextView.textChangeEvents(marvel_comic_search_text_field)
            .map { it.text().toString() }
            .throttleLast(100, TimeUnit.MILLISECONDS)
            .debounce(200, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .toFlowable(BackpressureStrategy.LATEST)
}
