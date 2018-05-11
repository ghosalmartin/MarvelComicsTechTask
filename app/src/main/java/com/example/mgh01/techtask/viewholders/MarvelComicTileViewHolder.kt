package com.example.mgh01.techtask.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.mgh01.techtask.extensions.load
import com.example.mgh01.techtask.extensions.openBrowser
import com.example.mgh01.techtask.models.MarvelComicResult
import kotlinx.android.synthetic.main.marvel_comic_search_result_tile.view.*

class MarvelComicTileViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(marvelComicSearchItem: MarvelComicResult) {
        with(marvelComicSearchItem) {
            view.run {
                val thumbnailUrl = "${thumbnail?.path}.${thumbnail?.extension}"
                marvel_comic_search_result_avatar.load(thumbnailUrl)
                marvel_comic_search_result_text.text = title
                setOnClickListener {
                    //TODO move to presenter
                    urls?.find { it.type == "detail" }?.let {
                        context.openBrowser(it.url.orEmpty())
                    }
                }
            }
        }
    }
}