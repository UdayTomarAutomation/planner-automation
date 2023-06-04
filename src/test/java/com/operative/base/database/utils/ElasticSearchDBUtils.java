/**
 * 
 */
package com.operative.base.database.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.operative.base.utils.Logger;

/**
 * @author upratap
 *
 */
public class ElasticSearchDBUtils {
	Settings settings;
	TransportClient client;

	public ElasticSearchDBUtils(String host) {

		settings = Settings.builder().put("client.transport.ignore_cluster_name", true).build();
		client = new PreBuiltTransportClient(settings);
		try {
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));
		} catch (final UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TransportClient getClient() {
		return client;
	}

	public SearchResponse getDetails(BoolQueryBuilder bool, String reqFields[], String index, String type,
			String fileName) throws Exception {

		final SearchRequestBuilder srb = client.prepareSearch(index).setTypes("region").setQuery(bool);
		srb.setFetchSource(reqFields, null);
		srb.setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH);
		final SearchResponse response = srb.setSize(10000).execute().actionGet();
		return response;
	}

	public SearchResponse getAllDetails(BoolQueryBuilder bool, String index) throws Exception {

		final SearchRequestBuilder srb = client.prepareSearch(index.split(",")).setQuery(bool).setSize(10000);
		srb.setSearchType(SearchType.QUERY_AND_FETCH);
		final SearchResponse response = srb.execute().actionGet();
		return response;
	}

	public boolean isIndexExists(String index) {
		boolean exists = client.admin().indices().prepareExists(index).execute().actionGet().isExists();
		return exists;
	}

	public SearchResponse getAllDetails(BoolQueryBuilder bool, String index, SortBuilder sort, int size)
			throws Exception {

		final SearchRequestBuilder srb = client.prepareSearch(index.split(",")).addSort(sort).setQuery(bool)
				.setSize(size);
		srb.setSearchType(SearchType.QUERY_AND_FETCH);
//		BasePage.log("ES Request : " + srb.toString());
		final SearchResponse response = srb.execute().actionGet();
		return response;
	}

	public SearchResponse getDetails(BoolQueryBuilder bool, String reqFields[], String index, String type)
			throws Exception {

		final SearchRequestBuilder srb = client.prepareSearch(index).setTypes("region").setQuery(bool);
		srb.storedFields("*");

		// srb.setFetchSource(reqFields, null);
		srb.setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH);
		Logger.log(srb.toString());
		final SearchResponse response = srb.setSize(10000).execute().actionGet();

		return response;

	}

	public SearchResponse getAllDetails(BoolQueryBuilder bool, String index, SortBuilder sort) throws Exception {

		final SearchRequestBuilder srb = client.prepareSearch(index.split(",")).addSort(sort).setQuery(bool);
		srb.setSearchType(SearchType.QUERY_AND_FETCH);
		Logger.log("ES Request : " + srb.toString());
		final SearchResponse response = srb.execute().actionGet();
		return response;
	}
}
