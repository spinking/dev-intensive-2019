package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import kotlinx.android.synthetic.main.item_user_list.*
import kotlinx.android.synthetic.main.item_user_list.sv_indicator
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.spToPixels
import ru.skillbranch.devintensive.models.TextDrawable
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.Utils

/**
 * Created by BashkatovSM on 26.08.2019
 */
class UserAdapter(val listener: (UserItem) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var items: List<UserItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_user_list, parent, false)
        return  UserViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(items[position], listener)

    fun updateData(data : List<UserItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].hashCode() == data[newPos].hashCode()

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(user: UserItem, listener: (UserItem) -> Unit) {
            if(user.avatar != null) {
                Glide.with(itemView)
                    .load(user.avatar)
                    .into(iv_avatar_user)
            } else {
                Glide.with(itemView).clear(iv_avatar_user)
                iv_avatar_user.setInitials(user.initials ?: "??")
                /*iv_avatar_user.setImageDrawable(
                    TextDrawable
                        .builder()
                        .beginConfig()
                        .width(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
                        .height(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
                        .fontSize(48.spToPixels)
                        .endConfig()
                        .buildRound(user.initials ?: "", Utils.randomColor()))*/
            }
            sv_indicator.visibility = if(user.isOnline) View.VISIBLE else View.GONE
            tv_user_name.text = user.fullName
            tv_last_activity.text = user.lastActivity
            iv_selected.visibility = if(user.isSelected) View.VISIBLE else View.GONE
            itemView.setOnClickListener{listener.invoke(user)}
        }

    }
}