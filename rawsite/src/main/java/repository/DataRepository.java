package repository;

import javax.servlet.http.HttpServletResponse;

public interface DataRepository {
    void userSave(String username, String email, String password, String uuid);
    void addCookieId(String uuid, HttpServletResponse response);
}
