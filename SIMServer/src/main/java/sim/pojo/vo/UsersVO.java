package sim.pojo.vo;

/**
 * The type UsersVO contains all the user information without password, witch means it is for user to get information.
 */
public class UsersVO {
    private String id;
    private String username;
    private String nickname;
    private Double gpa;

    /**
     * Gets id.
     *
     * @return the id
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
     * @return the username
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
     * Gets nickname.
     *
     * @return the nickname
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
     * @return the gpa
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