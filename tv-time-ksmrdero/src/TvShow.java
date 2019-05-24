import java.util.ArrayList;

/**
 * Parse TV show data from http://www.tvmaze.com/api.
 * Written by James Wei on 1/29/19.
 */
class TvShow {
    /** Embedded episode data in JSON. */
    private Episodes _embedded;

    /**
     * Getter for retrieving embedded data.
     * @return Embedded episode data
     */
    Episodes getEmbeddedData() {
        return _embedded;
    }

    /**
     * the class containing all episode information
     */
    class Episodes {
        /* The number of digits in the year. */
        private final static int NUMBER_OF_DIGITS_IN_YEAR = 4;
        /* The start index of the air date string. */
        private final static int START_INTEGER_IN_AIR_DATE = 0;
        /**
         * The array of episodes.
         */
        private SpecificEpisode[] episodes;

        /**
         * Getter to retrieve all episode objects.
         * @return the collection of all specific episodes
         */
        SpecificEpisode[] getAllEpisodes() {
            return episodes;
        }

        /**
         * Grabs all episodes in a given season.
         * @param season the season number
         * @return an arraylist of all the episodes in that season
         */
        ArrayList<SpecificEpisode> getAllEpisodeFromSeason(int season) {
            // if given season does not exist
            if (!(getAllSeasons().contains(season))) {
                return null;
            }

            ArrayList<SpecificEpisode> allEpisodes = new ArrayList<>();
            for (SpecificEpisode episode : episodes) {
                if (episode.getSeason() == season) {
                    allEpisodes.add(episode);
                }
            }
            return allEpisodes;
        }

        /**
         * Retrieve all episodes from year.
         * @param year the year
         * @return all episodes from the year
         */
        ArrayList<SpecificEpisode> getAllEpisodeFromYear(int year) {
            ArrayList<SpecificEpisode> episodesInYear = new ArrayList<>();
            for (SpecificEpisode episode : episodes) {
                int episodeDate = Integer.parseInt(episode.airdate.substring(
                        START_INTEGER_IN_AIR_DATE, NUMBER_OF_DIGITS_IN_YEAR));
                if (episodeDate == year) {
                    episodesInYear.add(episode);
                }
            }

            if (episodesInYear.size() == 0) {
                return null;
            }

            return episodesInYear;
        }

        /**
         * Retrieve all episodes from a given phrase.
         * @param phrase the phrase to search with
         * @return all episodes given phrase is in its name
         */
        ArrayList<SpecificEpisode> getAllEpisodesFromString(String phrase) {
            if (phrase == null) {
                return null;
            }

            ArrayList<SpecificEpisode> episodesFromString = new ArrayList<>();
            for (SpecificEpisode episode : episodes) {
                String episodeName = episode.getName();
                if (episodeName.toLowerCase().contains(phrase.toLowerCase())) {
                    episodesFromString.add(episode);
                }
            }

            if (episodesFromString.size() == 0) {
                return null;
            }

            return episodesFromString;
        }

        /**
         * Retrieve all episodes that are the same episode number in their respective season.
         * @param number the episode number in its respective season
         * @return all episodes with the episode number
         */
        ArrayList<SpecificEpisode> getAllEpisodesFromNumber(int number) {
            ArrayList<SpecificEpisode> episodesFromNumber = new ArrayList<>();
            for (SpecificEpisode episode : episodes) {
                int episodeNumber = episode.getEpisodeNumber();
                if (episodeNumber == number) {
                    episodesFromNumber.add(episode);
                }
            }

            if (episodesFromNumber.size() == 0) {
                return null;
            }

            return episodesFromNumber;
        }

        /**
         * Grabs all the seasons in the TV show.
         * Helper method in checking input in getAllEpisodeFromSeason.
         * @return an arraylist of seasons
         */
        ArrayList<Integer> getAllSeasons() {
            ArrayList<Integer> allSeasons = new ArrayList<>();
            for (SpecificEpisode episode : episodes) {
                // prevent adding duplicate seasons
                if (!(allSeasons.contains(episode.getSeason()))) {
                    allSeasons.add(episode.getSeason());
                }
            }
            return allSeasons;
        }

        /**
         * The specific episode data.
         */
        class SpecificEpisode {
            /** The episode ID. */
            private int id;
            /** The episode name. */
            private String name;
            /** The episode season. */
            private int season;
            /** The episode number. */
            private int number;
            /** The episode air date. */
            private String airdate;
            /** The episode runtime. */
            private int runtime;
            /** The episode summary. */
            private String summary;

            /**
             * Getter for retrieving specific episode ID.
             * @return the id of the specific episode
             */
            int getId() {
                return id;
            }

            /**
             * Getter for retrieving specific episode ID.
             * @return the id of the specific episode
             */
            String getName() {
                return name;
            }

            /**
             * Getter for retrieving specific episode season.
             * @return the season of the specific episode
             */
            int getSeason() {
                return season;
            }

            /**
             * Getter for retrieving specific episode number.
             * @return the number of the specific episode
             */
            int getEpisodeNumber() {
                return number;
            }

            /**
             * Getter for retrieving specific episode runtime.
             * @return the number of the specific episode
             */
            int getRuntime() {
                return runtime;
            }

            /**
             * Getter for retrieving specific episode air date.
             * @return the air date of the specific episode
             */
            String getAirDate() {
                return airdate;
            }

            /**
             * Getter for retrieving specific episode summary.
             * @return the summary of the specific episode
             */
            String getSummary() {
                return summary;
            }
        }
    }
}

