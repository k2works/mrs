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

    public Todo(int id, String title, String details, boolean finished) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.finished = finished;
    }

    public int id() {
        return id;
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

    public Todo finish() {
        return new Todo(id, title, details, true);
    }
}
