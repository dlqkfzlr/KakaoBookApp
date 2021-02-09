package m.woong.kakaobookapp.ui.books

import m.woong.kakaobookapp.ui.model.Book

interface SelectCallBack {
    fun selectBook(book: Book)
}