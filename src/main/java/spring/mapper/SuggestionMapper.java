package spring.mapper;
import org.mapstruct.Mapper;
import spring.dto.SuggestionBriefDto;
import spring.dto.projection.SuggestionBriefProjection;

@Mapper(componentModel = "spring")
public interface SuggestionMapper extends BaseMapper<SuggestionBriefProjection, SuggestionBriefDto> {
}
// List<SuggestionBriefDto> convertEntityToDTO(List<SuggestionBriefProjection> suggestionBriefProjection);
