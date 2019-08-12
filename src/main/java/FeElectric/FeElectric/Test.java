package FeElectric.FeElectric;

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Test {

	// 生成的图网络结构存储地址
	private static final String DB_PATH = "D:\\study\\neo4j\\neo4j-community-3.5.7\\data\\databases\\graph.db";

	// 图数据库Service
	private GraphDatabaseService graphDB;

	// 边关系枚举类
	private enum RelTypes implements RelationshipType {
		CRIME, LINK
	}

	public void init() {
		graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));
	}

	/** 创建数据 */
	public void create() {

		Transaction tx = graphDB.beginTx();
		// 创建事件节点 (可选带label)
		// 建立边的关系
		// 添加权重
		// 事务成功，然后关闭数据库
		tx.success();
		tx.close();

	}

	public static void main(String[] args) {
		Test as = new Test();
		as.init();
		as.create();
	}
}

class CaseLabel implements Label {
	private String name;

	public CaseLabel(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}
}
