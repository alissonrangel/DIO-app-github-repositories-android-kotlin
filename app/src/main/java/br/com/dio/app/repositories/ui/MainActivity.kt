package br.com.dio.app.repositories.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.core.hideSoftKeyboard
import br.com.dio.app.repositories.databinding.ActivityMainBinding
import br.com.dio.app.repositories.presentation.MainViewModel
import br.com.dio.app.repositories.presentation.SecondViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    //private val viewModel2 by viewModel<SecondViewModel>()
    private val adapter by lazy { RepoListAdapter(this) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rvRepos.adapter = adapter

        //viewModel.getRepoList("AlissonRangel")

        viewModel.repos.observe(this) {
            when(it){
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
        viewModel.owner.observe(this){
            when(it){
                MainViewModel.State2.Loading -> dialog.show()
                is MainViewModel.State2.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State2.Success -> {
                    dialog.dismiss()
                    val htmlURL = it.owner.htmlURL
                    binding.chipGithub.text = "go to github - "+ it.owner.htmlURL
                    binding.chipGithub.visibility = View.VISIBLE
                    binding.chipGithub.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlURL))
                        startActivity(intent)
                    }
                    Glide.with(binding.root.context)
                        .load(it.owner.avatarURL).into(binding.ivOwner)
                    binding.tvOwnerName.text = it.owner.name
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.e(TAG, "onQueryTextSubmit: $query")

        //binding.tvOwnerName.text = query

        query?.let {
            viewModel.getRepoList(it)
            viewModel.getUserInfos(it)
        }

        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    companion object{
        private const val TAG = "TAG"
    }
}