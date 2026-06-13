//import Model.Player;
//import Model.SurfaceType;
//import Model.Tournament;
//import Model.User;
//import Service.PlayerService;
//import Service.TournamentService;
//import Service.UserService;
//
//import java.math.BigDecimal;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.Optional;
//
//public class TestMain {
//
//    public static void main(String[] args) {
//
//        TournamentService service =
//                new TournamentService();
//
//        Tournament tournament =
//                new Tournament();
//
//        tournament.setName(
//                "Australian Open"
//        );
//
//        tournament.setFoundedYear(1905);
//
//        tournament.setPrizeMoney(
//                new BigDecimal("75000000")
//        );
//
//        tournament.setSurface(
//                SurfaceType.HARD
//        );
//
//        tournament.setImagePath(
//                "assets/ao.jpg"
//        );
//
//        service.save(tournament);
//
//        List<Tournament> tournaments =
//                service.findAll();
//
//        tournaments.forEach(System.out::println);
//    }
//}
