package projet.backend.dto;

public record TrendingItem(
        Long id,
        String title,
        String genre,
        int nbEpisodes,
        long recentViews,
        double averageNote,
        double score
) {}
