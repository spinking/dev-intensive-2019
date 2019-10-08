package ru.skillbranch.devintensive.ui.archive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.adapters.ArchiveItemTouchHelperCallback
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel

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
        chatAdapter = ChatAdapter{
            val snackbar = Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG)
            val snackBarView = snackbar.view
            snackBarView.background = getDrawable(R.drawable.bg_snackbar)
            val textView: TextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(getSnackbarColor())
            snackbar.show()
        }
        //ДЗ кастом материал декоратор time: 1:13 tutorial 5
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(getDrawable(R.drawable.divider))
        val touchCallback = ArchiveItemTouchHelperCallback(chatAdapter) {
            viewModel.restoreFromArchive(it.id)

            //ДЗ добавить обработчик отмены добавления time: 1:33 tutorial 5
            val snackbar = Snackbar.make(rv_archive_list, "Восстановить чат с ${it.title} из архива?", Snackbar.LENGTH_LONG)
                .setAction("отмена".toUpperCase()) { _ -> viewModel.addToArchive(it.id) }
            snackbar.setActionTextColor(getAccentColor())
            val snackBarView = snackbar.view
            snackBarView.background = getDrawable(R.drawable.bg_snackbar)
            val textView: TextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(getSnackbarColor())
            snackbar.show()

        }

        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(rv_archive_list)

        with(rv_archive_list) {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            addItemDecoration(divider)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.getChatData().observe(this, Observer { chatAdapter.updateData(it) })
    }

    private fun getAccentColor(): Int {
        val tv = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, tv, true)
        return tv.data
    }
    private fun getSnackbarColor(): Int {
        val tv = TypedValue()
        theme.resolveAttribute(R.attr.colorSnackBarText, tv, true)
        return tv.data
    }
}
