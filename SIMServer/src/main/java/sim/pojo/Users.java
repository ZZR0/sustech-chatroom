package sim.pojo;


import javax.persistence.Id;

/**
 * The type Users.
 */
public class Users {
    @Id
    private String id;

    private String username;

    private String password;

    private String nickname;

    private Double gpa;

    /**
     * Gets id.
     *
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets username.
     *
     * @return username username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return password password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets nickname.
     *
     * @return nickname nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets nickname.
     *
     * @param nickname the nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets gpa.
     *
     * @return gpa gpa
     */
    public Double getGpa() {
        return gpa;
    }

    /**
     * Sets gpa.
     *
     * @param gpa the gpa
     */
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}