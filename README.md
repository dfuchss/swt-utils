# swt-utils
![Maven Deploy (Dev)](https://github.com/dfuchss/swt-utils/workflows/Maven%20Deploy%20(Dev)/badge.svg)
[![Latest Release](https://img.shields.io/github/release/dfuchss/swt-utils.svg)](https://github.com/dfuchss/swt-utils/releases/latest)
[![GitHub issues](https://img.shields.io/github/issues/dfuchss/swt-utils.svg?style=square)](https://github.com/dfuchss/swt-utils/issues)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=square)](https://github.com/dfuchss/swt-utils/blob/master/LICENCE.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dfuchss_swt-utils&metric=alert_status)](https://sonarcloud.io/dashboard?id=dfuchss_swt-utils)

This project contains a small set of classes that may help to work with SWT

# Maven & Co.
If you want to use maven or some similar tool add the following code to your pom:
```xml
<repositories>
	<repository>
		<id>gh-fuchss</id>
		<name>Github Nexus Fuchss</name>
		<url>https://packages.fuchss.org/github/releases/raw/branch/releases/</url>
		OR
		<url>https://packages.fuchss.org/github/snapshots/raw/branch/snapshots/</url>
	</repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.fuchss</groupId>
    <artifactId>swt-utils</artifactId>
    <version>develop-SNAPSHOT</version>
  </dependency>
</dependencies>
```
