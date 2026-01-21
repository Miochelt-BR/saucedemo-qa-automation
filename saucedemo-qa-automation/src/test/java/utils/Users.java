package utils;

public class Users {

    public enum UserType {
        STANDARD, LOCKED, PROBLEM, PERFORMANCE, ERROR, VISUAL
    }

    public static final String PASSWORD = "secret_sauce";

    public static String getUsername(UserType type) {
        return switch (type) {
            case STANDARD -> "standard_user";
            case LOCKED -> "locked_out_user";
            case PROBLEM -> "problem_user";
            case PERFORMANCE -> "performance_glitch_user";
            case ERROR -> "error_user";
            case VISUAL -> "visual_user";
        };
    }
}
