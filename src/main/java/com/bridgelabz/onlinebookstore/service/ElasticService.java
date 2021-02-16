package com.bridgelabz.onlinebookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.onlinebookstore.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticService implements IElasticService {

	@Autowired
	private RestHighLevelClient highLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	private String TYPE = "book";
	private String INDEX = "bookstore";

	@Override
	public Book add(Book book) {
		Map<String, Object> bookMap = objectMapper.convertValue(book, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX).id(book.getElasticId()).source(bookMap);
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.add(indexRequest);
		try {
			System.out.println("index Request" + indexRequest);
			highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public List<Book> searchBooksByAuthor(String search) {
		BoolQueryBuilder boolBuilder = new BoolQueryBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
						.must(QueryBuilders.queryStringQuery("*" + search + "*")
						.analyzeWildcard(true).field("bookAuthor"));
		boolBuilder.must(queryBuilder);
		SearchRequest searchRequest = new SearchRequest(INDEX);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolBuilder);
		searchRequest.source(searchSourceBuilder);
		List<Book> bookList = new ArrayList<>();
		SearchResponse searchResponse = null;
		try {
			System.out.println("Try block");
			searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits().value > 0) {
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for (SearchHit hit : searchHit) {
					Map<String, Object> map = hit.getSourceAsMap();
					bookList.add(objectMapper.convertValue(map, Book.class));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookList;
	}

	@Override
	public void deleteBookById(String id) {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		try {
			DeleteResponse deleteResponse = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
	}

	
	@Override
	public Book updateBookById(String id, Book book) {
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
				.fetchSource(true);
		try {
			String bookJson = objectMapper.writeValueAsString(book);
			updateRequest.doc(bookJson, XContentType.JSON);
			UpdateResponse updateResponse = highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
			System.out.println(updateResponse.status());
		} catch (IOException e) {
		e.printStackTrace();
		}
		return book;
	}

}
