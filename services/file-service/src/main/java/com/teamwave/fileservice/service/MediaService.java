package com.teamwave.fileservice.service;

import java.io.File;

public interface MediaService {
    Long getAudioDuration(File file);
    Long getVideoDuration(File file);
}
