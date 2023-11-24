package dbutils;

import lombok.SneakyThrows;


import java.sql.*;


public class DBUtils {

    @SneakyThrows
    public static int getCountLinesFNSINN() {
        String countSQL = "SELECT COUNT(*) FROM FNS_INN_PERSON_TEST_QUERY;";
        int result = 0;
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:firebirdsql://192.168.1.140/3050:/ncore-bank.fdb"
                        , "SYSDBA", "masterkey"
                );
                Statement countStmt = conn.createStatement();
        ) {
            try (ResultSet rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    result = count;
                }
            }
        }
        return result;
    }


}
