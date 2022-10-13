package site.metacoding.miniproject1.web.dto.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WantedsListDto {
	private Integer id;
	private Integer positionCodeId;
	private Integer careerCodeId;
	private Integer companyId;
	private String title;
	private String detail;
	private Integer viewCount;
	private Timestamp created;
	private Timestamp enddate;
	private String companyName;
	private String companyRegion;
}