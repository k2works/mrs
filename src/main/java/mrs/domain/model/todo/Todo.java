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

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public boolean isFinished() {
        return finished;
    }

}
