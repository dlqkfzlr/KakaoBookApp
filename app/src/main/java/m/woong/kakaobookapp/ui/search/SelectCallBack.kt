package m.woong.kakaobookapp.ui.search

import m.woong.kakaobookapp.ui.model.Book

interface SelectCallBack {
    fun selectBook(book: Book)
}