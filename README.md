# Popular-Movies-Stage1
 An app to help users discover popular and recent movies.

 This app will:

 - Upon launch, present the user with an grid arrangement of movie posters.
 - Allow the user to change sort order via a setting: The sort order can be by most popular, or by top rated
 - Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
   - original title
   - movie poster image - background poster
   - A plot synopsis (called overview in the api)
   - user rating (called vote_average in the api)
   - release date
   - original language
   
## What Will I Learn After Stage 1?
You will fetch data from the Internet with theMovieDB API.
You will use adapters and custom list layouts to populate list views.
You will incorporate libraries to simplify the amount of code you need to write

## API Key
The movie information uses [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api) API.
To make your app work, you have to enter your own API key into `build.gradle` file.

```build.gradle (app)
API_KEY="Api Key"
```
