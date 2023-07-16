package com.pavani.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IFileDAO {
    void save(InputStream inputStream, File file) throws IOException;
}
