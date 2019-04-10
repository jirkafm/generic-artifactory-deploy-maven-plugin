package com.github.jirkafm.mvn;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.maven.model.FileSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.github.jirkafm.mvn.auth.AuthenticationHeader;
import com.github.jirkafm.mvn.auth.AuthenticationHeaderFactory;
import com.github.jirkafm.mvn.checksum.ChecksumAlgorithm;
import com.github.jirkafm.mvn.deploy.ArtifactoryDeployStatusAgregate;
import com.github.jirkafm.mvn.deploy.ArtifactoryFileDeploy;
import com.github.jirkafm.mvn.deploy.ArtifactoryFileDeployInput;
import com.github.jirkafm.mvn.deploy.ArtifactoryHeaders;
import com.github.jirkafm.mvn.fs.MavenFileSetToJavaFileList;
import com.github.jirkafm.mvn.invocationbuilder.BuilderFactory;
import com.github.jirkafm.mvn.invocationbuilder.HeaderEnhancedBuilderFactory;
import com.github.jirkafm.mvn.invocationbuilder.WebTargetBuilderFactory;

@Mojo(name = "deploy")
public class Plugin extends AbstractMojo {

	/**
	 * A URL path to artifactory server.
	 *
	 */
	@Parameter(property = "url")
	private URI url;

	/**
	 * A generic artifactory repository
	 *
	 */
	@Parameter(property = "repository")
	private String repository;

	/**
	 * The method to use for the REST request.
	 *
	 * The REST request method can be configured via the <code>method</code> tag.
	 * 
	 * Defaults to <code>PUT</code>
	 *
	 */
	@Parameter(property = "method")
	private final String method = "PUT";

	/**
	 * A <code>map</code> of query headers to add to the REST request.
	 *
	 * The <code>headers</code> element will provide a way to add multiple header
	 * elements to the final REST request.
	 */
	@Parameter(property = "headers")
	private Map<String, String> headers;

	/**
	 * A list of {@link org.apache.maven.model.FileSet} rules to select files and
	 * directories.
	 *
	 * This list of <code>fileset</code> elements will be used to gather all the
	 * files to be submitted in the REST request. One REST request will be made per
	 * file.
	 */
	@Parameter(property = "filesets")
	private List<FileSet> filesets = new ArrayList<>();

	/**
	 * A <code>map</code> of query parameters to add to the REST request URL.
	 *
	 * The <code>queryParams</code> element will provide a way to add multiple query
	 * params to the final REST URL.
	 */
	@Parameter(property = "queryParams")
	private Map<String, String> queryParams;

	/**
	 * A {@link org.apache.maven.model.FileSet} rule to select files to send in the
	 * REST request.
	 *
	 * The fileset will be used to gather all the files to be submitted in the REST
	 * request. One REST request will be made per file.
	 *
	 * Internally, this element will be added to the list of <code>filesets</code>,
	 * so it will be processed in addition to the list of <code>filesets</code>
	 */
	@Parameter(property = "fileset")
	private FileSet fileset;

	/**
	 * The type of the data sent by the REST request.
	 *
	 * The data type of the REST request data. Default
	 * <code>MediaType.TEXT_PLAIN_TYPE</code>
	 *
	 * If this is specified, use the elements for MediaType class:
	 * 
	 * <pre>
	 *     &lt;requestType&gt;
	 *       &lt;type&gt;application&lt;/type&gt;
	 *       &lt;subtype&gt;json&lt;/subtype&gt;
	 *     &lt;/requestType&gt;
	 * </pre>
	 */
	@Parameter
	private MediaType requestType = MediaType.TEXT_PLAIN_TYPE;

	/**
	 * The type of the data returned by the REST request.
	 *
	 * The expected data type of the REST response. Default
	 * <code>MediaType.APPLICATION_OCTET_STREAM_TYPE</code>
	 *
	 * See <code>requestType</code> for example of usage.
	 */
	@Parameter
	private MediaType responseType = MediaType.APPLICATION_OCTET_STREAM_TYPE;

	/**
	 * List of checksum headers to be created for a file to be added into
	 * Artifactory.
	 * 
	 * <p>
	 * Expected values:
	 * <ul>
	 * <li>{@code SHA1}</li>
	 * <li>{@code SHA256}</li>
	 * <li>{@code MD5}</li>
	 * </ul>
	 * 
	 */
	@Parameter
	private List<ChecksumAlgorithm> fileChecksums;

	/**
	 * Username for basic authentication to Artifactory.
	 * 
	 * @see Plugin#password password for details
	 */
	@Parameter
	private String username;

	/**
	 * User password for basic authentication to Artifactory.
	 * 
	 * <p>
	 * Please check <a href=
	 * "https://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API#ArtifactoryRESTAPI-Authentication">authentication
	 * methods</a> supported by Artifactory.
	 * </p>
	 * <p>
	 * Following combinations are supported:
	 * <ul>
	 * <li>username + password</li>
	 * <li>username + apikey</li>
	 * <li>apikey</li>
	 * <li>accessToken</li>
	 * </ul>
	 * </p>
	 * 
	 */
	@Parameter
	private String password;

	/**
	 * Artifactory access token for accesing REST API.
	 * 
	 * @see Plugin#password password for details
	 * 
	 */
	@Parameter
	private String accessToken;

	/**
	 * Artifactory key for accessing REST API.
	 * 
	 * @see Plugin#password password for details
	 */
	@Parameter
	private String apiKey;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		final List<File> files = new MavenFileSetToJavaFileList(filesets, fileset).getList();
		if (files.isEmpty()) {
			getLog().info("No files to process");
			return;
		}

		final AuthenticationHeader authHeader = new AuthenticationHeaderFactory(username, password, accessToken, apiKey)
				.getInstance();
		final ArtifactoryDeployStatusAgregate deployStatusAgregate = new ArtifactoryDeployStatusAgregate();
		final ArtifactoryHeaders artifactoryHeaders = new ArtifactoryHeaders(headers, fileChecksums, authHeader);
		final WebTarget target = new WebTargetBuilder(url).setRepository(repository).setQueryParams(queryParams)
				.build();
		final BuilderFactory builderFactory = new WebTargetBuilderFactory(target, responseType);
		final BuilderFactory headerEnhancedBuilderFactory = new HeaderEnhancedBuilderFactory(builderFactory,
				artifactoryHeaders);

		for (final File file : files) {
			final RemoteFileLocation remoteFileLocation = new RemoteFileLocation(target.getUri(), file.getName());
			final ArtifactoryFileDeployInput input = new ArtifactoryFileDeployInput(file, method, requestType);
			final ArtifactoryFileDeploy deploy = new ArtifactoryFileDeploy(headerEnhancedBuilderFactory, input);
			deploy.addListener(deployStatusAgregate);
			deploy.addListener(new DeployStatusListener(remoteFileLocation));
			deploy.deploy();
		}

		if (!deployStatusAgregate.deploySuccesfull()) {
			throw new MojoExecutionException(deployStatusAgregate.toString());
		}
	}

}