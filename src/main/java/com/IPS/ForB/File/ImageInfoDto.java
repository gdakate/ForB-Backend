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
public class ImageInfoDto {
    private Long img_idx;
    private String img_url;
    private String audio_url;
}