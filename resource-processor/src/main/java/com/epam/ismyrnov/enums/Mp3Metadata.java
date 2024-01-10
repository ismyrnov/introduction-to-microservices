package com.epam.ismyrnov.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Mp3Metadata {
  ALBUM("xmpDM:album"),
  ARTIST("xmpDM:artist"),
  NAME("dc:title"),
  LENGTH("xmpDM:duration"),
  YEAR("xmpDM:releaseDate");

  private final String value;
}
