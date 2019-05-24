import java.util.ArrayList;

/**
 * Aggregation methods for JSON's parsed in TvShow.java.
 * Written by James Wei on 1/29/19.
 */
class Aggregation {

    /**
     * Returns the number of episodes in a given list.
     * @param theEpisodes the collection of episodes
     * @return the number of episodes in the list
     */
    static int getNumberOfEpisodes(ArrayList<TvShow.Episodes.SpecificEpisode> theEpisodes) {
        if (theEpisodes == null) {
            return 0;
        }
        return theEpisodes.size();
    }

    /**
     * Returns the average length of episodes in a given a collection of episodes.
     * @param theEpisodes the collection of episodes.
     * @return the average lengths of episodes
     */
    static float getAverageLengthOfEpisodes(ArrayList<TvShow.Episodes.SpecificEpisode> theEpisodes) {
        if (theEpisodes == null) {
            return 0;
        }

        int lengthSum = 0;
        for (TvShow.Episodes.SpecificEpisode episode : theEpisodes) {
            lengthSum += episode.getRuntime();
        }
        return (float) lengthSum / theEpisodes.size();
    }

    /**
     * Returns the maximum length of a episode.
     * @param theEpisodes the collection of episodes
     * @return the maximum length of the list of episodes
     */
    static int getMaxLengthOfEpisode(ArrayList<TvShow.Episodes.SpecificEpisode> theEpisodes) {
        if (theEpisodes == null) {
            return 0;
        }

        int maxLength = 0;
        for (TvShow.Episodes.SpecificEpisode episode : theEpisodes) {
            if (episode.getRuntime() > maxLength) {
                maxLength = episode.getRuntime();
            }
        }
        return maxLength;
    }

    /**
     * Returns the minimum length of a episode.
     * @param theEpisodes the collection of episodes
     * @return the minimum length of the list of episodes
     */
    static int getMinLengthOfEpisode(ArrayList<TvShow.Episodes.SpecificEpisode> theEpisodes) {
        if (theEpisodes == null) {
            return 0;
        }

        int minLength = theEpisodes.get(0).getRuntime();
        for (TvShow.Episodes.SpecificEpisode episode : theEpisodes) {
            if (episode.getRuntime() < minLength) {
                minLength = episode.getRuntime();
            }
        }
        return minLength;
    }
}
