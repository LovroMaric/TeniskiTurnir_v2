package Repository;

import Model.Category;
import Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ReadRepository<Category> {

    public List<Category> findAll() {

        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT id, name
                FROM category
                ORDER BY id
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(rs.getLong("id"), rs.getString("name"));

                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }
}