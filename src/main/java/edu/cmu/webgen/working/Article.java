package edu.cmu.webgen.working;

import edu.cmu.webgen.project.*;
import edu.cmu.webgen.WebGen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * An article is a key element of a web page. May contain inner content, such as text, inner articles, and events.
 */
public class Article extends Entry {

    Article(@NotNull List<Node> inner, @NotNull String directoryName, @NotNull LocalDateTime created, @NotNull LocalDateTime lastUpdate) {
        super(inner, directoryName, created, lastUpdate);
    }

    public Stream<? extends Article> getAllArticles() {
        return Stream.concat(Stream.of(this), inner.stream().flatMap(Node::getAllArticles));
    }

    public boolean isArticlePinned() {
        return metadata.has("pinned") && !metadata.get("pinned").equals("false");
    }

    public LocalDateTime getPublishedDate() {
        if (metadata.has("date"))
            try {
                return WebGen.parseDate(metadata.get("date"));
            } catch (ParseException e) {
                System.err.println(e.getMessage());
            }

        return getLastUpdate();
    }
}
