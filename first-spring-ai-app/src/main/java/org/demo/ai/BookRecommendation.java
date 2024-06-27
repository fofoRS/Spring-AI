package org.demo.ai;

import java.util.List;

public record BookRecommendation(String bookName, String authorName, String overview, List<String> suggestions) {
}
