import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private ArrayList<String> castList;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);

        castList = new ArrayList<String>();
        for (Movie movie : movies) {
            String[] movieCastList = movie.getCast().split("\\|");
            for (String castMember : movieCastList) {
                castMember = castMember.toLowerCase();
                if (!castList.contains(castMember)) {
                    castList.add(castMember);
                }
            }
        }
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";
        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        switch (option) {
            case "t" -> searchTitles();
            case "c" -> searchCast();
            case "k" -> searchKeywords();
            case "g" -> listGenres();
            case "r" -> listHighestRated();
            case "h" -> listHighestRevenue();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                results.add(movie);
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println(choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member search term: ");
        String castMemberSearchTerm = scanner.nextLine().toLowerCase();
        ArrayList<String> results = new ArrayList<>();

        for (String castMember : castList) {
            if (castMember.contains(castMemberSearchTerm)) {
                results.add(castMember);
            }
        }

        for(int i = 0; i < results.size(); i++) {
            for (int j = i + 1; j < results.size(); j++) {
                if(results.get(i).compareTo(results.get(j))>0) {
                    String temp = results.get(i);
                    results.set(i, results.get(j));
                    results.set(j, temp);
                }
            }
        }

        for (int i = 0; i < results.size(); i++) {
            String castMember = results.get(i);
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + castMember);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Movie> moviesWithCast = new ArrayList<>();
        for (Movie movie : movies) {
            String[] castArray = movie.getCast().split("\\|");
            for (String castMember : castArray){
                if (castMember.toLowerCase().equals(results.get(choice))) {
                    moviesWithCast.add(movie);
                }
            }
        }

        System.out.println();
        sortResults(moviesWithCast);
        for (int i = 0; i < moviesWithCast.size(); i++) {
            String title = moviesWithCast.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }

        System.out.print("\nEnter the number of the movie you want to learn more about: ");
        int choice2 = scanner.nextInt() - 1;
        scanner.nextLine();
        displayMovieInfo(moviesWithCast.get(choice2));

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String keywordTerm = scanner.nextLine();
        keywordTerm = keywordTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (Movie movie : movies) {
            String keywords = movie.getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.contains(keywordTerm)) {
                results.add(movie);
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        String[] genres = new String[]{"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "Foreign", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};
        for (int i = 0; i < genres.length; i++) {
            String title = genres[i];
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }

        ArrayList<Movie> results = new ArrayList<>();
        System.out.print("Pick a genre number: ");
        String genreSearchTerm = genres[Integer.parseInt(scanner.nextLine()) - 1].toLowerCase();

        for (Movie movie : movies){
            String[] movieGenres = movie.getGenres().split("\\|");
            for (String genre : movieGenres) {
                if (genre.toLowerCase().equals(genreSearchTerm)){
                    results.add(movie);
                }
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> top50Rated = new ArrayList<>();
        top50Rated.add(movies.get(0));
        int index = 0;
        for (int i = 1; i < movies.size(); i++) {
            for (int j = 1; j < top50Rated.size(); j++) {
                if (movies.get(i).getUserRating() > top50Rated.get(0).getUserRating()){
                    index = 0;
                }
                else if (movies.get(i).getUserRating() >= top50Rated.get(j).getUserRating() && movies.get(i).getUserRating() <= top50Rated.get(j - 1).getUserRating()) {
                    index = j;
                }
                else if (movies.get(i).getUserRating() < top50Rated.get(top50Rated.size() - 1).getUserRating()){
                    index = top50Rated.size();
                }
            }
            top50Rated.add(index, movies.get(i));
            if (top50Rated.size() > 50){
                top50Rated.remove(50);
            }
        }

        for (int i = 0; i < top50Rated.size(); i++) {
            String movie = top50Rated.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + movie + " : " + top50Rated.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = top50Rated.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> top50Rated = new ArrayList<>();
        int index = 0;
        for (Movie movie : movies) {
            for (int j = 1; j < top50Rated.size(); j++) {
                if (movie.getRevenue() > top50Rated.get(0).getRevenue()) {
                    index = 0;
                } else if (movie.getRevenue() > top50Rated.get(j).getRevenue() && movie.getRevenue() < top50Rated.get(j - 1).getRevenue()) {
                    index = j;
                } else if (movie.getRevenue() < top50Rated.get(top50Rated.size() - 1).getRevenue()) {
                    index = top50Rated.size();
                }
            }
            top50Rated.add(index, movie);
            if (top50Rated.size() > 50) {
                top50Rated.remove(50);
            }
        }

        for (int i = 0; i < top50Rated.size(); i++) {
            String movie = top50Rated.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(choiceNum + ". " + movie + " : " + top50Rated.get(i).getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = top50Rated.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}