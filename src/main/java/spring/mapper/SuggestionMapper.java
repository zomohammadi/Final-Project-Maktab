package spring.mapper;

import spring.dto.SuggestionBriefDto;
import spring.dto.projection.SuggestionBriefProjection;

@org.mapstruct.Mapper(componentModel = "spring")
public interface SuggestionMapper extends BaseMapper<SuggestionBriefProjection, SuggestionBriefDto> {

}
// List<SuggestionBriefDto> convertEntityToDTO(List<SuggestionBriefProjection> suggestionBriefProjection);
