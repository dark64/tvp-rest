package com.codevert.tvp.core.scrappers;

import java.io.IOException;
import java.util.List;

public interface Scrapper<T> {

    List<T> extract() throws IOException;
}
