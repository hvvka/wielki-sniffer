package swi.wikisniffer.book.model.searchengine;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "enwikibooks", type = "_doc")
public class Book {
    @Id
    private String id;

    @Field(name = "title", type = FieldType.Text)
    private String title;

    @Field(name = "first_image", type = FieldType.Text)
    private String firstImage = "";

    @Field(name = "text", type = FieldType.Text)
    private String text;

    @Field(name = "categories", type = FieldType.Nested)
    private List<String> categories;

    @Field(name = "timestamp", type = FieldType.Text)
    private String timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", firstImage='" + firstImage + '\'' +
                ", text='" + text + '\'' +
                ", categories=" + categories +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
