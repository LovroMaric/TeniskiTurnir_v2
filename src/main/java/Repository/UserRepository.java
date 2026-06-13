package Repository;

import Model.Role;
import Model.User;
import Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class UserRepository implements IUserRepository {

    public Optional<User> login(String username, String password) {

        String sql = "SELECT * FROM login_user(?, ?)";

        try (Connection connection = DBUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getLong("id"));

                user.setUsername(rs.getString("username"));

                user.setPassword(rs.getString("password"));

                user.setRole(Role.valueOf(rs.getString("role")));

                return Optional.of(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void register(String username, String password) {

        String sql = "CALL register_user(?, ?)";

        try (Connection connection = DBUtil.getConnection();

             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            statement.execute();

            System.out.println(
                    "USER REGISTERED!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
