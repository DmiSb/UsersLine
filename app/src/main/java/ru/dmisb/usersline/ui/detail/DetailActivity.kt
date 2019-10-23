package ru.dmisb.usersline.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.include_toolbar.*
import ru.dmisb.usersline.R
import ru.dmisb.usersline.data.api.HttpConfig
import ru.dmisb.usersline.data.db.entity.User
import ru.dmisb.usersline.utils.Const

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.init(intent.getStringExtra(Const.EXTRAS_USER_ID) ?: "")
        observeViewModel()

        initToolbar()
    }

    private fun observeViewModel() {
        viewModel.getUser().observe(this, Observer {
            if (it != null) initView(it)
        })
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            title = getString(R.string.detail_title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    private fun initView(user: User?) {
        userNameView.text = user?.name ?: ""
        userNickNameView.text = if (user?.name?.isNotEmpty() == true) "@${user.userName}" else ""

        val options = RequestOptions()
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_user)

        Glide.with(this)
            .load(String.format(HttpConfig.CONTENT_URL, user?.name ?: ""))
            .apply(options)
            .into(userAvatarView)
    }
}