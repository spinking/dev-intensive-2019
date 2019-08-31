package ru.skillbranch.devintensive.ui.archive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class ArchiveActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewModel: ArchiveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        initToolbar()
        initViews()
        initViewModel()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        chatAdapter = ChatAdapter(this){
            Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG).show()
        }
        //ДЗ кастом материал декоратор time: 1:13 tutorial 5
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val touchCallback = ChatItemTouchHelperCallback(chatAdapter) {
            viewModel.addToArchive(it.id)
            chatAdapter.notifyItemChanged(0)

            val snackbar: Snackbar

            //ДЗ добавить обработчик отмены добавления time: 1:33 tutorial 5
            snackbar = Snackbar.make(rv_chat_list, "Восстановить чат с ${it.title} из архива?", Snackbar.LENGTH_LONG)
                .setAction("Нет") { _ -> viewModel.restoreFromArchive(it.id) }
            snackbar.setActionTextColor(getAccentColor())
            val snackBarView = snackbar.view
            snackBarView.setBackgroundColor(getPrimaryColor())
            snackbar.show()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.getChatData().observe(this, Observer { chatAdapter.updateData(it) })
    }

    private fun getPrimaryColor(): Int {
        val tv = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, tv, true)
        return tv.data
    }

    private fun getAccentColor(): Int {
        val tv = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, tv, true)
        return tv.data
    }
}
