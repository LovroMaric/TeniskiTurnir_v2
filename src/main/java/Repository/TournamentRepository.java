package Repository;

import Model.*;
import Util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentRepository implements ITournamentRepository {

    @Override
    public void save(Tournament tournament) {

        String sql = "CALL add_tournament(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, tournament.getName());
            stmt.setInt(2, tournament.getFoundedYear());
            stmt.setBigDecimal(3, tournament.getPrizeMoney());
            stmt.setString(4, tournament.getSurface().name());
            stmt.setString(5, tournament.getImagePath());
            stmt.setLong(6, tournament.getCountry().getId());
            stmt.setLong(7, tournament.getCategory().getId());

            stmt.execute();

            System.out.println("TOURNAMENT SAVED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tournament> findAll() {

        List<Tournament> tournaments = new ArrayList<>();

        String sql = "SELECT * FROM get_tournaments()";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Country country = new Country(rs.getLong("country_id"), rs.getString("country_name"));

                Category category = new Category(rs.getLong("category_id"), rs.getString("category_name"));

                Tournament tournament = new Tournament(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("founded_year"),
                        rs.getBigDecimal("prize_money"),
                        SurfaceType.valueOf(rs.getString("surface")),
                        rs.getString("image_path"),
                        country,
                        category,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                tournaments.add(tournament);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tournaments;
    }

    @Override
    public void update(Tournament tournament) {

        String sql = "CALL update_tournament(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, tournament.getId());
            stmt.setString(2, tournament.getName());
            stmt.setInt(3, tournament.getFoundedYear());
            stmt.setBigDecimal(4, tournament.getPrizeMoney());
            stmt.setString(5, tournament.getSurface().name());
            stmt.setString(6, tournament.getImagePath());
            stmt.setLong(7, tournament.getCountry().getId());
            stmt.setLong(8, tournament.getCategory().getId());

            stmt.execute();

            System.out.println("TOURNAMENT UPDATED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "CALL delete_tournament(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, id);
            stmt.execute();

            System.out.println("TOURNAMENT DELETED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayerToTournament(Long tournamentId, Long playerId, String result) {

        String sql = "CALL add_player_to_tournament(?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, tournamentId);
            stmt.setLong(2, playerId);
            stmt.setString(3, result);

            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}