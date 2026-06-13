package Repository;

import Model.Country;
import Util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository implements CrudRepository<Country> {

    @Override
    public void save(Country country) {

        String sql = "CALL add_country(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, country.getName());
            stmt.execute();

            System.out.println("COUNTRY SAVED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Country> findAll() {

        List<Country> countries = new ArrayList<>();

        String sql = "SELECT * FROM get_countries()";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Country country = new Country(rs.getLong("id"), rs.getString("name"));
                countries.add(country);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return countries;
    }

    @Override
    public void update(Country country) {

        String sql = "CALL update_country(?, ?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, country.getId());
            stmt.setString(2, country.getName());

            stmt.execute();

            System.out.println("COUNTRY UPDATED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "CALL delete_country(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setLong(1, id);
            stmt.execute();

            System.out.println("COUNTRY DELETED!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}