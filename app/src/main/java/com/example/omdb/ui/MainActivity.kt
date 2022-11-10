package com.example.omdb.ui


import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.utlils.NetworkUtil
import com.example.domain.models.ClientResult
import com.example.domain.models.MovieBean
import com.example.domain.models.SearchBean
import com.example.omdb.R
import com.example.omdb.base.BaseActivity
import com.example.omdb.collectEvent
import com.example.omdb.databinding.ActivityMainBinding
import com.example.omdb.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener
import ru.alexbykov.nopaginate.paginate.NoPaginate
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnLoadMoreListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var paginate: NoPaginate
    private var page = 1
    private var totalResults: Int? = 0
    private var searchQuery = ""
    private var movieList: MutableList<SearchBean> = mutableListOf()

    @Inject
    lateinit var networkUtil: NetworkUtil
    private val moviesAdapter by lazy {
        MoviesAdapter(itemClickListener = {
            // we can open any other view , or bottomsheet or anythign here if needed with data
            Toast.makeText(this, "movie released : year" + it.year, Toast.LENGTH_SHORT).show()
        }, context = this@MainActivity)
    }


    override fun readArguments(extras: Intent) {

    }

    override fun setupUi() {
        mainViewModel.getInitialMoviesWithPagination(page)
        handleSearch()
        setupRecyclerView()
    }


    private fun handleSearch() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //on submit of the query then hiding keyboard
                    mainViewModel.getMoviesFromSearch(query = query, page)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchQuery = newText
                    binding.searchView.isSubmitButtonEnabled = true
                }
                if (newText == null || newText.length < 2 || newText.isEmpty()) {
                    //clearing the movie list totally if the conditions meet
                    movieList.clear()
                    page = 1
                }
                return true
            }

        })


    }


    private fun setupRecyclerView() {
        binding.movieCardsRV.layoutManager =
            GridLayoutManager(this, 2)
        binding.movieCardsRV.adapter = moviesAdapter
        paginate = NoPaginate.with(binding.movieCardsRV)
            .setLoadingTriggerThreshold(1)
            .setOnLoadMoreListener(this)
            .build()
    }

    override fun observeData() {
        collectEvent(mainViewModel.moviesList) {
            when (it) {
                is ClientResult.InProgress -> {
                    if (movieList.isEmpty() && searchQuery.isEmpty()) {
                        //loading screen for first time
                        renderLoadingScreen(true)
                    }
                    updateLoadingStateOfPagination(true)
                    updateErrorStateOfPagination(false)
                }
                is ClientResult.Success -> {
                    if (movieList.isEmpty() && searchQuery.isEmpty()) {
                        renderLoadingScreen(false)
                    }
                    updateLoadingStateOfPagination(false)
                    updateErrorStateOfPagination(false)
                    renderSuccessScreen(it)
                }
                is ClientResult.Error -> {
                    updateLoadingStateOfPagination(false)
                    updateErrorStateOfPagination(true)
                    renderErrorScreenWithRetry(it)
                }
            }
        }


    }

    private fun renderSuccessScreen(clientResult: ClientResult.Success<MovieBean>) {
        if (binding.retryLayoutHolder.retryLayoutCL.visibility == View.VISIBLE) {
            binding.retryLayoutHolder.retryLayoutCL.visibility = View.GONE
        }
        if (binding.noMovieFoundHolder.retryLayoutCL.visibility == View.VISIBLE) {
            binding.noMovieFoundHolder.retryLayoutCL.visibility = View.GONE
        }
        if (binding.movieCardsRV.visibility == View.GONE) {
            binding.movieCardsRV.visibility = View.VISIBLE
        }


        totalResults = clientResult.data.totalResults
        if (clientResult.data.search?.size == 0) {
            renderNoMovieFoundScreen()
        } else {
            //if something from prev search is still relevent
            if (movieList.isNotEmpty()) {
                movieList.filter { searchBean ->
                    searchBean.title!!.contains(searchQuery)
                }
            }
            clientResult.data.search?.forEachIndexed { index, search ->
                movieList.add(search)
            }
            moviesAdapter.submitList(movieList.toList())
        }
    }

    override fun setListener() {

    }

    override fun onDestroy() {
        if (::paginate.isInitialized)
            paginate.unbind()
        super.onDestroy()
    }

    private fun updateLoadingStateOfPagination(isLoading: Boolean) {
        if (::paginate.isInitialized)
            paginate.showLoading(isLoading)
    }

    private fun updateErrorStateOfPagination(isError: Boolean) {
        if (::paginate.isInitialized)
            paginate.showError(isError)
    }

    private fun hasLoadedAllItems(): Boolean {
        if (::paginate.isInitialized) {
            val totalPages = totalResults?.div(10)?.minus(1)
            if (page >= totalPages!!) {
                paginate.setNoMoreItems(true)
                return true
            }
        }
        return false
    }

    override fun onLoadMore() {
        if (!hasLoadedAllItems()) {
            page++
            if (searchQuery.isEmpty()) {
                //if searchQuery is totally empty then search back for the main home page results
                mainViewModel.getInitialMoviesWithPagination(page)
            } else {
                //search for the given word at current time
                mainViewModel.getMoviesFromSearch(searchQuery, page)
            }
        } else {
            mainViewModel.getInitialMoviesWithPagination(page)
        }
    }

    private fun renderLoadingScreen(isLoading: Boolean) {
        if (isLoading) {
            binding.circularProgressView.visibility = View.VISIBLE
        } else {
            binding.circularProgressView.visibility = View.GONE
        }
    }

    private fun renderErrorScreenWithRetry(clientResult: ClientResult.Error) {
        binding.movieCardsRV.visibility = View.GONE
        binding.retryLayoutHolder.retryLayoutCL.visibility = View.VISIBLE
        binding.retryLayoutHolder.retryMessageTV.text = clientResult.error.message
        binding.retryLayoutHolder.retryCTA.setOnClickListener {
            if (searchQuery.isEmpty()) {
                mainViewModel.getInitialMoviesWithPagination(page)
            } else {
                mainViewModel.getMoviesFromSearch(searchQuery, page)
            }
        }
    }

    private fun renderNoMovieFoundScreen(hasNetwork: Boolean = true) {
        binding.movieCardsRV.visibility = View.GONE
        binding.noMovieFoundHolder.retryLayoutCL.visibility = View.VISIBLE
        binding.noMovieFoundHolder.retryMessageTV.text =
            if (hasNetwork) "No Movies Found" else "No Network detected"
    }


}