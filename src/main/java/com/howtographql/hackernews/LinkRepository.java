package com.howtographql.hackernews;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;



public class LinkRepository {
    
	private final MongoCollection<Document> links;

	public LinkRepository(MongoCollection<Document> links) {
        this.links = links;
    }
	
    public Link findById(String id) {
        Document doc = links.find(Filters.eq("_id", new ObjectId(id))).first();
        return link(doc);
    }
    public List<Link> getAllLinks() {
    	List<Link> allLinks = new ArrayList<Link>();
        for (Document doc : links.find()) {
            allLinks.add(link(doc));
        }
        return allLinks;
    }
    
    public void saveLink(Link link) {
    	Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        links.insertOne(doc);
    }
    
    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"));
    }
}
