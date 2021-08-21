package br.com.dio.app.repositories.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.ItemRepoBinding
import com.bumptech.glide.Glide

class RepoListAdapter(var context: Context) : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DiffCallback()) {

//    companion object{
//        var listRepositories: MutableList<Repo> = mutableListOf()
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo){
            //listRepositories.add(item)
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description
            binding.tvRepoLanguage.text = item.language
            binding.chipStar.text = item.stargazersCount.toString()
            binding.ivOwner.setOnClickListener {
                //binding.itemRepo.visibility = View.INVISIBLE
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlURL))
                context.startActivity(intent)
            }
//            Glide.with(binding.root.context)
//                .load(item.owner.avatarURL).into(binding.ivOwner)
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Repo>(){
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

}
