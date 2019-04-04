package com.github.jirkafm.mvn.fs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.FileSet;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.LoggerFactory;

public final class FileSetTransformer {

	private final FileSet fileSet;

	public FileSetTransformer(final FileSet fileSet) {
		this.fileSet = fileSet;
	}

	public List<File> toFileList() {
		try {
			if (fileSet.getDirectory() != null) {
				final File directory = new File(fileSet.getDirectory());
				final String includes = toString(fileSet.getIncludes());
				final String excludes = toString(fileSet.getExcludes());
				return FileUtils.getFiles(directory, includes, excludes);
			} else {
				LoggerFactory.getLogger(getClass()).warn("Fileset [{}] directory empty", fileSet.toString());
				return new ArrayList<>();
			}
		} catch (final IOException e) {
			throw new FileSystemException(String.format("Unable to get paths to fileset [%s]", fileSet.toString()), e);
		}
	}

	private String toString(final List<String> strings) {
		final StringBuilder sb = new StringBuilder();
		for (final String string : strings) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(string);
		}
		return sb.toString();
	}
}
