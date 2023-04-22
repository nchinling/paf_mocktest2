package sg.edu.nus.iss.paf2.repository;

public class DBQueries {
    public static final String GET_ALL_USERS ="select * from user";
    public static final String SELECT_USER_FROM_USERNAME ="select * from user where username=?";
    public static final String INSERT_USER = "insert into user(user_id, username)"
    +" values (?, ?)";

    public static final String INSERT_TASK = "insert into task(description, priority, due_date, username)"
                                                        +" values (?, ?, ? ,?)";
}
