package mrs.domain.model.user;

/**
 * 氏名
 */
public class Name {
    String firstName;
    String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
