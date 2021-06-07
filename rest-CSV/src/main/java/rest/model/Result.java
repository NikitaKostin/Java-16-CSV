package rest.model;

import java.util.List;

public record Result(int count, List<String> statuses) {
}
