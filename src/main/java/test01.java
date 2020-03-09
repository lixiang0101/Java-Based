import java.sql.*;

public class test01 {
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.deregisterDriver(driver);

        //2.连接数据库
        /**
         *  jdbc:mysql://172.16.100.202:3306/test
         *      jdbc:mysql:// 协议
         *      172.16.100.202 ip地址
         *      3306 端口号
         *      test 库名
         */
        String url = "jdbc:mysql://172.16.100.202:3306/test";
        String userName = "cp_lixiang01";
        String passWd = "Wc2ZY3U,CDxo";
        Connection conn = DriverManager.getConnection(url, userName, passWd);
        System.out.println("数据库连接成功 " + conn);
        //3、获取数据库操作对象，把sql语句发送给mysql客户端
        Statement statement = conn.createStatement();

        //4、执行sql
        String sql = "";
        //.executeUpdate(sql)专门执行DML语句（insert，update，delete），返回值是一个int，代表“影响数据库中的记录条数”
        int result = statement.executeUpdate(sql);

        //5、处理查询结果

        //6、关闭资源
        if (statement != null) {//关闭资源前先判断是否为空
            statement.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
