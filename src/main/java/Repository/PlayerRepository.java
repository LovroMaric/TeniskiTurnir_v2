package Repository;

import Model.Country;
import Model.Player;
import Util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository implements CrudRepository<Player> {

    @Override
    public void save(Player player) {

        String sql = "CALL add_player(?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, player.getFirstName());
            stmt.setString(2, player.getLastName());
            stmt.setInt(3, player.getRanking());
            stmt.setString(4, player.getImagePath());

            if (player.getCountry() != null) {
                stmt.setLong(5, player.getCountry().getId());
            } else {
                stmt.setNull(5, Types.BIGINT);
            }

            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Player> findAll() {

        List<Player> players = new ArrayList<>();

        String sql = "SELECT * FROM get_players()";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Country country = null;

                if (rs.getObject("country_id") != null) {
                    country = new Country(rs.getLong("country_id"), rs.getString("country_name"));
                }

                Player player = new Player(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getInt("ranking"), rs.getString("image_path"));

                player.setCountry(country);

                players.add(player);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public void update(Player player) {

        String sql = "CALL update_player(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, player.getId());
            stmt.setString(2, player.getFirstName());
            stmt.setString(3, player.getLastName());
            stmt.setInt(4, player.getRanking());
            stmt.setString(5, player.getImagePath());

            if (player.getCountry() != null) {
                stmt.setLong(6, player.getCountry().getId());
            } else {
                stmt.setNull(6, Types.BIGINT);
            }

            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "CALL delete_player(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, id);
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}