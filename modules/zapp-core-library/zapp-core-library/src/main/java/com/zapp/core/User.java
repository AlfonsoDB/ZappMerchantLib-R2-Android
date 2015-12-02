package com.zapp.core;

/**
 * Represents the details about the financial institution's consumer.
 */
public class User implements IValidatable {

    /**
     * The consumer's first name.
     */
    private String firstName;

    /**
     * The consumer's last name.
     */
    private String lastName;

    /**
     * The consumer's middle name.
     */
    private String middleName;

    /**
     * The consumer's title.
     */
    private String title;

    /**
     * The consumer's email.
     */
    private String email;

    /**
     * The consumer's contact phone number.
     */
    private String phone;

    /**
     * The default constructor.
     */
    public User(final String firstName, final String lastName, final String middleName, final String title, final String email, final String phone)
            throws ZappModelValidationException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.title = title;
        this.email = email;
        this.phone = phone;
        validate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public final void validate() throws ZappModelValidationException {
        if (email == null) {
            ValidationUtils.require(firstName, "User.firstName");
        } else {
            ValidationUtils.requireValidEmail(email, "User.email is not valid");
        }
    }

    @Override
    public String toString() {
        return String.format("User[firstName: %s, email: %s]", firstName, email);
    }
}
