package com.IPS.ForB.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextInfoDto {
    private String audio_url;
    private int font_size;
    private String text; // text_content
}
