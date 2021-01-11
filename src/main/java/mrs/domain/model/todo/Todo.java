package mrs.domain.model.todo;

/**
 * やること
 */
public class Todo {
    private int id;
    private String title;
    private String details;
    private boolean finished;

    @Deprecated
    public Todo() {
    }

    public Todo(String title, String details, boolean finished) {
        this.title = title;
        this.details = details;
        this.finished = finished;
    }

    public String title() {
        return title;
    }

    public String details() {
        return details;
    }

    public boolean finished() {
        return finished;
    }

}
