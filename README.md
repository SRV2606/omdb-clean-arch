Major Focus: Code Architecture, clean coding  styles, used CLEAN ARCHITECTURE  with MVVM and latest tech

Tech Stack : Flow, Hilt, MVVM, coroutines , kotlin, higher order fns, lazy initialization,, mappers, retrofit caching, clean arch

Problem Statement :
OMDB doesn't have a API to get a list of movies as popular,top-rated or something

The hack was to  query a movie which has a lot of results to show as a list during open, query = batman , consider this as home page


Handled pagination with the help of Nopaginate Library
Handled search of the movie with the help of query given with changing the list of results in the grid recycle view
Handled loading, error and success states
Base classes view holders, list adapters, api states handling and extension functions
Handled search cases where no results, clear search, closed keyboard handled
Used flow and coroutines
Used hilt for dependency injection
Caching disabled to check network states



Clean Arch :
Domain : usecases, repository, beans
Data : servermodels, repositoryimpl,mappers ,api serv,
App: ui , viewmodels, hilt injection




