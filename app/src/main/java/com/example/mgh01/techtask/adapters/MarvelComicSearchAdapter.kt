package com.example.mgh01.techtask.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mgh01.techtask.R
import com.example.mgh01.techtask.models.MarvelComicResult
import com.example.mgh01.techtask.viewholders.MarvelComicTileViewHolder

class MarvelComicSearchAdapter : RecyclerView.Adapter<MarvelComicTileViewHolder>() {

    private var results = mutableListOf<MarvelComicResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelComicTileViewHolder =
            MarvelComicTileViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.marvel_comic_search_result_tile, parent, false))

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: MarvelComicTileViewHolder, position: Int) {
        holder.bind(results[position])
    }

    fun update(results: List<MarvelComicResult>?) {
        if (results == null || results.isEmpty()) return
        this.results.clear()
        this.results.addAll(results.toMutableList())
        notifyDataSetChanged()
    }
}