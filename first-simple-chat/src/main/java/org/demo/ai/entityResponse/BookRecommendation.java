package org.demo.ai.entityResponse;

import java.util.List;

public record BookRecommendation(String bookName, String authorName, String overview, List<String> suggestions) {
}
