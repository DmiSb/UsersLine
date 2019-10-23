package ru.dmisb.usersline.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.include_toolbar.*
import ru.dmisb.usersline.R
import ru.dmisb.usersline.common.BaseAdapter
import ru.dmisb.usersline.data.db.entity.User
import ru.dmisb.usersline.ui.detail.DetailActivity
import ru.dmisb.usersline.utils.Const

class UsersActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: BaseAdapter<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        observeViewModel()

        initToolbar()

        adapter = BaseAdapter(
            layout = R.layout.item_user,
            items = viewModel.getUsers().value?.toMutableList() ?: mutableListOf(),
            holderFactory = { UserHolder(it) },
            onItemClick = { showDetail(it.id) }
        )
        layoutManager = LinearLayoutManager(this)
        usersListView.layoutManager = layoutManager
        usersListView.adapter = adapter

        usersListSwipeView.setOnRefreshListener { viewModel.refresh() }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.users_title)
        }
    }

    private val userScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val position = layoutManager.findFirstVisibleItemPosition()
            viewModel.loadUsers(position)
        }
    }

    private fun observeViewModel() {
        viewModel.getUsers().observe(this, Observer {
            if (it?.isNotEmpty() == true) {
                adapter.items.clear()
                adapter.items.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.isLoading().observe(this, Observer {
            if (it == false) usersListSwipeView.isRefreshing = false
            if (it == true && adapter.items.isEmpty()) usersListSwipeView.isRefreshing = true
        })
    }

    private fun showDetail(userId: String) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra(Const.EXTRAS_USER_ID, userId)
        startActivity(detailIntent)
    }

    override fun onResume() {
        super.onResume()
        usersListView.addOnScrollListener(userScrollListener)
    }

    override fun onPause() {
        super.onPause()
        usersListView.removeOnScrollListener(userScrollListener)
    }
}
