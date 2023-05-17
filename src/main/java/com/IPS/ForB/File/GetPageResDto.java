package com.IPS.ForB.File;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPageResDto {
    private Long page_id;
    private FullTextInfoDto full_text;
    private List<TextInfoDto> text;
    private List<ImageInfoDto> image;
}
