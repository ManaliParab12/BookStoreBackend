package com.bridgelabz.onlinebookstore.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
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
}
