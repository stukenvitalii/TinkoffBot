package edu.java.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;

@Validated

public class ListLinksResponse {
    @JsonProperty("links")
    @Valid
    private List<LinkResponse> links = null;

    @JsonProperty("size")
    private Integer size = null;

    public ListLinksResponse links(List<LinkResponse> links) {
        this.links = links;
        return this;
    }

    public ListLinksResponse addLinksItem(LinkResponse linksItem) {
        if (this.links == null) {
            this.links = new ArrayList<LinkResponse>();
        }
        this.links.add(linksItem);
        return this;
    }

    @Valid
    public List<LinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
    }

    public ListLinksResponse size(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListLinksResponse listLinksResponse = (ListLinksResponse) o;
        return Objects.equals(this.links, listLinksResponse.links)
            && Objects.equals(this.size, listLinksResponse.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(links, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ListLinksResponse {\n");

        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
