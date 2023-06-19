package homework_2.models.getUsersResponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RootModel {

	@JsonProperty("per_page")
	Integer perPage;
	Integer total, page;
	@JsonProperty("total_pages")
	Integer totalPages;
	List<UserDataModel> data;
	SupportModel support;

}