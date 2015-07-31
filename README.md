# pomfar

pomfar is a tool to display maven pom files directly from jar/war/zip files, free as beer and open source (Apache License, Version 2.0). Do not require any external lib.

### Current Stable Version is [1.2.0](https://maven-release.s3.amazonaws.com/release/org/javastack/pomfar/1.2.0/pomfar-1.2.0-bin.zip)

---

## Usage (Linux)

    ./bin/pomfar.sh <file.jar|war|zip>
    
## Extended usage:

    env POMFAR_OPTS="-Dpomfar.quiet=true -Dpomfar.file=pom.xml" \
      ./bin/pomfar.sh <file.jar|war|zip>

---
Inspired in [zcat](http://linux.die.net/man/1/zcat) and [jar](http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/jar.html), this is a Java-minimalistic version.
