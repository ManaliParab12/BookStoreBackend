package com.bridgelabz.onlinebookstore.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
	public Book add(Book book )  {
		Map<String, Object> bookMap = objectMapper.convertValue(book, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getElasticId()).source(bookMap);
		System.out.println("index Request" +indexRequest);
		try {
			highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	
	@Override
	public List<Book> searchBooks(String search) {
		SearchRequest searchRequest = new SearchRequest(INDEX);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		List<Book> bookList = new ArrayList<>();
		SearchResponse searchResponse = null;
		try {
			searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits() > 0) {
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
	public List<Book> searchBooksByAuthor(String search) {
		BoolQueryBuilder boolBuilder = new BoolQueryBuilder();
		QueryBuilder queryBuilder =  QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*" + search + "*").analyzeWildcard(true).field("bookAuthor"));
		boolBuilder.must(queryBuilder);
		SearchRequest searchRequest = new SearchRequest(INDEX);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolBuilder);
		searchRequest.source(searchSourceBuilder);
		List<Book> bookList = new ArrayList<>();
		SearchResponse searchResponse = null;
		try {
			searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits() > 0 ) 
			{
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for (SearchHit hit : searchHit) {
					Map<String, Object> map = hit.getSourceAsMap();
					bookList.add(objectMapper.convertValue(map, Book.class));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();		
		}return bookList;
	}
}
