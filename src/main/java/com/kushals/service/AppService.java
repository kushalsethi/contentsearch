package com.kushals.service;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

	@Autowired
	private TransportClient client;

	private static final String LIBRARY_INDEX = "contentlibrary";
	private static final String CONTENT_TYPE = "content";

	private static final String VIEWING_INDEX = "activitylibrary";
	private static final String ACTIVITY_TYPE = "activity";

	
	/**
	 * Adds content to library index
	 * @param contentObj - Content JSON Object
	 * @return
	 */
	public String insertContent(Map<String, Object> contentObj) {
		String id = contentObj.get("id").toString();
		IndexResponse response = client.prepareIndex(LIBRARY_INDEX, CONTENT_TYPE, id).setSource(contentObj).get();
		return response.getResult().toString();
	}

	/**
	 * Returns requested Content 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getContentsByID(String id) {
		GetRequestBuilder response = client.prepareGet(LIBRARY_INDEX, CONTENT_TYPE, id);
		return response.get().getSourceAsMap();
	}

	/**
	 * Returns all the contents matched with the requested tag
	 * @param tags
	 * @return
	 */
	public List<Map<String, Object>> getContentsByTag(String tags) {

		List<Map<String, Object>> contentList = new LinkedList<>();
		QueryBuilder qb = boolQuery().must(matchQuery("metadata.tags", tags));
		SearchResponse scrollResp = client.prepareSearch(LIBRARY_INDEX).setTypes(CONTENT_TYPE)
				.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC).setScroll(new TimeValue(60000)).setQuery(qb)
				.setSize(100).get();
		// Scroll until no hits are returned
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				Map<String, Object> content = hit.getSourceAsMap();
				contentList.add(content);
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute()
					.actionGet();
		} while (scrollResp.getHits().getHits().length != 0);
		return contentList;
	}

	/**
	 * Adds viewing activity for user to viewing activity index
	 * @param viewingActivityObj - JSON Object
	 * @return
	 */
	public String insertViewingActivity(Map<String, Object> viewingActivityObj) {
		String id = viewingActivityObj.get("id").toString();
		IndexResponse response = client.prepareIndex(VIEWING_INDEX, ACTIVITY_TYPE, id).setSource(viewingActivityObj)
				.get();
		return response.getResult().toString();
	}

	/**
	 * Returns requested activity
	 * @param id
	 * @return
	 */
	public Map<String, Object> getActivityByID(String id) {
		GetRequestBuilder response = client.prepareGet(VIEWING_INDEX, ACTIVITY_TYPE, id);
		return response.get().getSourceAsMap();
	}

	/**
	 * Returns contents matched with requested location and tag
	 * @param tags
	 * @param location
	 * @return
	 */
	public List<Map<String, Object>> getContentsByViewingActivity(String tags, String location) {

		List<Map<String, Object>> activityList = new LinkedList<>();

		QueryBuilder qb = boolQuery().must(matchQuery("location", location))
				.must(matchQuery("contents_viewed.metadata.tags", tags));

		SearchResponse scrollResp = client.prepareSearch(VIEWING_INDEX).setTypes(ACTIVITY_TYPE)
				.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC).setScroll(new TimeValue(60000)).setQuery(qb)
				.setSize(100).get();
		// Scroll until no hits are returned
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				Map<String, Object> content = hit.getSourceAsMap();
				activityList .add(content);
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute()
					.actionGet();
		} while (scrollResp.getHits().getHits().length != 0);
		return activityList;
	}

}
