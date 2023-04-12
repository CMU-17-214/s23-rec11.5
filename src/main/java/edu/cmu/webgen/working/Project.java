package edu.cmu.webgen.working;

import edu.cmu.webgen.project.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a project with articles and events.
 */
public class Project {
    private String ownerOrg;
    final List<Article> articles;
    final String title;
    final HashMap<Object, Set<Topic>> topics;

    public Project(String title, String ownerOrg, List<Article> articles, List<Event> events, HashMap<Object, Set<Topic>> topics) {
        this.title = title;
        this.ownerOrg = ownerOrg;
        this.articles = new ArrayList<>(articles);
        Collections.sort(this.articles);
        this.topics = new HashMap<>(topics);
    }


    @Override
    public String toString() {
        return "Project %s by %s with %d articles".formatted(
                this.title, this.ownerOrg, this.articles.size());
    }

    /**
     * return the title of this project
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }


    public List<Article> getArticles() {
        return this.articles;
    }

    public List<Article> getAllArticles() {
        Stream<Article> articlesStream = this.articles.stream();
        return
                articlesStream.flatMap(Entry::getAllArticles)
                        .sorted().collect(Collectors.toList());
    }

    public Set<Topic> getTopics() {
        return getAllArticles().stream().flatMap((s) -> s.getTopics().stream()).collect(Collectors.toSet());
    }


    public @NotNull Set<Topic> getAllTopics() {
        Set<Topic> result = new HashSet<>();
        for (Set<Topic> t : this.topics.values())
            result.addAll(t);
        return result;
    }

    public void setTopics(Object projectPart, Set<Topic> newTopics) {
        this.topics.put(projectPart, newTopics);
    }

    public String getOwnerOrg() {
        return this.ownerOrg;
    }
}
