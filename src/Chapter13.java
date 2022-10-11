import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Chapter13 {

	private static final String URL = "jdbc:mysql://localhost:3306/database01?user=user01&"
			+ "password=password01&useSSL=false&allowPublicKeyRetrieval=true";

	public static void main(String[] args) throws Exception {

//		// 事前準備
//		try (Connection connection = DriverManager.getConnection(URL);
//				PreparedStatement statement = connection
//						.prepareStatement("insert into user (email, name, money) values (?, ?, ?)")) {
//			statement.setString(1, "aaa@aaa.aaa");
//			statement.setString(2, "ユーザーA");
//			statement.setInt(3, 10000);
//			
//			statement.setString(1, "bbb@bbb.bbb");
//			statement.setString(2, "ユーザーB");
//			statement.setInt(3, 10000);
//			
//			int count = statement.executeUpdate();
//			System.out.println("userテーブルに、新しく" + count + "件のデータが挿入されました");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		// コミット成功
		Connection connection = null;
		
		try {
			// connectionの取得処理
			connection = DriverManager.getConnection(URL);
			// 自動コミットしない設定
			connection.setAutoCommit(false);
			
			PreparedStatement statement = connection.prepareStatement("update user set money = money -1000 where name = ?");
			
			statement.setString(1, "ユーザーA");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("update user set money = money + 1000 where name = ?");
			
			statement.setString(1, "ユーザーB");
			statement.executeUpdate();
			// コミット処理
			connection.commit();
			
			System.out.println("コミット完了");
		} catch (Exception e) {
			// 例外時はロールバック処理
			connection.rollback();
			e.printStackTrace();
			System.out.println("コミット失敗");
		}
	}

}
