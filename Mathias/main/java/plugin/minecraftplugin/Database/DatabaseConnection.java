package plugin.minecraftplugin.Database;

import org.bukkit.entity.Player;
import plugin.minecraftplugin.Minecraftplugin;

import java.sql.*;
import java.sql.Connection;

/*
By Mathias
not finished | not finaly tested
!ENTER FINISH DATE!
Todo:
    - Difference between buy/sell price -> Add something which decides whether it is a sell/buy price
    f.E. : New Collumn in Database (1=Sell|2=Buy), then include this in the query in GetPrice()

Existing Classes:
- Add Player to Database (InsertNewUser)
- Add Money to a Target/ Remove Money from a Target (AddMoney | RemoveMoney)
- Transfer Money between two Targets (TransferMoney)
- Get an Item Price (GetPrice)
*/

public class DatabaseConnection {
    public static String url = "********";
    public static String user = "********";
    public static String password = "********";

    public void getCollumnsSQLquery(String query, int SQLColumns) {
        String Data = "";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            if (result == null) {
                System.out.println("Abfrage nicht gefunden");
            }
            while (result.next()) {
                for (int i = 1; i <= SQLColumns; i++) {
                    Data += result.getString(i) + ":";
                    // This Error is irrelevant
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (Data == "") {
            System.out.println("Data ist leer");
        } else {
            System.out.println(Data);
        }
    }

    //Add a new Player to Database (finished | tested)
    public void InsertNewUser(String playername, String player_uuid) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            ResultSet result_test = statement.executeQuery("SELECT * FROM Players WHERE PlayerUUID = '" + player_uuid + "'");
            boolean val = result_test.next();
            if (!val) {     // !var = var==false
                statement.executeUpdate("INSERT INTO Players (Playername, PlayerUUID) VALUES ('"+playername+"', '"+player_uuid+"')");
            } else {
                System.out.println("Der Spieler "+playername+" ist bereits eingetragen.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get Player Money
    public int GetPlayerMoney(String player_uuid) {
        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            ResultSet player_money_result = statement.executeQuery("SELECT Money From Players WHERE PlayerUUID = '"+player_uuid+"'");

            String Player_Money_Amount = "";
            while(player_money_result.next()) {
                Player_Money_Amount += player_money_result.getString(1);
            }
            int PlayerMoney = Integer.parseInt(Player_Money_Amount);

            return PlayerMoney;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //Add Money (finished | tested)
    public void AddMoney(String target_uuid, int sum_to_be_added) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            ResultSet target_result = statement.executeQuery("SELECT Money FROM Players WHERE PlayerUUID = '"+target_uuid+"'");
            String Target_Data = "";
            while(target_result.next()) {
                Target_Data += target_result.getString(1);
            }
            int actuall_t_amount = Integer.parseInt(Target_Data);
            System.out.println(actuall_t_amount);

            int new_target_amount = actuall_t_amount + sum_to_be_added;
            if (new_target_amount != actuall_t_amount) {
                statement.executeUpdate("UPDATE Players SET Money = '"+new_target_amount+"' WHERE PlayerUUID = '"+target_uuid+"'");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Remove Money (not finished | not tested)
    public void RemoveMoney(String target_uuid, int sum_to_be_removed) {
        try{
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            ResultSet target_result = statement.executeQuery("SELECT Money FROM Players WHERE PlayerUUID = '"+target_uuid+"'");
            String Target_Data = "";
            while(target_result.next()) {
                Target_Data += target_result.getString(1);
            }
            int actuall_t_amount = Integer.parseInt(Target_Data);
            System.out.println(actuall_t_amount);

            int new_target_amount = actuall_t_amount - sum_to_be_removed;
            if (new_target_amount != actuall_t_amount) {
                statement.executeUpdate("UPDATE Players SET Money = '"+new_target_amount+"' WHERE PlayerUUID = '"+target_uuid+"'");
            }

    }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Transfer money from player to target (finished | not tested)
    public void TransferMoney(Player player, String player_uuid, String target_uuid, int amount) {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            //------Get actuall player money amount------//
            ResultSet player_result = statement.executeQuery("SELECT Money FROM Players WHERE PlayerUUID = '"+player_uuid+"'");
            String Player_Data = "";
            while (player_result.next()) {
                Player_Data += player_result.getString(1);
            }
            int actuall_p_amount = Integer.parseInt(Player_Data);
            System.out.println(actuall_p_amount);
            //------Get actuall player money amount------//


            //------Get actuall target money amount------//
            ResultSet target_result = statement.executeQuery("SELECT Money FROM Players WHERE PlayerUUID = '"+target_uuid+"'");
            String Target_Data = "";
            while(target_result.next()) {
                Target_Data += target_result.getString(1);
            }
            int actuall_t_amount = Integer.parseInt(Target_Data);
            System.out.println(actuall_t_amount);
            //------Get actuall target money amount------//

            //-----Transfer the given amount of money-----//
            int new_player_amount = actuall_p_amount - amount;
            int new_target_amount = actuall_t_amount + amount;
            if (new_player_amount != actuall_p_amount && new_player_amount >= 0) {
                statement.executeUpdate("UPDATE Players SET Money = '"+new_player_amount+"' WHERE PlayerUUID = '"+player_uuid+"'");
                if (new_target_amount != actuall_t_amount) {
                    statement.executeUpdate("UPDATE Players SET Money = '"+new_target_amount+"' WHERE PlayerUUID = '"+target_uuid+"'");
                }
            }else{
                player.sendMessage(Minecraftplugin.PREFIX + "§cSo viel Geld hast du nicht mehr!!");
            }
            //-----Transfer the given amount of money-----//

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get Price for Items from Database
    public int GetPrice(String material, int buy_or_sell) {
        //buy_or_sell = 1 -> BuyMenu | buy_or_sell = 2 -> SellMenu
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();

            ResultSet price_result = statement.executeQuery("SELECT Price FROM ItemPrices WHERE Material = '"+material+"' AND MenuType = '"+buy_or_sell+"'");
            String Item_Price_result = "";
            while(price_result.next()) {
                Item_Price_result += price_result.getString(1);
            }
            int ItemPrice = Integer.parseInt(Item_Price_result);

            return ItemPrice;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
