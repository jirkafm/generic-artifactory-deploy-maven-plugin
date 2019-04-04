package com.github.jirkafm.mvn.fs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.FileSet;

public class MavenFileSetToJavaFileList {

	private final FileSet fileset;
	private List<FileSet> filesets;

	public MavenFileSetToJavaFileList(final List<FileSet> filesets, final FileSet fileset) {
		this.filesets = filesets;
		this.fileset = fileset;
	}

	public List<File> getList() {
		final List<File> files = new ArrayList<>();
		if (fileset != null) {
			if (filesets == null) {
				filesets = new ArrayList<>();
			}
			filesets.add(fileset);

		}
		if (filesets != null) {
			for (final FileSet fs : filesets) {
				if ((fs != null) && (fs.getDirectory() != null)) {
					final FileSetTransformer fileMgr = new FileSetTransformer(fs);
					files.addAll(fileMgr.toFileList());
				}
			}
		}
		return files;
	}

}
