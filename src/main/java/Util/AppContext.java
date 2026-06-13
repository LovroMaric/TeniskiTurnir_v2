package Util;

import Repository.*;
import Service.*;

public class AppContext {

    private static final PlayerRepository playerRepository = new PlayerRepository();

    private static final CountryRepository countryRepository = new CountryRepository();

    private static final TournamentRepository tournamentRepository = new TournamentRepository();

    private static final IPlayerService playerService = new PlayerService(playerRepository);

    private static final ICountryService countryService = new CountryService(countryRepository);

    private static final ITournamentService tournamentService = new TournamentService(tournamentRepository);

    private static final CategoryRepository categoryRepository = new CategoryRepository();

    private static final ICategoryService categoryService = new CategoryService(categoryRepository);

    public static IPlayerService getPlayerService() {
        return playerService;
    }

    public static ICountryService getCountryService() {
        return countryService;
    }

    public static ITournamentService getTournamentService() {
        return tournamentService;
    }

    public static ICategoryService getCategoryService() {
        return categoryService;
    }
}