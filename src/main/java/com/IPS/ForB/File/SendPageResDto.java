package com.IPS.ForB.File;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class SendPageResDto {
    private Long page_id;
    private String full_audio_url;
    private List<SendTextInfoDto> text;
    private List<ImageInfoDto> image;

    @Builder
    public SendPageResDto(Long page_id, String full_audio_url, List<SendTextInfoDto> text, List<ImageInfoDto> image) {
        this.page_id = page_id;
        this.full_audio_url = full_audio_url;
        this.text = text;
        this.image = image;
    }
}
