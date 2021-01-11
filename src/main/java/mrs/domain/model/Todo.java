package mrs.domain.model;

public class Todo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column todo.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column todo.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column todo.details
     *
     * @mbg.generated
     */
    private String details;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column todo.finished
     *
     * @mbg.generated
     */
    private Boolean finished;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column todo.id
     *
     * @return the value of todo.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column todo.id
     *
     * @param id the value for todo.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column todo.title
     *
     * @return the value of todo.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column todo.title
     *
     * @param title the value for todo.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column todo.details
     *
     * @return the value of todo.details
     *
     * @mbg.generated
     */
    public String getDetails() {
        return details;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column todo.details
     *
     * @param details the value for todo.details
     *
     * @mbg.generated
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column todo.finished
     *
     * @return the value of todo.finished
     *
     * @mbg.generated
     */
    public Boolean getFinished() {
        return finished;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column todo.finished
     *
     * @param finished the value for todo.finished
     *
     * @mbg.generated
     */
    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}