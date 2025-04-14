package com.teamwave.fileservice.service.impl;

import com.teamwave.fileservice.exception.MediaServiceException;
import com.teamwave.fileservice.service.MediaService;
import com.xuggle.xuggler.IContainer;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MediaServiceImpl implements MediaService {
    @Override
    public Long getAudioDuration(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            AudioHeader audioHeader = audioFile.getAudioHeader();
            return (long) audioHeader.getTrackLength();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
            throw new MediaServiceException("Error on try to read duration of file", e);
        }
    }

    @Override
    public Long getVideoDuration(File file) {
        IContainer container = IContainer.make();
        if (container.open(file.getPath(), IContainer.Type.READ, null) < 0) {
            throw new MediaServiceException("Unable to open file");
        }
        long duration = container.getDuration(); // Duration in microseconds
        return duration / 1000000; // Convert to seconds
    }
}
