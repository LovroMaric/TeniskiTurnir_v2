package Util;

public class TestConnection {
    public static void main(String[] args) {
        try (var conn = DBUtil.getConnection()) {
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
