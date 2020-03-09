import java.sql.*;
import java.util.ResourceBundle;

/**
 * 注册驱动的另外一种方式
 * public class Driver extends NonRegisteringDriver implements java.sql.Driver {
 *     public Driver() throws SQLException {
 *     }
 *
 *     static {
 *         try {
 *             DriverManager.registerDriver(new Driver());
 *         } catch (SQLException var1) {
 *             throw new RuntimeException("Can't register driver!");
 *         }
 *     }
 * }
 */
public class test02 {
    public static void main(String[] args) {
        /**
         * 1、com.mysql.jdbc.Driver类中有一个静态代码块，这个静态代码块就是注册驱动
         * 2、静态代码块在类加载的时候执行
         * 3、反射机制 Class.forName()会导致类的加载
         * 4、通过资源配置文件将各个参数传递，避免直接在java文件中输入mysql的密码等信息
         */
        ResourceBundle mysqlconf = ResourceBundle.getBundle("mysqlconf");
        String driver = mysqlconf.getString("driver");
        String url = mysqlconf.getString("url");
        String userName = mysqlconf.getString("userName");
        String passWd = mysqlconf.getString("passWd");

        Connection conn = null;
        try {
            Class.forName(driver); //这里不用变量接收返回值，因为只用类加载动作
            conn = DriverManager.getConnection(url, userName, passWd);
            System.out.println("数据库连接成功 " + conn);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "select * from test.xs_课程明细底表 limit 10;";
        ResultSet resultSet = null;
        Statement stat = null;
        try {
            stat = conn.createStatement();
            resultSet = stat.executeQuery(sql);
            boolean flag = resultSet.next();
            while (flag){
                String teacher = resultSet.getString("上课老师");
                Date data = resultSet.getDate("上课日期");
                String unitName = resultSet.getString("课程名称");
                System.out.println(teacher + data + unitName);
                flag = resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
