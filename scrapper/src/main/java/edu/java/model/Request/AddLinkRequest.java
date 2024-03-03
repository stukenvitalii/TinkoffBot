package edu.java.model.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URL;
import java.util.Objects;
import javax.annotation.Generated;
import org.springframework.validation.annotation.Validated;

/**
 * AddLinkRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-02-29T17:43:31.402605197Z[GMT]")
public class AddLinkRequest   {
  @JsonProperty("link")
  private URL link = null;

  public AddLinkRequest link(URL link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
   **/
  @Schema(description = "")

    public URL getLink() {
    return link;
  }

  public void setLink(URL link) {
    this.link = link;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddLinkRequest addLinkRequest = (AddLinkRequest) o;
    return Objects.equals(this.link, addLinkRequest.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddLinkRequest {\n");

    sb.append("    link: ").append(toIndentedString(link)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
