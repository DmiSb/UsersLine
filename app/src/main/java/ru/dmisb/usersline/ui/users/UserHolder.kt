package ru.dmisb.usersline.ui.users

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*
import ru.dmisb.usersline.R
import ru.dmisb.usersline.common.BaseViewHolder
import ru.dmisb.usersline.data.api.HttpConfig
import ru.dmisb.usersline.data.db.entity.User

class UserHolder(itemView: View) : BaseViewHolder<User>(itemView) {

    override fun bindView(data: User, onItemClick: ((item: User) -> Unit)?) {
        with (itemView) {
            userNameView.text = data.name

            val options = RequestOptions()
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_user)

            Glide.with(context)
                .load(String.format(HttpConfig.CONTENT_URL, data.name))
                .apply(options)
                .into(userAvatarView)

            setOnClickListener { onItemClick?.invoke(data) }
        }
    }
}