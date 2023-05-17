package com.IPS.ForB.File;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilePostResponseDto {
    private Long file_id;


    @Builder
    public FilePostResponseDto(Long file_id) {
        this.file_id = file_id;
    }
}
