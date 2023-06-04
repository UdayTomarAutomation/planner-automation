/**
 * 
 */
package com.operative.base.database.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.qameta.allure.Step;

/**
 * @author upratap
 *
 */
public class MongoDBUtils {

	//Get Mongo Client
	@Step("Get Mongo Client")
	public MongoClient getClient(String dbUrl) {
		return new MongoClient(dbUrl, 27017);
	}

	//Get Mongo Document Collection
	@Step("Get Mongo Document Collection")
	public MongoCollection<Document> getCollection(MongoClient client, String dbName, String collectionName) {
		return client.getDatabase(dbName).getCollection(collectionName);
	}

	// Get Mongo Db table data
	@Step("Get Mongo Db table data")
	@SuppressWarnings({ "resource", "deprecation" })
	public static DBCollection getMongoDb(String dbUrl, String dbName, String collectionName) {
		DB db = null;

		final MongoClient mongoClient = new MongoClient(dbUrl, 27017);
		// get access to requied db by giving db name
		db = mongoClient.getDB(dbName);
		// get db table raw data
		final DBCollection table = db.getCollection(collectionName);
		return table;
	}


	// Get Mongo Document Using Response Id
	@Step("Get Mongo Document Using Response Id")
	public String getMongoDocument(String responseId, String dbUrl, String dbName, String collectionName) {
		String document = "";
		try {
			final BasicDBObject searchQuery = new BasicDBObject();
			// filter and get data based on response Id
			searchQuery.put("_id", new ObjectId(responseId));
			final DBObject doff = getMongoDb(dbUrl, dbName, collectionName).findOne(searchQuery);
			document = doff.toString();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	// Get The Count Of Mongo Document
	@Step("Get The Count Of Mongo Document")
	public Integer getRecordCount(String dbUrl, String dbName, String collectionName) {
		final Integer count = getMongoDb(dbUrl, dbName, collectionName).find().count();
		return count;
	}

	public static MongoCollection getMongoDbCollection(String dbUrl, String dbName, String collectionName) {
		MongoDatabase db = null;
		final MongoClient mongoClient = new MongoClient(dbUrl, 27017);
		// get access to requied db by giving db name
		db = mongoClient.getDatabase(dbName);
		// get db table raw data
		final MongoCollection<Document> table = db.getCollection(collectionName);
		return table;
	}
	@Step("Get The List Of Mongo Document")
	public static List<Document> getDocumentList(String dbUrl, String dbName, String collectionName,
			BasicDBObject query) {
		@SuppressWarnings("unchecked")
		final MongoCollection<Document> collection = getMongoDbCollection(dbUrl, dbName, collectionName);
		final List<Document> documents = collection.find(query).into(new ArrayList<Document>());
		return documents;
	}

	// Get Mongo List Of Mongo Document Iterator
	@Step("Get Mongo List Of Mongo Document Iterator")
	public Iterator getListOfDocumentsBySearch(String dbURL, String dbName, String collectionName,
			BasicDBObject searchQuery) {
		Iterator<DBObject> listDbObjects = null;
		try {
			final DBCursor cursor = getMongoDb(dbURL, dbName, collectionName).find(searchQuery);
			listDbObjects = cursor;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return listDbObjects;
	}

	// Get Mono DB Cursor
	@Step("Get Mono DB Cursor")
	public DBCursor getCursor(String dbURL, String dbName, String collectionName) {
		DBCursor cursor = null;
		try {

			cursor = getMongoDb(dbURL, dbName, collectionName).find();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}
	
	@Step("Get Mono DB ListOfDocuments By  passing Query object")
	public Iterator getListOfDocumentsByQueryObject(String dbURL, String dbName, String collectionName,
			DBObject searchQuery) {
		Iterator<DBObject> obj = null;
		try {
			final DBCollection table = MongoDBUtils.getMongoDb(dbURL, dbName, collectionName);

			final DBCursor cursor = table.find(searchQuery);
			obj = cursor;
			// cursor.getReadPreference().
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	// Get Mono DB Cursor
	@Step("Get Mono DB Cursor")
	public DBCursor getCursor(String dbURL, String dbName, String collectionName, BasicDBObject searchQuery) {
		DBCursor cursor = null;
		try {
			cursor = getMongoDb(dbURL, dbName, collectionName).find(searchQuery);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}

	// Delete Mongo document
	@Step("Delete Mongo document")
	@SuppressWarnings("resource")
	public MongoDBUtils deleteMongoDocumentByField(String fieldName, String value, String dbUrl, String dbName,
			String collectionName) {
		try {
			final MongoDatabase db = new MongoClient(dbUrl, 27017).getDatabase(dbName);
			db.getCollection(collectionName).deleteMany(new Document(fieldName, value));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return this;
	}


	// Get Mongo Document Using Response Id
	@Step("Get Mongo Document Using Response Id for plan")
	public Integer getMongoDocumentCountWithQuery(String query, String planName, String dbUrl, String dbName,
			String collectionName) {
		int documentCount = 0;
		try {
			final BasicDBObject searchQuery = new BasicDBObject();
			// filter and get data based on response Id
			searchQuery.put(query, planName);
			documentCount = getMongoDb(dbUrl, dbName, collectionName).find(searchQuery).count();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return documentCount;

	}
}
