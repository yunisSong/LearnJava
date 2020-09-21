package Tool;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//
public class JDBCUtils {
    private static DataSource ds;

//    初始化配置文件，只有在类第一次调用的时候调用一次
    static {
        //1.加载配置文件
        try {
//            获取 SRC 路径下的文件路径方式 ClassLoader
            Properties pro = new Properties();
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL resource = classLoader.getResource("druid.properties");
            pro.load(new FileReader(resource.getPath()));
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    获取数据库连接对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//   获取数据库连接池对象
    public static DataSource getDataSource() {
        return ds;
    }
    public static void close(Statement stmt, Connection connection) {
        close(null, stmt, connection);
    }

//    释放资源
    public static void close(ResultSet set, Statement stmt, Connection connection) {

        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
