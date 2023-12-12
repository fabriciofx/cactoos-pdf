[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
[![Java Profiler](https://www.ej-technologies.com/images/product_banners/jprofiler_small.png)](https://www.ej-technologies.com/products/jprofiler/overview.html)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fabriciofx/cactoos-pdf.svg)](https://search.maven.org/artifact/com.github.fabriciofx/cactoos-pdf/0.2.1/jar)
[![Javadoc](https://www.javadoc.io/badge/com.github.fabriciofx/cactoos-pdf.svg)](http://www.javadoc.io/doc/com.github.fabriciofx/cactoos-pdf)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/fabriciofx/cactoos-pdf/blob/master/LICENSE.txt)

[![Hits-of-Code](https://hitsofcode.com/github/fabriciofx/cactoos-pdf?branch=main&label=Hits-of-Code)](https://hitsofcode.com/github/fabriciofx/cactoos-pdf/view?branch=main&label=Hits-of-Code)

## Introduction

**Cactoos PDF** is a collection of object-oriented Java classes to build a [PDF](https://en.wikipedia.org/wiki/PDF) document.

**Motivation**.
We are not happy with current Java PDF solutions because:

1. Their code aren't truly object-oriented, i.e. they aren't obey these [design principles](http://www.elegantobjects.org#principles)

2. Their license code aren't really free to do anything, like MIT or BSD license

## How to use

```xml
<dependency>
  <groupId>com.github.fabriciofx</groupId>
  <artifactId>cactoos-pdf</artifactId>
</dependency>
```

Java version required: 1.8+.


### Usage

**WARNING**: Cactoos PDF is in a VERY early development stage. So the API can change at any moment.

Here an example how to build a Hello World PDF file using our API:

```java
final File file = new File("HelloWorld.pdf");
final Date date = new Date(2023, 12, 11, 20, 11, 32, "Etc/GMT-3");
final Count count = new ObjectCount();
Files.write(
    file.toPath(),
    new Document(
        count,
        new Information(
            count,
            new MapOf<>(
                new MapEntry<>("Title", "Hello World"),
                new MapEntry<>("Subject", "PDF document"),
                new MapEntry<>("Author", "Fabricio Cabral"),
                new MapEntry<>("Creator", "cactoos-pdf"),
                new MapEntry<>("Producer", "cactoos-pdf"),
                new MapEntry<>("CreationDate", date.asString()),
                new MapEntry<>("ModDate", date.asString()),
                new MapEntry<>("Keywords", "cactoos pdf elegant objects")
            )
        ),
        new Catalog(
            count,
            new DefaultPages(
                count,
                PageFormat.A4,
                new DefaultPage(
                    count,
                    new Resources(
                        new Font(
                            count,
                            new FontFamily("Times-Roman", "Type1"),
                            "F1"
                        )
                    ),
                    new Contents(
                        new FlateEncode(
                            new Text(
                                count,
                                18,
                                0,
                                0,
                                "Hello World"
                            )
                        )
                    )
                )
            )
        )
    ).asBytes()
);

```

## Contributions

Contributions are welcome! Please, open an issue before submit any kind (ideas,
documentation, code, ...) of contribution.


### How compile it?

```
$ mvn clean install -Pqulice
```


## License

The MIT License (MIT)

Copyright (C) 2023 Fabrício Barros Cabral

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


## Acknowledgements

David West ([Blog](http://davewest.us/)) for:
- [Object Thinking](http://amzn.to/2BVeiNl) Book

[@yegor256](https://github.com/yegor256) as Yegor Bugayenko ([Blog](https://wwww.yegor256.com)) for:
- [Elegant Objects](https://www.yegor256.com/elegant-objects.html) [Vol. 1](http://amzn.to/2BXdZSs) and [Vol. 2](http://amzn.to/2BuFFP4) books
